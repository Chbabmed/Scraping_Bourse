package com.GUI.BitcoinUI;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.DB.Load;
import com.BTC.Scrapers.Scrap;
import com.BTC.Transformers.TransformData;

import java.awt.*;
import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class InterfaceGraph {

    // Constructeur de la classe InterfaceGraph
    public InterfaceGraph() {
        // fenêtre
        JFrame frame = new JFrame("Bitcoin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(500, 30);
        int spacing = 10; // Espacement vertical en pixels

        // panneau conteneur 1
        JPanel panelConteneur1 = new JPanel();
        panelConteneur1.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.25)));
        panelConteneur1.add(panel, BorderLayout.CENTER);

        // les boutons

        //**********************************************************************************************************//
        //                                            BOUTTON1  (mise à jour)                                       //
        //                                                                                                          //
        //**********************************************************************************************************//

        JButton button1 = new JButton("Mise à jour de la base de données");
        button1.setMaximumSize(buttonSize); // réglage des dimensions du bouton
        button1.setFocusPainted(false); // Supprimer l'effet de focus
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue()); // Colle pour centrer verticalement
        panel.add(Box.createVerticalStrut(spacing)); // Espacement
        panel.add(button1);
        button1.addActionListener(e -> {
            // Affichage de la boîte de dialogue de confirmation
            int response = JOptionPane.showConfirmDialog(frame,
                    "Voulez-vous mettre à jour la base de données du Bitcoin ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Vérifier la réponse
            if (response == JOptionPane.YES_OPTION) {
                // Action à réaliser si l'utilisateur confirme
                // crap + transform + load

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

                } catch (Exception err) {
                    err.printStackTrace();
                }
            } else {
                // Action si l'utilisateur annule
                JOptionPane.showMessageDialog(frame, "Opération annulée.");
            }
        });


        //**********************************************************************************************************//
        //                                            BOUTTON2                                                      //
        //                                                                                                          //
        //**********************************************************************************************************//


        JButton button2 = new JButton("Affichage de la Charte");
        button2.setMaximumSize(buttonSize); // réglage des dimensions du bouton
        button2.setFocusPainted(false); // Supprimer l'effet de focus
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(spacing)); // Espacement
        panel.add(button2);
        button2.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            frame.dispose();

            // Créer une nouvelle instance de la classe CharteVariations
            new AfficherCharte();
        });
        panel.add(button2);



        //**********************************************************************************************************//
        //                                            BOUTTON3                                                      //
        //                                                                                                          //
        //**********************************************************************************************************//


        JButton button3 = new JButton("Prédiction AI");
        button3.setMaximumSize(buttonSize); // réglage des dimensions du bouton
        button3.setFocusPainted(false); // Supprimer l'effet de focus
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(spacing)); // Espacement
        panel.add(button3);
        button3.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            frame.dispose();

            // Créer une nouvelle instance de la classe graphique AI
            new AI();
        });


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
        scrollPane.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.9), (int) (frame.getHeight() * 0.4)));


        // panneau conteneur 2
        JPanel panelConteneur2 = new JPanel();
        panelConteneur2.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.5)));
        panelConteneur2.add(scrollPane, BorderLayout.CENTER);


        // Rediriger System.out vers le JTextField
        PrintStream printStream;
        try {
            printStream = new PrintStream(new CustomOutputStream(ta), true, "UTF-8");
            System.setOut(printStream);
            System.setErr(printStream);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //**********************************************************************************************************//
        //                                          PANNEAU retour à la page d'accueil                              //
        //                                                                                                          //
        //**********************************************************************************************************//

        JPanel panelAccueil = new JPanel();
        FlowLayout layoutAccueil = new FlowLayout();
        layoutAccueil.setHgap(0); // Espacement horizontal entre les composants
        panelAccueil.setLayout(layoutAccueil);
        panelAccueil.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.25)));

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

        //***********************************************************************************************************//

        // Ajouter le panneau à la fenêtre
        frame.add(panelConteneur1,BorderLayout.NORTH);
        frame.add(panelConteneur2, BorderLayout.CENTER);
        frame.add(panelAccueil,BorderLayout.SOUTH);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }

    // Classe interne pour rediriger le System.out vers JTextField
    private static class CustomOutputStream extends OutputStream {
        private  JTextArea ta;

        public CustomOutputStream(JTextArea textArea) {
            this.ta = textArea;
        }

        @Override
        public void write(int b) {
            // Append the character to the text area
            ta.append(String.valueOf((char) b));
            ta.setCaretPosition(ta.getDocument().getLength()); // Scroll automatically
        }
    }
}