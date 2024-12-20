package com.GUI.BitcoinUI;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Day;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

class ShowChart {
    private DatabaseHandler dbHandler;

    public ShowChart(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void displayChart(JPanel panel, String startDate, String endDate) {
        List<BitcoinData> bitcoinData = dbHandler.getDataBetweenDates(startDate, endDate);

        TimeSeries series = new TimeSeries("Bitcoin Prices");

        for (BitcoinData data : bitcoinData) {
            try {
                LocalDate date = LocalDate.parse(data.getDate());
                series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), data.getClose());
            } catch (Exception e) {
                System.err.println("erreur d'ajout des données à la charte: " + data.getDate());
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Charte du Prix du Bitcoin",
                "Date",
                "Prix (USD)",
                dataset,
                false,
                true,
                false
        );

        // Nettoyer le panel2 avant d'ajouter la nouvelle charte
        panel.removeAll();

        // Ajouter la nouvelle charte
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        // Revalider et actualiser l'affichage
        panel.revalidate();
        panel.repaint();
    }
}
