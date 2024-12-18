package com.Project.DB;

import com.Project.Classes.transformActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class BourseDataBaseHandler {

    public static void InsertBourseData(transformActions data) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertQuery = "INSERT INTO BourseData (tradeDate, company_Name, ticker, openingPrice, closingPrice, " +
                "hightPrice, lowPrice, NumberOfSecuritiestTraded, volume, NumberOfTransactions, capitalization) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Get the database connection from DB_connect class
            connection = DB_Connect.connect();
            preparedStatement = connection.prepareStatement(insertQuery);

            Date Formatted_date = new Date();



            // Set parameters for the prepared statement
            preparedStatement.setString(1, data.getTradeDate());
            preparedStatement.setString(2, data.getCompany_Name());
            preparedStatement.setString(3, data.getTicker());
            preparedStatement.setDouble(4, data.getOpeningPrice());
            preparedStatement.setDouble(5, data.getClosingPrice());
            preparedStatement.setDouble(6, data.getHightPrice());
            preparedStatement.setDouble(7, data.getLowPrice());
            preparedStatement.setDouble(8, data.getNumberOfSecuritiestTraded());
            preparedStatement.setDouble(9, data.getVolume());
            preparedStatement.setInt(10, data.getNumberOfTransactions());
            preparedStatement.setDouble(11, data.getCapitalization());

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new row was inserted successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error while inserting data: " + e.getMessage());
        }
    }
}
