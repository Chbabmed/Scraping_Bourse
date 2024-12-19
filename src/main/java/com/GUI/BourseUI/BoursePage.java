package com.GUI.BourseUI;

import javax.swing.*;
import java.awt.*;

public class BoursePage extends JFrame {

    public BoursePage() {
        // Configurer la fenêtre de la page "Bourse"
        this.setTitle("Bourse Page");
        this.setSize(1080, 720);
        this.setLayout(new BorderLayout());

        JLabel message = new JLabel("Bienvenue sur la page Bourse", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));

        this.add(message, BorderLayout.CENTER);



    }

    // Méthode pour afficher la page
    public void display() {
        this.setVisible(true);
    }
}
