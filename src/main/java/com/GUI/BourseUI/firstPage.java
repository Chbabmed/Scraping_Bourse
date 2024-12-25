package com.GUI.BourseUI;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.BTC.DB.Load;
import com.BTC.Transformers.TransformData;
import com.Project.Classes.transformActions;
import com.GUI.BitcoinUI.AI;
import com.Project.DB.BourseDataBaseHandler;
import com.Project.DB.LoadBourse;
import com.Project.Scrapers.BourseScraper;
import com.Project.Transformers.BourseTransformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.List;

public class firstPage extends JFrame {

    private String InstrumentChoisi;

    public firstPage(String InstrumentChoisi) {
        // --> Frame global configuration:
        this.setTitle(InstrumentChoisi);
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


        //----------------------------------------- BTN Actions :
        btnUpdateDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Affichage de la boîte de dialogue de confirmation
                int response = JOptionPane.showConfirmDialog(firstPage.this,
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

                        String date = null;
                        List<String[]> dirthyData;

                        LoadBourse.GetLastDate(InstrumentChoisi);

                        BourseScraper Bs = new BourseScraper();
                        dirthyData = Bs.BScraper(InstrumentChoisi);
                        List<transformActions> cleanData = BourseTransformer.TransformData(dirthyData);

                        LoadBourse loader = new LoadBourse();
                        loader.loadBourseData(cleanData);



                    } catch (Exception err) {
                        System.out.println(" ");
                    }
                } else {
                    // Action si l'utilisateur annule
                    JOptionPane.showMessageDialog(firstPage.this, "Opération annulée.");
                }
            };
        });

        btnDisplayChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BourseChart Bs = new BourseChart(InstrumentChoisi);
                Bs.display();
            }
        });

        // button de prediction :
        btnPrediction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predictionUI p = new predictionUI(InstrumentChoisi);
                p.display();
            }
        });

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
        Dimension dm = new Dimension(500, 350);
        //dm.setSize((int)(this.getWidth() *0.9), (int) (this.getHeight() * 0.2));
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