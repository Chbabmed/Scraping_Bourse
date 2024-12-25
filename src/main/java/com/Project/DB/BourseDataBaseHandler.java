package com.Project.DB;

import com.Project.Classes.transformActions;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BourseDataBaseHandler {

    /*************************************
    * First Function :
    * --> insertion of data into database :
    *
     * */

    public static boolean InsertBourseData(transformActions data) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean status = false;



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

            // Execute the query :

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0 ) {
                System.out.println("A new row was inserted successfully.");
                status = true;
            }

        } catch (SQLException e) {
            //System.err.println("Error while inserting data: " + e.getMessage());
            status = false;
        }
        return status;
    }

    /*************************************
     * Second Function :
     * --> Getting data from database :
     * */

    public static List<transformActions> GetAllBourseData(String StartDate, String EndDate, String instrument) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<transformActions> data = new ArrayList<transformActions>();

        // converting data to appropriate format :
        java.sql.Date sqldateStart = null;
        java.sql.Date sqldateEnd = null;
        sqldateStart = convertDateToSQL(StartDate, "yyyy-MM-dd");
        sqldateEnd = convertDateToSQL(EndDate, "yyyy-MM-dd");


        String query = "SELECT * FROM BourseData WHERE company_Name = ? AND  tradeDate BETWEEN ? AND ? ORDER BY tradeDate DESC";
        try{
            connection = DB_Connect.connect();
            preparedStatement = connection.prepareStatement(query);
            try {
                preparedStatement.setString(1, instrument);
                preparedStatement.setDate(2, sqldateStart);
                preparedStatement.setDate(3, sqldateEnd);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String tradeDate = rs.getString("tradeDate");
                    String company_Name = rs.getString("company_Name");
                    String ticker = rs.getString("ticker");
                    double openingPrice = rs.getDouble("openingPrice");
                    double closingPrice = rs.getDouble("closingPrice");
                    double highPrice = rs.getDouble("hightPrice");
                    double lowPrice = rs.getDouble("lowPrice");
                    int NumberOfSecuritiestTraded = rs.getInt("NumberOfSecuritiestTraded");
                    int Volume = rs.getInt("Volume");
                    int NumberOfTransactions = rs.getInt("NumberOfTransactions");
                    double Capitalization = rs.getDouble("Capitalization");
                    data.add(new transformActions(tradeDate,company_Name,ticker,openingPrice,closingPrice,
                            highPrice, lowPrice, Volume, NumberOfSecuritiestTraded, NumberOfTransactions, Capitalization ));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Error while retrieving data from database: " + e.getMessage());
            return null;
        }

        return data;
    }

    // function to convert string to date format :
    public static java.sql.Date convertDateToSQL(String date, String inputFormat) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return java.sql.Date.valueOf(parsedDate); // Converts LocalDate to java.sql.Date
    }



}
