package com.GUI.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.GUI.BitcoinUI.BitcoinPage;
import com.GUI.BourseUI.BoursePage;
import com.GUI.BourseUI.firstPage;

public class HomePage extends JFrame {

    public HomePage() {
        // Configurer la fenêtre principale
        this.setTitle("Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2, 1)); // Deux sections : Bourse et BTC

        // SECTION 1 : Bourse
        JPanel boursePanel = new JPanel();
        boursePanel.setLayout(new BoxLayout(boursePanel, BoxLayout.Y_AXIS));
        boursePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Bordure

        // Load the "bourse" logo
        ImageIcon bourseIcon = new ImageIcon("src/main/assets/bourse casa.png");
        Image bourseLogoImage = bourseIcon.getImage();
        Image scaledBourseLogo = bourseLogoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon resizedBourseIcon = new ImageIcon(scaledBourseLogo);

        JLabel bourseLogo = new JLabel(resizedBourseIcon, SwingConstants.CENTER);
        bourseLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton bourseButton = new JButton("Scrape Bourse");
        bourseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bourseButton.setForeground(new Color(128, 0, 0)); // Couleur maroon

        // Ajouter une action au bouton "scrape Bourse"
        bourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Naviguer vers la nouvelle page
                firstPage firstPage = new firstPage();
                firstPage.display();
            }
        });

        boursePanel.add(Box.createVerticalGlue());
        boursePanel.add(bourseLogo); // Add the image instead of text
        boursePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement
        boursePanel.add(bourseButton);
        boursePanel.add(Box.createVerticalGlue());

        // SECTION 2 : BTC
        JPanel btcPanel = new JPanel();
        btcPanel.setLayout(new BoxLayout(btcPanel, BoxLayout.Y_AXIS));
        btcPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Bordure

        // Load the Bitcoin logo
        ImageIcon btcIcon = new ImageIcon("src/main/assets/Bitcoin-Logo.png");
        Image logoBtc = btcIcon.getImage();
        Image scaledBTC = logoBtc.getScaledInstance(100, 56, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon resizedBTCIcon = new ImageIcon(scaledBTC);

        JLabel btcLogo = new JLabel(resizedBTCIcon, SwingConstants.CENTER);
        btcLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btcButton = new JButton("Scrape Yahoo Finance");
        btcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        btcButton.setForeground(new Color(128, 0, 0)); // Couleur maroon

        // Ajouter une action au bouton "Scrape Yahoo Finance"
        btcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BitcoinPage bitcoinPage = new BitcoinPage();
                bitcoinPage.display();
            }
        });

        btcPanel.add(Box.createVerticalGlue());
        btcPanel.add(btcLogo); // Add the image instead of text
        btcPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement
        btcPanel.add(btcButton);
        btcPanel.add(Box.createVerticalGlue());

        // Ajouter les sections à la fenêtre principale
        this.add(boursePanel);
        this.add(btcPanel);
    }

    // Méthode pour afficher la fenêtre
    public void display() {
        this.setVisible(true);
    }
}
