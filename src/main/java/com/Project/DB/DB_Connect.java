package com.Project.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect {

    private static String url = "jdbc:mysql://localhost:3306/bourse_scraping";
    private static String username = "root";
    private static String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
