package com.GUI.BitcoinUI;

import com.BTC.DB.DatabaseHandler;

import javax.swing.*;
import java.awt.*;

public class AfficherCharte {

    public AfficherCharte() {

        // fenêtre
        JFrame frame = new JFrame("Bitcoin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // panneau 1
        JPanel panel1 = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(10); // Espacement vertical entre les composants
        panel1.setLayout(layout);

        // Création des champs de texte pour les dates
        JLabel l1 = new JLabel("de");
        JLabel l2 = new JLabel("à");
        JTextField dateDebut = new JTextField();
        JTextField dateFin = new JTextField();
        Dimension textFieldSize = new Dimension(80, 25); // Largeur 150px, hauteur 25px
        dateDebut.setPreferredSize(textFieldSize);
        dateFin.setPreferredSize(textFieldSize);


        // Panneau 2

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());


        //**********************************************************************************************************//
        //                                            BOUTTON (Afficher la charte)                                  //
        //                                                                                                          //
        //**********************************************************************************************************//

        JButton afficherButton = new JButton("Afficher le Charte");
        afficherButton.setPreferredSize(new Dimension(300, 30));
        afficherButton.setFocusPainted(false); // Supprimer lefocus



        // Ajouter action du bouton
        afficherButton.addActionListener(e -> {
            String startDate = dateDebut.getText().trim();
            String endDate = dateFin.getText().trim();

            if (startDate.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer des dates valides (format yyyy-mm-dd).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DatabaseHandler db = new DatabaseHandler();
            ShowChart chart = new ShowChart(db);
            // Appeler la méthode pour afficher la charte dans panel2
            chart.displayChart(panel2, startDate, endDate);
        });



        panel1.add(l1);
        panel1.add(dateDebut);
        panel1.add(l2);
        panel1.add(dateFin);
        panel1.add(afficherButton);

        //**********************************************************************************************************//
        //                                          PANNEAU retour à la page d'accueil                              //
        //                                                                                                          //
        //**********************************************************************************************************//

        JPanel panelAccueil = new JPanel();
        FlowLayout layoutAccueil = new FlowLayout();
        layoutAccueil.setHgap(10); // Espacement horizontal entre les composants
        panelAccueil.setLayout(layoutAccueil);

        JButton RetourButton = new JButton("Retouner à la page d'acceuil");
        RetourButton.setPreferredSize(new Dimension(300, 30));
        RetourButton.setFocusPainted(false); // Supprimer le focus
        panelAccueil.add(RetourButton);
        RetourButton.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            frame.dispose();
            // Créer une nouvelle instance de la classe voulue
            new InterfaceGraph(); // à remplacer par la page d'accueil principale
        });



        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panelAccueil, BorderLayout.SOUTH);
        // Rendre la fenêtre visible
        frame.setLocationRelativeTo(null); // Centre de l'écran
        frame.setVisible(true);
    }
}