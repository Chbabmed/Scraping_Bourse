package com.Project.Predection;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileOutputStream;

public class PredictionPDFGenerator {

    // Méthode pour générer le rapport de prédiction
    public static void generateReport(double[][] predictions, String modelName,
                                      int JoursPrediction, String developerName, String Instru, double ecart) {
        Document document = new Document();

        try {
            // Création du fichier PDF
            PdfWriter.getInstance(document, new FileOutputStream("PredictionResultsReport.pdf"));

            // Ouvrir le document pour écrire
            document.open();

            // Ajouter un titre
            Paragraph title = new Paragraph("Rapport des Prédictions Boursières ",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            // Ajouter le développeur et le modèle utilisé
            document.add(new Paragraph("Instrument Choisi : " + Instru));
            document.add(new Paragraph("Développeur : " + developerName));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Modèle AI utilisé : " + modelName));
            document.add(new Paragraph("standard error : " + ecart));

            // Ajouter la date de prédiction
            document.add(new Paragraph("Jours pour faire prediction : " + JoursPrediction));

            // Ajouter un espace
            document.add(new Paragraph("\n"));

            // Ajouter un tableau des prédictions
            PdfPTable table = new PdfPTable(4); // 4 colonnes : Jour, Prédiction, Limite inférieure, Limite supérieure
            table.setWidthPercentage(100);

            // Ajouter les en-têtes de colonnes
            table.addCell("Jour");
            table.addCell("Prédiction");
            table.addCell("Limite Inférieure");
            table.addCell("Limite Supérieure");

            // Remplir les données des prédictions dans le tableau
            for (int i = 0; i < predictions.length; i++) {
                table.addCell("Jour " + (i + 1));
                table.addCell(String.format("%.2f", predictions[i][0]));
                table.addCell(String.format("%.2f", predictions[i][1]));
                table.addCell(String.format("%.2f", predictions[i][2]));
            }

            // Ajouter le tableau au document
            document.add(table);

            // Ajouter un espace pour la conclusion
            document.add(new Paragraph("\n"));

            // Ajouter une conclusion avec les informations finales
            document.add(new Paragraph("Rapport généré avec succès pour les prédictions des prochains "+JoursPrediction+" jours."));

            // Fermer le document
            document.close();
            System.out.println("Le rapport PDF des prédictions a été généré avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
