package com.BTC.Prediction;

import java.io.*;
import java.util.List;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.Transformers.NormalizeData;
import org.apache.commons.math3.linear.RealMatrix;

public class Train_AI_Model {
    private DatabaseHandler dbHandler;

    public Train_AI_Model(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void trainModel(String startDate, String endDate) {
        List<BitcoinData> data = dbHandler.getDataBetweenDates(startDate, endDate);

        if (data.isEmpty()) {
            System.out.println("No data found for the specified date range.");
            return;
        }

        // Collection des prix de clotures
        double[] closingPrices = data.stream().mapToDouble(BitcoinData::getClose).toArray();

        // Normalization des données
        NormalizeData normalizer = new NormalizeData();
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // entrainement du modèle ARIMA
        ARIMA arima = new ARIMA(normalizedPrices);
        arima.train();

        // enregistrer le modèle et le normalisateur
        saveObject(arima, "arima.model");
        saveObject(normalizer, "normalizer.model");
        
        // vider le table test_predictions
        dbHandler.truncateTestPredictions();
    }

    private void saveObject(Object object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
            System.out.println("enregistré : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
