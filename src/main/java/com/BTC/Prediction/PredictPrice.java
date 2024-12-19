package com.BTC.Prediction;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.Transformers.NormalizeData;

import java.io.*;
import java.util.List;

public class PredictPrice {
    private DatabaseHandler dbHandler;

    public PredictPrice(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public double predictPrice(String predictionDate) {
        // Load ARIMA model and normalizer
        ARIMA arima = (ARIMA) loadObject("arima.model");
        NormalizeData normalizer = (NormalizeData) loadObject("normalizer.model");

        if (arima == null || normalizer == null) {
            System.out.println("aucun modèle entrainé est trouvé.");
            return -1;
        }

        // Get historical data up to the prediction date
        List<BitcoinData> data = dbHandler.getDataBetweenDates("2023-12-16", predictionDate);
        if (data.isEmpty()) {
            System.out.println("aucune donnée pour la prédiction est trouvée.");
            return -1;
        }

        double[] closingPrices = data.stream().mapToDouble(BitcoinData::getClose).toArray();

        // Normalize data
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // Predict the next value
        double normalizedPrediction = arima.predictNext(normalizedPrices);
        double prediction = normalizer.denormalize(normalizedPrediction);

        System.out.println("le prix prédict pour la date du  " + predictionDate + " est : " + prediction);

        
     // Enregistrer la prédiction dans la base de données via DatabaseHandler
        dbHandler.savePrediction(predictionDate, prediction);
        
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
