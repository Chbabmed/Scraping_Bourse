package com.GUI.BitcoinUI;

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

        // panneau 1
        JPanel panel1 = new JPanel();
        FlowLayout layout1 = new FlowLayout();
        layout1.setHgap(10); // Espacement horizontal entre les composants
        panel1.setLayout(layout1);

        // Création des champs de texte pour les dates
        JLabel l11 = new JLabel("de");
        JLabel l12 = new JLabel("à");
        JTextField dateDebut = new JTextField();
        JTextField dateFin = new JTextField();
        Dimension textFieldSize = new Dimension(80, 25); // Largeur 80px, hauteur 25px
        dateDebut.setPreferredSize(textFieldSize);
        dateFin.setPreferredSize(textFieldSize);

        JButton entrainerButton = new JButton("Entrainer Modèle AI");
        entrainerButton.setPreferredSize(new Dimension(300, 30));
        entrainerButton.setFocusPainted(false); // Supprimer le focus
        panel1.add(entrainerButton);
        panel1.add(l11);
        panel1.add(dateDebut);
        panel1.add(l12);
        panel1.add(dateFin);

        // panneau 2
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
        panel2.add(l21);
        panel2.add(dateDebut2);
        panel2.add(l22);
        panel2.add(dateFin2);

        // panneau 3
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

        ////
        //                                            ZONE DE TEXTE                                                 //
        //                                                                                                          //
        ////
        // Création de la zone de texte
        JTextArea ta = new JTextArea();
        ta.setEditable(false); // Empêche la modification par l'utilisateur
        JScrollPane scrollPane = new JScrollPane(ta);

        // Définir la taille de la zone de texte
        Dimension dm = new Dimension();
        dm.setSize((int)(frame.getWidth() * 0.9), (int)(frame.getHeight() * 0.2));
        ta.setPreferredSize(dm);

        // Panneau pour la zone de texte
        JPanel textPanel = new JPanel();
        textPanel.add(scrollPane);

        // Ajouter la zone de texte au panneau principal
        mainPanel.add(textPanel);

        // Ajouter mainPanel à la fenêtre
        frame.add(mainPanel, BorderLayout.CENTER);

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
