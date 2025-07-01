package com.carrental.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connect() {
        Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/car_rental_db";
            String user = "root";
            String password = "admin123";
            								// Change to your real password

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MySQL successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Connection Failed:");
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {
        connect(); // Testing the connection
    }
}
