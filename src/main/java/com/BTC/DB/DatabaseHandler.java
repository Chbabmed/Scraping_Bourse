package com.BTC.DB;

import com.BTC.Classes.BitcoinData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/trading";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Constructeur pour l'établissement de la connection à la base de données
    public DatabaseHandler() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            System.out.println("connection à la base de donnée établie avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour insérer les lignes
    public boolean insertData(BitcoinData data, String lastDate) {
        try {
            // Convertir les chaînes de date en LocalDate
            LocalDate dataDate = LocalDate.parse(data.getDate(), DateTimeFormatter.ISO_DATE);
            LocalDate lastDateAsLocalDate = LocalDate.parse(lastDate, DateTimeFormatter.ISO_DATE);

            // Comparaison des dates en LocalDate
            if (dataDate.compareTo(lastDateAsLocalDate) <= 0) {
                //System.out.println("Data for " + data.getDate() + " already exists or is outdated. Skipping insert.");
                return false; // Skip insertion si la date n'est pas plus récente
            }
        } catch (DateTimeParseException e) {
            System.err.println("erreur de parsing date: " + e.getMessage());
            return false; // Si la date ne peut pas être convertie, on ignore l'insertion
        }

        // Code d'insertion
        String insertSQL = "INSERT INTO bitcoin_data (date, open, high, low, close, adj_close, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); 
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, data.getDate());
            pstmt.setDouble(2, data.getOpen());
            pstmt.setDouble(3, data.getHigh());
            pstmt.setDouble(4, data.getLow());
            pstmt.setDouble(5, data.getClose());
            pstmt.setDouble(6, data.getAdjClose());
            pstmt.setLong(7, data.getVolume());

            pstmt.executeUpdate();
            System.out.println("données pour " + data.getDate() + " insérées avec succès.");
            return true; // Indiquer l'insertion réussie
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner false en cas d'erreur
        }
    }

    // Méthode pour récupérer la dernière date insérée dans la base de données
    public String getLastInsertedDate() {
        String query = "SELECT date FROM bitcoin_data ORDER BY date DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                String lastDate = rs.getString("date");
                System.out.println("dernière date de mise à jour: " + lastDate);  
                return lastDate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // pas de données
    }

    // Méthode pour récupérer les données entre deux dates
    public List<BitcoinData> getDataBetweenDates(String startDate, String endDate) {
        List<BitcoinData> data = new ArrayList<>();

        String query = "SELECT * FROM bitcoin_data WHERE date BETWEEN ? AND ? ORDER BY date ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("date");
                    double open = rs.getDouble("open");
                    double high = rs.getDouble("high");
                    double low = rs.getDouble("low");
                    double close = rs.getDouble("close");
                    double adjClose = rs.getDouble("adj_close");
                    long volume = rs.getLong("volume");

                    data.add(new BitcoinData(date, open, high, low, close, adjClose, volume));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // méthode pour enregistrer les prédictions de tests dans la table test_predictions
    
    public void savePredictionToDatabase(String date, double predictedValue, double actualValue, String predictionErrorPercentage) {
        String insertSQL = "INSERT INTO test_predictions (date, predicted_value, actual_value, prediction_error_percentage) " +
                           "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, date);
            pstmt.setDouble(2, predictedValue);
            pstmt.setDouble(3, actualValue);
            pstmt.setString(4, predictionErrorPercentage);

            pstmt.executeUpdate();
            System.out.println("Prediction de test enregistrée dans la base de donnée pour la date: " + date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // méthode pour enregistrer les prédictions de tests dans la table production_predictions
    
    public void savePrediction(String date, double predictedValue) {
        String insertSQL = "INSERT INTO production_predictions (date, predicted_value) " +
                           "VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, date);
            pstmt.setDouble(2, predictedValue);
            pstmt.executeUpdate();
            System.out.println("Prediction enregistrée dans la base de donnée pour la date " + date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
 // Méthode pour récupérer la date de la dernière prédiction
    public String getPredictionDate() {
        String query = "SELECT date FROM production_predictions ORDER BY date DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    // Méthode pour récupérer la valeur de la dernière prédiction
    public double getPredictedPrice() {
        String query = "SELECT predicted_value FROM production_predictions ORDER BY date DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("predicted_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

 // Méthode pour récupérer la plus grande erreur de prédiction 
    public String getPredictionError() {
        String query = "SELECT MAX(prediction_error_percentage) AS max_error FROM test_predictions";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("max_error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    
 // Méthode pour tronquer la table test_predictions
    public void truncateTestPredictions() {
        String query = "TRUNCATE TABLE test_predictions";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate();
            System.out.println("La table test_predictions a été tronquée avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du truncate de la table test_predictions.");
        }
    }

}
