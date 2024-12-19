package com.GUI.BitcoinUI;

import javax.swing.*;
import java.awt.*;

public class BitcoinPage extends JFrame {

    public BitcoinPage() {
        this.setTitle("Bitcoin Page");
        this.setSize(1080, 720);
        this.setLayout(new BorderLayout());

        // Add a label with a message
        JLabel message = new JLabel("Bienvenue sur la page Bitcoin", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));

        // Load the image
        ImageIcon icon = new ImageIcon("src/main/assets/Bitcoin-Logo.png");
        Image logo = icon.getImage();
        Image scaledLogo = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon resizedIcon = new ImageIcon(scaledLogo);

        // Create a label for the image
        JLabel imageLabel = new JLabel(resizedIcon, SwingConstants.CENTER);

        // Add the image and message to the frame
        this.add(imageLabel, BorderLayout.NORTH); // Add the image at the top
        this.add(message, BorderLayout.CENTER);   // Add the message in the center
    }

    public void display() {
        this.setVisible(true);
    }
}
