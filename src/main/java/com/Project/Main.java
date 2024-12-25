package com.Project;

import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;
import com.Project.DB.LoadBourse;
import com.Project.GUI.homePage;
import com.Project.Predection.ARIMAModel;
import com.Project.Predection.ModelTester;
import com.Project.Predection.dataHandler;
import com.Project.Scrapers.BourseScraper;
import com.Project.Transformers.BourseTransformer;

import com.Project.Predection.PredictionPDFGenerator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ------------------------------> Bourse code :


/*        String date = null;
        List<String[]> dirthyData;

        LoadBourse.GetLastDate("AKDITAL");

        BourseScraper Bs = new BourseScraper();
        dirthyData = Bs.BScraper("AKDITAL");
        List<transformActions> cleanData = BourseTransformer.TransformData(dirthyData);

        LoadBourse loader = new LoadBourse();
        loader.loadBourseData(cleanData);*/

        // Étape 1 : Charger les données

        String startDate = "2024-11-26";
        String endDate = "2024-12-18";
        String Instrument = "Akdital";

        //je doit ajouter instrument :
        double[] closingPrices = dataHandler.fetchClosingPrices(startDate, endDate, Instrument);

        // Diviser les données en data for entraînement et de test => " 60% For training | 40% for Model Testing "
        int splitIndex = (int) (closingPrices.length * 0.6);
        double[] trainData = new double[splitIndex];
        double[] testData = new double[closingPrices.length - splitIndex];

        // copier les element des tableaux precedent vers closing price table
        System.arraycopy(closingPrices, 0, trainData, 0, splitIndex);
        System.arraycopy(closingPrices, splitIndex, testData, 0, closingPrices.length - splitIndex);

        // Étape 2 : Entraîner le modèle ARIMA
        ARIMAModel arima = new ARIMAModel(trainData);
        arima.trainModel(testData);

        // --> get  standard error :
        double ecart =  arima.getEcartdError();

        // Étape 3 : Faire une prédiction avec marge d'erreur
        int futureSteps = 1;
        double[][] futurePredictions = arima.predictWithMargin(futureSteps);

        // Étape 4 : Afficher les prédictions avec marges d'erreur
        System.out.println("Predictions with Margin of Error for the next " + futureSteps + " days:");
        for (int i = 0; i < futureSteps; i++) {
            System.out.printf("Day %d: Prediction=%.2f, Lower Bound=%.2f, Upper Bound=%.2f%n",
                    i + 1, futurePredictions[i][0], futurePredictions[i][1], futurePredictions[i][2]);
        }

        // Générer le rapport PDF avec les prédictions
        PredictionPDFGenerator.generateReport(futurePredictions, "ARIMA", futureSteps, "Mohamed", Instrument, ecart);

    }
}
