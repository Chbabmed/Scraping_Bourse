package com.GUI.BourseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class predictionUI extends JFrame {

    public predictionUI() {
        // Définir le titre de la fenêtre
        this.setTitle("Module AI");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));
        this.add(mainPanel, BorderLayout.CENTER);

        // Section 1 : Entraîner le modèle AI
        JPanel trainPanel = new JPanel(new GridLayout(2, 1));
        JLabel trainLabel = new JLabel("Entraîner le modèle AI en utilisant les données de la base");
        JPanel trainSubPanel = new JPanel(new FlowLayout());
        JButton trainButton = new JButton("Entraîner Modèle AI");
        JTextField trainFromField = new JTextField(5);
        JTextField trainToField = new JTextField(5);
        trainSubPanel.add(trainButton);
        trainSubPanel.add(new JLabel("de"));
        trainSubPanel.add(trainFromField);
        trainSubPanel.add(new JLabel("à"));
        trainSubPanel.add(trainToField);
        trainPanel.add(trainLabel);
        trainPanel.add(trainSubPanel);
        mainPanel.add(trainPanel);




        //-----------------------------> Btn action :
        trainButton.addActionListener(e -> {
            // Affichage de la boîte de dialogue de confirmation
            int response = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous entraîner un nouveau modèle et remplacer l'actuel ?",
                    "Confirmation Entraînement",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                // Logique d'entraînement
                String fromDate = trainFromField.getText().trim();
                String toDate = trainToField.getText().trim();

                if (fromDate.isEmpty() || toDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez entrer des dates valides (format yyyy-mm-dd).",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Entraînement du modèle AI en cours...\nDates: de " + fromDate + " à " + toDate,
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
















        // Section 2 : Tester le modèle AI
        JPanel testPanel = new JPanel(new GridLayout(2, 1));
        JLabel testLabel = new JLabel("Tester le modèle actuel en utilisant les données de la base");
        JPanel testSubPanel = new JPanel(new FlowLayout());
        JButton testButton = new JButton("Tester Modèle AI");
        JTextField testFromField = new JTextField(5);
        JTextField testToField = new JTextField(5);
        testSubPanel.add(testButton);
        testSubPanel.add(new JLabel("de"));
        testSubPanel.add(testFromField);
        testSubPanel.add(new JLabel("à"));
        testSubPanel.add(testToField);
        testPanel.add(testLabel);
        testPanel.add(testSubPanel);
        mainPanel.add(testPanel);

        // Section 3 : Prédire le prix
        JPanel predictPanel = new JPanel(new GridLayout(2, 1));
        JLabel predictLabel = new JLabel("Prédire le prix futur de clôture");
        JPanel predictSubPanel = new JPanel(new FlowLayout());
        JButton predictButton = new JButton("Prédire le prix et générer rapport de prédiction...");
        JTextField predictDateField = new JTextField(5);
        predictSubPanel.add(predictButton);
        predictSubPanel.add(new JLabel("du"));
        predictSubPanel.add(predictDateField);
        predictPanel.add(predictLabel);
        predictPanel.add(predictSubPanel);
        mainPanel.add(predictPanel);

        // Section 4 : Zone de texte pour les résultats
        JPanel resultPanel = new JPanel(new BorderLayout());
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        mainPanel.add(resultPanel);

        // Bouton de retour
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Retourner à la page d'accueil");


        // hta n9adha mzzn :
        //bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this.dispose();
                chooseInstru chooseInstru = new chooseInstru();
                chooseInstru.display();
            }

            private void dispose() {
                this.dispose();
            }
        });

    }

    public void display(){
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
