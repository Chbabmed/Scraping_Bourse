package com.Project.PredictionFinal;

import com.Project.DB.BourseDataBaseHandler;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.awt.Desktop;
import java.io.File;

public class IssueReportPrediction_Bourse {


    public void generateReport(String instrument) {
        BourseDataBaseHandler db = new BourseDataBaseHandler();

        // Récupérer les informations de la base de données
        String datePrediction = db.getPredictionDate(instrument);
        double predictedPrice = db.getPredictedPrice(instrument);
        String predictionError = db.getPredictionError(instrument);

        // Création du document PDF
        Document document = new Document();
        try {
            // nom de l'instrument correct
            String safeInstrument = instrument.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String fileName = "PredictionReport_Bourse_" + safeInstrument + ".pdf";

            // Création du fichier PDF
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            // Ouvrir le document
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

            // Ajouter l'instrument
            document.add(new Paragraph("Instrument : " + instrument));

            // Ajouter la date de prédiction
            document.add(new Paragraph("Date de prédiction : " + datePrediction));

            // Ajouter la prédiction
            document.add(new Paragraph("Prix prédit : " + predictedPrice));

            // Ajouter l'estimation de l'erreur
            document.add(new Paragraph("Estimation de l'erreur : " + predictionError));

            // Fermer le document
            document.close();
            System.out.println("Le rapport PDF a été généré avec succès : " + fileName);
            // Ouvrir automatiquement le fichier PDF
            File pdfFile = new File(fileName);
            if (Desktop.isDesktopSupported() && pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Le fichier PDF ne peut pas être ouvert automatiquement.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

