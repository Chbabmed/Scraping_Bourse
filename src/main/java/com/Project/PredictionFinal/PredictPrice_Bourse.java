package com.Project.PredictionFinal;

import com.BTC.Prediction.ARIMA;
import com.BTC.Transformers.NormalizeData;
import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PredictPrice_Bourse {


    public double predictPrice(String instrument, String predictionDate) {
        // Load ARIMA model and normalizer
        ARIMA arima = (ARIMA) loadObject("./modelBourse/Bourse_arima.model");
        NormalizeData normalizer = (NormalizeData) loadObject("./modelBourse/Bourse_normalizer.model");

        if (arima == null || normalizer == null) {
            System.out.println("aucun modèle entrainé est trouvé.");
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


        List<transformActions> data = BourseDataBaseHandler.GetAllBourseData("2023-01-01", newDateString, instrument);
        if (data.isEmpty()) {
            System.out.println("aucune donnée pour la prédiction est trouvée.");
            return -1;
        }

        double[] closingPrices = data.stream().mapToDouble(transformActions::getClosingPrice).toArray();

        // Normalize data
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // Predict the next value
        double normalizedPrediction = arima.predictNext(normalizedPrices);
        double prediction = normalizer.denormalize(normalizedPrediction);

        System.out.println("le prix prédict de l'instrument : " + instrument + " pour la date du  " + predictionDate + " est : " + prediction);


        // Enregistrer la prédiction dans la base de données via DatabaseHandler
        BourseDataBaseHandler dataBase = new BourseDataBaseHandler();
        dataBase.savePrediction(predictionDate, instrument, prediction);

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
}

