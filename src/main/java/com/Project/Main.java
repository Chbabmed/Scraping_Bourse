package com.Project;


import com.Project.PredictionFinal.IssueReportPrediction_Bourse;
import com.Project.PredictionFinal.PredictPrice_Bourse;
import com.Project.PredictionFinal.TestPredictionPrice_Bourse;
import com.Project.PredictionFinal.Train_AI_Model_Bourse;


public class Main {
    public static void main(String[] args) {

        try {

            Train_AI_Model_Bourse trainer = new Train_AI_Model_Bourse();
            trainer.trainModel2("2024-11-16", "2024-12-01", "AKDITAL");

            //test du modèle pour prédire une date passée et calculer l'erreur de prédiction avec enregistrement des données de test dans la table test_predictions

            TestPredictionPrice_Bourse test_predictor = new TestPredictionPrice_Bourse();
            test_predictor.predictPriceForDateRange("AKDITAL", "2024-12-02","2024-12-20");

            // prédire le prix de clotured
            PredictPrice_Bourse predictor = new PredictPrice_Bourse();
            predictor.predictPrice("AKDITAL", "2024-12-21");

            // générer un rapport de prédiction
            IssueReportPrediction_Bourse report = new IssueReportPrediction_Bourse();
            report.generateReport("AKDITAL");

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
