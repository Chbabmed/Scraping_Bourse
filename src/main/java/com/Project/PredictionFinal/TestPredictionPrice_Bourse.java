package com.Project.PredictionFinal;

import com.BTC.Prediction.ARIMA;
import com.BTC.Transformers.NormalizeData;
import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;

import java.io.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class TestPredictionPrice_Bourse {


    // Méthode pour calculer l'erreur relative en pourcentage avec deux chiffres après la virgule

    private String calculateRelativeError(double actualValue, double predictedValue) {
        if (actualValue == 0) {
            return "0.00"; // Éviter la division par zéro et retourner un format valide
        }
        double error = Math.abs(actualValue - predictedValue) / actualValue * 100;
        // Formater à deux décimales et retourner sous forme de String
        return String.format("%.2f", error) + "%";
    }

    // Récupère la valeur réelle de l'action depuis la base de données pour calculer l'erreur de prédiction test

    private double getActualValue(String date, String instrument) {

        List<transformActions> data = BourseDataBaseHandler.GetAllBourseData(date,date,instrument);

        if (!data.isEmpty()) {
            return data.get(0).getClosingPrice(); // Utiliser la valeur de clôture
        }
        return 0.0; // Si aucune donnée n'est trouvée
    }

    public double predictPrice(String predictionDate, String instrument) {
        // Load ARIMA model and normalizer
        ARIMA arima = (ARIMA) loadObject("./modelBourse/Bourse_arima.model");
        NormalizeData normalizer = (NormalizeData) loadObject("./modelBourse/Bourse_normalizer.model");

        if (arima == null || normalizer == null) {
            System.out.println("No trained model found.");
            return -1;
        }

        //logique pour calculer la veille de la date de prédiction

        // Get historical data up to the prediction date

        // Parser la chaîne en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(predictionDate, formatter);

        // Décrémenter la date d'un jour
        LocalDate decrementedDate = date.minusDays(1);

        // Convertir la date décrémentée en String
        String newDateString = decrementedDate.format(formatter);


        List<transformActions> data = BourseDataBaseHandler.GetAllBourseData("1970-01-01", newDateString, instrument);
        if (data.isEmpty()) {
            System.out.println("No data found for prediction.");
            return -1;
        }

        double[] closingPrices = data.stream().mapToDouble(transformActions::getClosingPrice).toArray();

        // Normalize data
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // Predict the next value
        double normalizedPrediction = arima.predictNext(normalizedPrices);
        double prediction = normalizer.denormalize(normalizedPrediction);

        System.out.println("le prix prédict pour la date du " + predictionDate + " est : " + prediction);

        // Obtenir la valeur réelle pour la date demandée
        double actualValue = getActualValue(predictionDate, instrument);

        // Calculer l'erreur relative en pourcentage
        String predictionErrorPercentage = calculateRelativeError(actualValue, prediction);
        System.out.println("Erreur de prédiction : " + predictionErrorPercentage);

        // initialiser la table des enregistrement;

        BourseDataBaseHandler.truncateTestPredictionsTable();

        // Enregistrer la prédiction dans la base de données via DatabaseHandler
        BourseDataBaseHandler dataBase = new BourseDataBaseHandler();
        dataBase.savePredictionToDatabase(predictionDate, instrument, prediction, actualValue, predictionErrorPercentage);

        return prediction;

    }


    private Object loadObject(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour afficher les résultats de predictPrice pour un intervalle entre dateStart et dateEnd

    public void predictPriceForDateRange(String instrument, String dateStart, String dateEnd) {
        LocalDate startDate = LocalDate.parse(dateStart, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(dateEnd, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            // Appeler predictPrice pour chaque date dans l'intervalle
            String currentDateString = currentDate.toString();
            System.out.println("Prédiction pour le " + currentDateString + ":");
            predictPrice(currentDateString, instrument);
            currentDate = currentDate.plusDays(1); // Passer au jour suivant
        }
    }
}
