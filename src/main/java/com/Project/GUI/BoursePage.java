package com.Project.GUI;

import javax.swing.*;

public class BoursePage {
    public BoursePage() {
        // Créer la fenêtre pour Scrape Bourse
        JFrame bourseFrame = new JFrame("Scrape Bourse");
        bourseFrame.setSize(300, 150);
        bourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ajouter un label informatif
        JLabel label = new JLabel("Page Scrape Bourse", SwingConstants.CENTER);
        bourseFrame.add(label);

        // Rendre la fenêtre visible
        bourseFrame.setVisible(true);
    }
}