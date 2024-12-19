package com.Project.GUI;

import javax.swing.*;

public class BTCPage {
    public BTCPage() {
        // Créer la fenêtre pour Scrape BTC
        JFrame btcFrame = new JFrame("Scrape BTC");
        btcFrame.setSize(300, 150);
        btcFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ajouter un label informatif
        JLabel label = new JLabel("Page Scrape BTC", SwingConstants.CENTER);
        btcFrame.add(label);

        // Rendre la fenêtre visible
        btcFrame.setVisible(true);
    }
}
