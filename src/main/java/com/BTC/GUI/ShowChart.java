package com.BTC.GUI;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Day;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class ShowChart {
    private DatabaseHandler dbHandler;

    public ShowChart(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void displayChart(String startDate, String endDate) {
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

        JFrame frame = new JFrame("Bitcoin Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
