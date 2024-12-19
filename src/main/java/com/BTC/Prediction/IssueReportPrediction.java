package com.BTC.Prediction;

import com.BTC.DB.DatabaseHandler;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class IssueReportPrediction {
    
    private DatabaseHandler db;

    public IssueReportPrediction(DatabaseHandler db) {
        this.db = db;
    }

    public void generateReport() {
        // Récupérer les informations de la base de données
        String datePrediction = db.getPredictionDate();
        double predictedPrice = db.getPredictedPrice();
        String predictionError = db.getPredictionError();

        // Création du document PDF
        Document document = new Document();
        try {
            // Création du fichier PDF
            PdfWriter.getInstance(document, new FileOutputStream("PredictionReport.pdf"));

            // Ouvrir le document pour écrire
            document.open();

            // Ajouter un titre
            Paragraph title = new Paragraph("Rapport de Prédiction",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter le développeur
            document.add(new Paragraph("Développeur : Étudiants ENSA Kénitra S7"));

            // Ajouter le modèle AI
            document.add(new Paragraph("Modèle AI : ARIMA"));

            // Ajouter la date de prédiction
            document.add(new Paragraph("Date de prédiction : " + datePrediction));

            // Ajouter la prédiction
            document.add(new Paragraph("Prix prédit : " + predictedPrice));

            // Ajouter l'estimation de l'erreur
            document.add(new Paragraph("Estimation de l'erreur : " + predictionError));

            // Fermer le document
            document.close();
            System.out.println("Le rapport PDF a été généré avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
