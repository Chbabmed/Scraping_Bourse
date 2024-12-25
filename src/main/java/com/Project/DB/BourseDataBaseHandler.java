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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // les methodes ajouter par nour el yakine


    // méthode pour enregistrer les tests de prédiction
    public void savePredictionToDatabase(String date, String instrument, double predictedValue, double actualValue, String predictionErrorPercentage) {
        String insertSQL = "INSERT INTO test_predictions_Bourse (date, instrument, predicted_value, actual_value, prediction_error_percentage) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB_Connect.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, instrument);
            preparedStatement.setDouble(3, predictedValue);
            preparedStatement.setDouble(4, actualValue);
            preparedStatement.setString(5, predictionErrorPercentage);

            preparedStatement.executeUpdate();
            System.out.println("Prediction de test pour l'instrument : " + instrument + " enregistrée dans la base de données pour la date: " + date);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'enregistrement de la prédiction dans la base de données.");
        }
    }


    // méthode pour enregistrer les prédictions dans la table production_predictions_bourse

    public void savePrediction(String date, String instrument, double predictedValue) {
        String insertSQL = "INSERT INTO production_predictions_Bourse (date, instrument, predicted_value) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DB_Connect.connect();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {

            pstmt.setString(1, date);
            pstmt.setString(2, instrument);
            pstmt.setDouble(3, predictedValue);
            pstmt.executeUpdate();
            System.out.println("Prediction pour l'instrument : " + instrument + " enregistrée dans la base de donnée pour la date " + date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer la date de la dernière prédiction
    public String getPredictionDate(String instrument) {
        String query = "SELECT date FROM production_predictions_Bourse WHERE instrument = ? ORDER BY date DESC LIMIT 1";
        try (Connection conn = DB_Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // donner la valeur à instrument dana la requete
            pstmt.setString(1, instrument);

            // execution de la requete
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("date");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    // Méthode pour récupérer la valeur de la dernière prédiction
    public double getPredictedPrice(String instrument) {
        String query = "SELECT predicted_value FROM production_predictions_Bourse WHERE instrument = ? ORDER BY date DESC LIMIT 1";
        try (Connection conn = DB_Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // donner la valeur à instrument dana la requete
            pstmt.setString(1, instrument);

            // execution de la requete
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("predicted_value");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Méthode pour récupérer la plus grande erreur de prédiction
    public String getPredictionError(String instrument) {
        String query = "SELECT MAX(prediction_error_percentage) AS max_error FROM test_predictions_Bourse WHERE instrument = ?";
        try (Connection conn = DB_Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // donner la valeur à instrument dana la requete
            pstmt.setString(1, instrument);

            // execution de la requete
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("max_error");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    //  methode pour initialiser la table de test

    public static void truncateTestPredictionsTable() {
        String truncateSQL = "TRUNCATE TABLE test_predictions_Bourse";
        try (Connection connection = DB_Connect.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(truncateSQL)) {

            preparedStatement.executeUpdate();
            System.out.println("La table test_predictions_Bourse a été vidée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression des données dans la table test_predictions_Bourse.");
        }
    }


}
