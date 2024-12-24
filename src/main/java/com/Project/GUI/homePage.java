// Importation des packages nécessaires
package com.Project.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homePage {
    private JFrame mainFrame; // Déclarer mainFrame comme variable d'instance

    // Constructeur
    public homePage() {
        // Créer la fenêtre principale
        mainFrame = new JFrame("Main Interface");
        mainFrame.setSize(400, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2, 1));

        // Ajouter les boutons
        JButton scrapeBourseButton = new JButton("Scrape Bourse");
        JButton scrapeBTCButton = new JButton("Scrape BTC");

        // Définir la taille préférée des boutons
        scrapeBourseButton.setPreferredSize(new Dimension(100, 30));
        scrapeBTCButton.setPreferredSize(new Dimension(100, 30));

        // Réduire les marges internes des boutons
        scrapeBourseButton.setMargin(new Insets(5, 5, 5, 5));
        scrapeBTCButton.setMargin(new Insets(5, 5, 5, 5));

        // Ajouter les actions pour les boutons
        scrapeBourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BoursePage(); // Ouvre la page Scrape Bourse
                //return null;
            }
        });

        scrapeBTCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BTCPage(); // Ouvre la page Scrape BTC
                //return null;
            }
        });

        // Ajouter les boutons à des panneaux pour ajuster leur taille
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(scrapeBourseButton);
        panel2.add(scrapeBTCButton);

        // Ajouter les panneaux à la fenêtre principale
        mainFrame.add(panel1);
        mainFrame.add(panel2);
    }

    // Méthode pour afficher la fenêtre
    public void display() {
        mainFrame.setVisible(true);
    }
}
