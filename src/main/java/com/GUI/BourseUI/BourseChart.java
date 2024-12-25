package com.GUI.BourseUI;

import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BourseChart extends JFrame {

    private JTextField startDateField;
    private JTextField endDateField;
    private JButton displayButton;

    public BourseChart(String instrument) {
        this.setTitle(instrument + " Chart");
        this.setSize(800, 600);  // Set the size of the frame

        // Set up the panel for date inputs and button (top panel)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // Start Date Input
        JLabel startDateLabel = new JLabel("Start Date (yyyy-MM-dd): ");
        startDateField = new JTextField(10);
        topPanel.add(startDateLabel);
        topPanel.add(startDateField);

        // End Date Input
        JLabel endDateLabel = new JLabel("End Date (yyyy-MM-dd): ");
        endDateField = new JTextField(10);
        topPanel.add(endDateLabel);
        topPanel.add(endDateField);

        // Button to display the chart
        displayButton = new JButton("Afficher la Chart");
        topPanel.add(displayButton);

        // Add top panel to the frame
        this.add(topPanel, BorderLayout.NORTH);

        // Add action listener to the button
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Here you can implement the logic to fetch and display the chart based on dates
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();

                // Call a method to generate the chart based on the dates
                JFreeChart chart = createChart(startDate, endDate);
                displayChart(chart);
            }
        });
    }

    // Method to create a chart
    private JFreeChart createChart(String startDate, String endDate) {
        // Initialize the dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Create an instance of your database handler to fetch the data
        BourseDataBaseHandler db = new BourseDataBaseHandler();

        // Fetch the data for the given date range
        List<transformActions> data = db.GetAllBourseData(startDate, endDate);

        // Use a Map to avoid duplicate dates
        Map<String, Double> aggregatedData = new HashMap<>();

        for (transformActions action : data) {
            String tradeDate = action.getTradeDate();
            double closingPrice = action.getClosingPrice();

            // Pour enregister just le plus haut price de jour iterated :
            aggregatedData.put(tradeDate, Math.max(aggregatedData.getOrDefault(tradeDate, 0.0), closingPrice));
        }

        // Ajouter aggregated data to the dataset pour l'afficher dans la chart :
        for (Map.Entry<String, Double> entry : aggregatedData.entrySet()) {
            dataset.addValue(entry.getValue(), "ClosingPrice", entry.getKey());
        }


        // Create the chart with the dataset
        JFreeChart chart = ChartFactory.createLineChart(
                "Bourse Chart",              // Chart title
                "Date",                      // X-axis label
                "Closing Price",                     // Y-axis label
                dataset,                     // Dataset
                PlotOrientation.VERTICAL,    // Plot orientation
                true,                        // Include legend
                true,                        // Tooltips
                false                        // URLs
        );

        return chart;
    }


    // Method to display the chart
    private void displayChart(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(780, 400)); // Set the chart panel size
        this.add(chartPanel, BorderLayout.CENTER);  // Add chart panel to the frame
        this.revalidate();  // Refresh the frame
    }

    public void display() {
        this.setVisible(true);
    }

}
