package com.GUI.BitcoinUI;

import com.BTC.DB.DatabaseHandler;
import com.BTC.Prediction.IssueReportPrediction;
import com.BTC.Prediction.PredictPrice;
import com.BTC.Prediction.TestPredictionPrice;
import com.BTC.Prediction.Train_AI_Model;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class AI {

    public AI() {

        // fenêtre
        JFrame frame = new JFrame("Module AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);  // Augmenté pour accommoder la zone de texte
        frame.setLocationRelativeTo(null);

        // Panneau principal (pour organiser les panneaux verticaux)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        mainPanel.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.55)));


        //**********************************************************************************************************//
        //                                      PANNEAU d'entrainement du modèle                                    //
        //                                                                                                          //
        //**********************************************************************************************************//
        JPanel panel1 = new JPanel();
        FlowLayout layout1 = new FlowLayout();
        layout1.setHgap(10); // Espacement horizontal entre les composants
        panel1.setLayout(layout1);

        // Création des champs de texte pour les dates
        JLabel l11 = new JLabel("de");
        JLabel l12 = new JLabel("à");
        JTextField dateDebut1 = new JTextField();
        JTextField dateFin1 = new JTextField();
        Dimension textFieldSize = new Dimension(80, 25); // Largeur 80px, hauteur 25px
        dateDebut1.setPreferredSize(textFieldSize);
        dateFin1.setPreferredSize(textFieldSize);

        JButton entrainerButton = new JButton("Entrainer Modèle AI");
        entrainerButton.setPreferredSize(new Dimension(300, 30));
        entrainerButton.setFocusPainted(false); // Supprimer le focus
        panel1.add(entrainerButton);
        entrainerButton.addActionListener(e -> {
            // Affichage de la boîte de dialogue de confirmation
            int response = JOptionPane.showConfirmDialog(frame,
                    "Voulez-vous entrainer un autre modèle et remplacer le modèle entrainé actuel",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Vérifier la réponse
            if (response == JOptionPane.YES_OPTION) {
                // Action à réaliser si l'utilisateur confirme

                try {

                    String startDate1 = dateDebut1.getText().trim();
                    String endDate1 = dateFin1.getText().trim();

                    if (startDate1.isEmpty() || endDate1.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Veuillez entrer des dates valides (format yyyy-mm-dd).", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // entrainement du modèle ARIMA (par exemple en utilisant les données du 2024-01-01 au 2024-10-31)

                    DatabaseHandler db = new DatabaseHandler();
                    Train_AI_Model trainer = new Train_AI_Model(db);
                    trainer.trainModel(startDate1, endDate1);

                } catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                // Action si l'utilisateur annule
                JOptionPane.showMessageDialog(frame, "Opération annulée.");
            }
        });
        panel1.add(l11);
        panel1.add(dateDebut1);
        panel1.add(l12);
        panel1.add(dateFin1);


        //**********************************************************************************************************//
        //                                          PANNEAU de test du modèle                                       //
        //                                                                                                          //
        //**********************************************************************************************************//

        JPanel panel2 = new JPanel();
        FlowLayout layout2 = new FlowLayout();
        layout2.setHgap(10); // Espacement horizontal entre les composants
        panel2.setLayout(layout2);

        // Création des champs de texte pour les dates
        JLabel l21 = new JLabel("de");
        JLabel l22 = new JLabel("à");
        JTextField dateDebut2 = new JTextField();
        JTextField dateFin2 = new JTextField();
        Dimension textFieldSize2 = new Dimension(80, 25);
        dateDebut2.setPreferredSize(textFieldSize2);
        dateFin2.setPreferredSize(textFieldSize2);

        JButton testerButton = new JButton("Tester Modèle AI");
        testerButton.setPreferredSize(new Dimension(300, 30));
        testerButton.setFocusPainted(false); // Supprimer le focus
        panel2.add(testerButton);

        testerButton.addActionListener(e -> {
            // Affichage de la boîte de dialogue de confirmation
            int response = JOptionPane.showConfirmDialog(frame,
                    "Voulez-vous tester le modèle actuel",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Vérifier la réponse
            if (response == JOptionPane.YES_OPTION) {
                // Action à réaliser si l'utilisateur confirme

                try {

                    String startDate2 = dateDebut2.getText().trim();
                    String endDate2 = dateFin2.getText().trim();

                    if (startDate2.isEmpty() || endDate2.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Veuillez entrer des dates valides (format yyyy-mm-dd).", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // test du modèle

                    DatabaseHandler db = new DatabaseHandler();
                    TestPredictionPrice test_predictor = new TestPredictionPrice(db);
                    test_predictor.predictPriceForDateRange(startDate2,endDate2);

                } catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                // Action si l'utilisateur annule
                JOptionPane.showMessageDialog(frame, "Opération annulée.");
            }
        });
        panel2.add(l21);
        panel2.add(dateDebut2);
        panel2.add(l22);
        panel2.add(dateFin2);


        //**********************************************************************************************************//
        //                                           PANNEAU de prédiction                                          //
        //                                                                                                          //
        //**********************************************************************************************************//

        JPanel panel3 = new JPanel();
        FlowLayout layout3 = new FlowLayout();
        layout3.setHgap(10); // Espacement horizontal entre les composants
        panel3.setLayout(layout3);

        // Création des champs de texte pour les dates
        JLabel l31 = new JLabel("du");
        JTextField date = new JTextField();
        Dimension textFieldSize3 = new Dimension(80, 25);
        date.setPreferredSize(textFieldSize3);


        JButton PredireButton = new JButton("Prédire le prix et générer rapport de prédiction");
        PredireButton.setPreferredSize(new Dimension(300, 30));
        PredireButton.setFocusPainted(false); // Supprimer le focus
        panel3.add(PredireButton);
        PredireButton.addActionListener(e -> {
            // Affichage de la boîte de dialogue de confirmation
            int response = JOptionPane.showConfirmDialog(frame,
                    "Voulez-vous effectuer une prédiction?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Vérifier la réponse
            if (response == JOptionPane.YES_OPTION) {
                // Action à réaliser si l'utilisateur confirme

                try {

                    String datePrediction = date.getText().trim();

                    if (datePrediction.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Veuillez entrer une date valide (format yyyy-mm-dd).", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // prédiction et génération de rapport pdf

                    DatabaseHandler db = new DatabaseHandler();
                    // prédire le prix de cloture de la date donnée
                    PredictPrice predictor = new PredictPrice(db);
                    predictor.predictPrice(datePrediction);


                    // générer un rapport de prédiction
                    IssueReportPrediction report = new IssueReportPrediction(db);
                    report.generateReport();

                } catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                // Action si l'utilisateur annule
                JOptionPane.showMessageDialog(frame, "Opération annulée.");
            }
        });
        panel3.add(l31);
        panel3.add(date);


        //panneau des titre
        JPanel panelTitre1 = new JPanel();
        JLabel titre1 = new JLabel("Entrainer le model AI en utilisant les données de la base");
        panelTitre1.add(titre1);
        JPanel panelTitre2 = new JPanel();
        JLabel titre2 = new JLabel("Tester le model actuel en utilisant les données de la base");
        panelTitre2.add(titre2);
        JPanel panelTitre3 = new JPanel();
        JLabel titre3 = new JLabel("Prédire le prix future de cloture");
        panelTitre3.add(titre3);


        // Ajouter les panneaux à mainPanel pour les organiser verticalement
        mainPanel.add(panelTitre1);
        mainPanel.add(panel1);
        mainPanel.add(panelTitre2);
        mainPanel.add(panel2);
        mainPanel.add(panelTitre3);
        mainPanel.add(panel3);

        //**********************************************************************************************************//
        //                                            ZONE DE TEXTE                                                 //
        //                                                                                                          //
        //**********************************************************************************************************//
        // la zone de texte
        JTextArea ta = new JTextArea();
        ta.setEditable(false); // Empêche la modification par l'utilisateur
        ta.setLineWrap(true);  // Retour automatique à la ligne
        ta.setWrapStyleWord(true); // Retour à la ligne au mot

        // Barre de défilement
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.9), (int) (frame.getHeight() * 0.2)));


        // panneau conteneur
        JPanel panelConteneur = new JPanel();
        panelConteneur.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.30)));
        panelConteneur.add(scrollPane, BorderLayout.CENTER);

        //**********************************************************************************************************//
        //                                          PANNEAU retour à la page d'accueil                              //
        //                                                                                                          //
        //**********************************************************************************************************//

        JPanel panelAccueil = new JPanel();
        FlowLayout layoutAccueil = new FlowLayout();
        layoutAccueil.setHgap(10); // Espacement horizontal entre les composants
        panelAccueil.setLayout(layoutAccueil);
        panelAccueil.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.15)));

        JButton RetourButton = new JButton("Retouner à la page d'acceuil");
        RetourButton.setPreferredSize(new Dimension(300, 30));
        RetourButton.setFocusPainted(false); // Supprimer le focus
        panelAccueil.add(RetourButton);
        RetourButton.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            frame.dispose();
            // Créer une nouvelle instance de la classe volue
            new InterfaceGraph(); // à remplacer par la page d'accueil principale
        });



        // Ajouter mainPanel à la fenêtre
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(panelConteneur, BorderLayout.CENTER);
        frame.add(panelAccueil, BorderLayout.SOUTH);



        // Rediriger System.out vers le JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(ta));
        System.setOut(printStream);
        System.setErr(printStream);

        // Rendre la fenêtre visible
        frame.setLocationRelativeTo(null); // Centrer sur l'écran
        frame.setVisible(true);
    }

    // Classe interne pour rediriger le System.out vers JTextArea
    public static class CustomOutputStream extends OutputStream {
        private JTextArea ta;

        public CustomOutputStream(JTextArea textArea) {
            this.ta = textArea;
        }

        @Override
        public void write(int b) {
            ta.append(String.valueOf((char) b));
            ta.setCaretPosition(ta.getDocument().getLength()); // Scroll automatiquement
        }
    }
}

