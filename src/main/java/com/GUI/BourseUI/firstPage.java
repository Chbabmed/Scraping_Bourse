package com.GUI.BourseUI;

import com.GUI.BitcoinUI.AI;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class firstPage extends JFrame {

    public firstPage() {
        // --> Frame global configuration:
        this.setTitle("La bourse de Casablanca");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);
        this.setSize(600, 600);

        // --> Panel for vertical alignment:
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // --> Button for updating database:
        JButton btnUpdateDB = new JButton("Mise à jour de la base des données");
        btnUpdateDB.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUpdateDB.setPreferredSize(new Dimension(400, 30));
        btnUpdateDB.setMaximumSize(new Dimension(400, 30)); // Force size

        // --> Button for displaying chart:
        JButton btnDisplayChart = new JButton("Affichage de la charte");
        btnDisplayChart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDisplayChart.setPreferredSize(new Dimension(400, 30));
        btnDisplayChart.setMaximumSize(new Dimension(400, 30)); // Force size

        // --> Button for Prediction Page :
        JButton btnPrediction = new JButton("Prédiction AI");
        btnPrediction.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPrediction.setPreferredSize(new Dimension(400, 30));
        btnPrediction.setMaximumSize(new Dimension(400, 30)); // Force size

        // --> Adding buttons to panel:
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        panel.add(btnUpdateDB);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        panel.add(btnDisplayChart);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing
        panel.add(btnPrediction);

        // --> Adding panel to frame:
        this.add(panel);

        // --> Creation of the textarea to display status of operations :
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Définir la taille de la zone de text :
        Dimension dm = new Dimension();
        dm.setSize((int)(this.getWidth() *0.9), (int) (this.getHeight() * 0.2));
        textArea.setPreferredSize(dm);

        JPanel PanelTextArea = new JPanel();
        PanelTextArea.add(scrollPane);

        panel.add(PanelTextArea);

        // redirection of System.out To textArea :
        PrintStream ps = new PrintStream(new AI.CustomOutputStream(textArea));
        System.setOut(ps);
        System.setErr(ps);
        this.setLocationRelativeTo(null);// center the frame


    }

    public void display() {
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}