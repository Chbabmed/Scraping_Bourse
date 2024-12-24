package com.Project.DB;

import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;
import com.Project.Classes.transformActions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoadBourse {

    private static String LastDate = null;

    public static void setLastDate(String lastDate) {
        LastDate = lastDate;
    }

    public static void GetLastDate(String Inst) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        java.sql.Date tradeDate = null;

        // Correct SQL query syntax with the WHERE clause first, then ORDER BY and LIMIT
        String query = "SELECT * FROM BourseData WHERE company_Name = ? ORDER BY tradeDate DESC LIMIT 1";

        try {
            // Get the database connection from DB_Connect class
            connection = DB_Connect.connect();
            preparedStatement = connection.prepareStatement(query);

            // Set the company_Name parameter
            preparedStatement.setString(1, Inst);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the tradeDate from the result set
                    tradeDate = rs.getDate("tradeDate");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while retrieving data from database: " + e.getMessage());
        }

        String date = String.valueOf(tradeDate);
        setLastDate(date);
        System.out.println("Last date: " + LastDate);
        //return tradeDate;
    }

    public void loadBourseData(List<transformActions> data) {

        // Retrieve the last inserted date only once
        String RecentInsertedDate = this.LastDate;

        if (RecentInsertedDate == null) {
            RecentInsertedDate = "2009-01-01";
        }

        int insertedCount = 0;  // Counter for successful insertions

        for (transformActions record : data) {
            // Increment counter only if the data is successfully inserted
            if (BourseDataBaseHandler.InsertBourseData(record)) {
                insertedCount++;  // Increment the counter only for successful insertions
            }
        }

        // Display the number of successfully inserted rows
        System.out.println(insertedCount + " ligne(s) insérée(s).");
    }




}
