package com.GUI.BourseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chooseInstru extends JFrame {

    //--> getter :
    public String getInstrumentChoisi() {
        return InstrumentChoisi;
    }

    //--> setter :
    public void setInstrumentChoisi(String instrumentChoisi) {
        InstrumentChoisi = instrumentChoisi;
    }

    private String InstrumentChoisi;

    public chooseInstru() {

        this.setTitle("Choose An Instrument");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);
        this.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // --> Button for updating database:
        JButton akditalBTN = new JButton("Scraper AKDITAL");
        akditalBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        akditalBTN.setPreferredSize(new Dimension(400, 30));
        akditalBTN.setMaximumSize(new Dimension(400, 30));

        // --> Button for displaying chart:
        JButton cihBTN = new JButton("Scraper CIH");
        cihBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        cihBTN.setPreferredSize(new Dimension(400, 30));
        cihBTN.setMaximumSize(new Dimension(400, 30));

        // --> Button for Prediction Page :
        JButton bcpBTN = new JButton("Scraper BCP");
        bcpBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
        bcpBTN.setPreferredSize(new Dimension(400, 30));
        bcpBTN.setMaximumSize(new Dimension(400, 30));

        // --> Adding buttons to panel:
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(akditalBTN);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(cihBTN);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(bcpBTN);

        // --> les Actions des buttons :
        // AKDITAL :
        akditalBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInstrumentChoisi("AKDITAL");
                firstPage akdiPage = new firstPage("AKDITAL");
                akdiPage.display();
            }
        });

        cihBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInstrumentChoisi("CIH");
                firstPage cihPage = new firstPage("CIH");
                cihPage.display();
            }
        });

        bcpBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInstrumentChoisi("BCP");
                firstPage bcpPage = new firstPage("BCP");
                bcpPage.display();
            }
        });


        // --> Adding panel to frame:
        this.add(panel);


    }


    public void display() {
        this.setVisible(true);
    }
}
