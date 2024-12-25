package com.Project.PredictionFinal;

import com.BTC.Prediction.ARIMA;
import com.BTC.Transformers.NormalizeData;
import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class Train_AI_Model_Bourse {



    // pour bourse de casa

    public void trainModel2(String startDate, String endDate, String instrument ) {
        List<transformActions> data = BourseDataBaseHandler.GetAllBourseData(startDate,endDate,instrument);

        if (((java.util.List<?>) data).isEmpty()) {
            System.out.println("No data found for the specified date range.");
            return;
        }

        // Collection des prix de clotures
        double[] closingPrices = data.stream().mapToDouble(transformActions::getClosingPrice).toArray();

        // Normalization des données
        NormalizeData normalizer = new NormalizeData();
        double[] normalizedPrices = normalizer.normalize(closingPrices);

        // entrainement du modèle ARIMA
        ARIMA arima = new ARIMA(normalizedPrices);
        arima.train();

        // enregistrer le modèle et le normalisateur
        saveObject(arima, "./modelBourse/Bourse_arima.model");
        saveObject(normalizer, "./modelBourse/Bourse_normalizer.model");

        // vider le table test_predictions
        //dbHandler.truncateTestPredictions();
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

