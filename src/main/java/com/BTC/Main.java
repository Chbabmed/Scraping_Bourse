package com.BTC;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.DB.Load;
import com.BTC.GUI.ShowChart2;
import com.BTC.Prediction.IssueReportPrediction;
import com.BTC.Prediction.PredictPrice;
import com.BTC.Prediction.TestPredictionPrice;
import com.BTC.Prediction.Train_AI_Model;
import com.BTC.Scrapers.Scrap;
import com.BTC.Transformers.TransformData;

import java.util.List;

// la class main pour l'instanciation et le test des Classes

public class Main {
    public static void main(String[] args) {
        try {
        	
        	//scraping des données 
        	
            Scrap scraper = new Scrap();
            
            System.out.println("scraping en cours. Veuillez patienter quelques secondes ...");
            List<String[]> Data_brute = scraper.scrapeData();
            System.out.println("scraping des données terminé");
            
            //transformation des données

            TransformData transformer = new TransformData();
            System.out.println("traitement des données en cours ...");
            List<BitcoinData> Data = transformer.transformData(Data_brute);
            
            System.out.println("traitement des données terminé");
            
            //chargement des données dans la DB dans la table bitcoin_data
            
            DatabaseHandler db = new DatabaseHandler();
            System.out.println("chargement des données en cours. Veuillez patienter quelques secondes ...");
            Load loader = new Load();
            loader.loadData(db, Data);

            System.out.println("mise à jours de la base de donées terminée");
            
            // affichage de la charte (par exemple du 2024-09-01 à 2024-12-10 )
            
            ShowChart2 chart = new ShowChart2(db);
            chart.displayChart("2024-09-01", "2024-12-10");
            
            // entrainement du modèle ARIMA (par exemple en utilisant les données du 2024-01-01 au 2024-10-31)
            
            Train_AI_Model trainer = new Train_AI_Model(db);
            trainer.trainModel("2023-12-16", "2024-10-31");
            
            //test du modèle pour prédire une date passée et calculer l'erreur de prédiction avec enregistrement des données de test dans la table test_predictions
            
            TestPredictionPrice test_predictor = new TestPredictionPrice(db);
            test_predictor.predictPriceForDateRange("2024-11-01","2024-12-17");
            
            
            // test réel pour prédire le prix de cloture de demain : le 2024-12-18
            PredictPrice predictor = new PredictPrice(db);
            predictor.predictPrice("2024-12-18");
            
            
            // générer un rapport de prédiction
            IssueReportPrediction report = new IssueReportPrediction(db);
            report.generateReport();
            
            // il faut ajouter le code pour la fermeture de connecxion à la base
           

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}