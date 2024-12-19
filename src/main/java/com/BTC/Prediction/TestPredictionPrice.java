package com.BTC.Prediction;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.Transformers.NormalizeData;

import java.io.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestPredictionPrice {
    private DatabaseHandler dbHandler;

    public TestPredictionPrice(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    
 // Méthode pour calculer l'erreur relative en pourcentage avec deux chiffres après la virgule
    
    private String calculateRelativeError(double actualValue, double predictedValue) {
        if (actualValue == 0) {
            return "0.00"; // Éviter la division par zéro et retourner un format valide
        }
        double error = Math.abs(actualValue - predictedValue) / actualValue * 100;
        // Formater à deux décimales et retourner sous forme de String
        return String.format("%.2f", error) + "%";
    }
    
    // Récupère la valeur réelle du Bitcoin depuis la base de données pour calculer l'erreur de prédiction test
    
    private double getActualValue(String date) {
        List<BitcoinData> data = dbHandler.getDataBetweenDates(date, date);
        if (!data.isEmpty()) {
            return data.get(0).getClose(); // Utiliser la valeur de clôture
        }
        return 0.0; // Si aucune donnée n'est trouvée
    }

    public double predictPrice(String predictionDate) {
        // Load ARIMA model and normalizer
        ARIMA arima = (ARIMA) loadObject("arima.model");
        NormalizeData normalizer = (NormalizeData) loadObject("normalizer.model");

        if (arima == null || normalizer == null) {
            System.out.println("No trained model found.");
            return -1;
        }

        // Get historical data up to the prediction date
        List<BitcoinData> data = dbHandler.getDataBetweenDates("1970-01-01", predictionDate);
        if (data.isEmpty()) {
            System.out.println("No data found for prediction.");
            return -1;
        }

        double[] closingPrices = data.stream().mapToDouble(BitcoinData::getClose).toArray();

        // Normalize data
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // Predict the next value
        double normalizedPrediction = arima.predictNext(normalizedPrices);
        double prediction = normalizer.denormalize(normalizedPrediction);

        System.out.println("le prix prédict pour la date du " + predictionDate + " est : " + prediction);
        
     // Obtenir la valeur réelle pour la date demandée
        double actualValue = getActualValue(predictionDate);
        
     // Calculer l'erreur relative en pourcentage
        String predictionErrorPercentage = calculateRelativeError(actualValue, prediction);
        System.out.println("Erreur de prédiction : " + predictionErrorPercentage);
        
     // Enregistrer la prédiction dans la base de données via DatabaseHandler
        dbHandler.savePredictionToDatabase(predictionDate, prediction, actualValue, predictionErrorPercentage);
        
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
    public void predictPriceForDateRange(String dateStart, String dateEnd) {
        LocalDate startDate = LocalDate.parse(dateStart, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(dateEnd, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            // Appeler predictPrice pour chaque date dans l'intervalle
            String currentDateString = currentDate.toString();
            System.out.println("Prédiction pour le " + currentDateString + ":");
            predictPrice(currentDateString);
            currentDate = currentDate.plusDays(1); // Passer au jour suivant
        }
    }
}
