package com.carrental.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.carrental.db.DBConnector;

public class AddCarForm {

    public void showAddCarForm(Stage stage) {
        Label titleLabel = new Label("🚗 Add New Car");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d63384;");

        TextField modelField = new TextField();
        modelField.setPromptText("Car Model");

        TextField brandField = new TextField();
        brandField.setPromptText("Car Brand");

        TextField regNumberField = new TextField();
        regNumberField.setPromptText("Registration Number");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        TextField priceField = new TextField();
        priceField.setPromptText("Price per Day");

        Label messageLabel = new Label();

        Button addButton = new Button("➕ Add Car");
        addButton.setStyle(
            "-fx-background-color: #ffb6c1; -fx-text-fill: black; " +
            "-fx-background-radius: 8; -fx-font-weight: bold; -fx-padding: 6 16;"
        );

        addButton.setOnAction(e -> {
            String model = modelField.getText();
            String brand = brandField.getText();
            String regNumber = regNumberField.getText();
            int year;
            double price;

            if (model.isEmpty() || brand.isEmpty() || regNumber.isEmpty() ||
                yearField.getText().isEmpty() || priceField.getText().isEmpty()) {
                messageLabel.setText("❌ All fields are required.");
                return;
            }

            try {
                year = Integer.parseInt(yearField.getText());
                price = Double.parseDouble(priceField.getText());

                Connection conn = DBConnector.connect();
                if (conn == null) {
                    messageLabel.setText("❌ Failed to connect to database.");
                    return;
                }

                String query = "INSERT INTO cars (model, brand, registration_number, year, price_per_day) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, model);
                stmt.setString(2, brand);
                stmt.setString(3, regNumber);
                stmt.setInt(4, year);
                stmt.setDouble(5, price);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    messageLabel.setText("✅ Car added successfully!");
                    modelField.clear();
                    brandField.clear();
                    regNumberField.clear();
                    yearField.clear();
                    priceField.clear();
                }

                conn.close();
            } catch (NumberFormatException nfe) {
                messageLabel.setText("❌ Please enter valid year and price.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error adding car.");
            }
        });

        Button backButton = new Button("⬅ Back");
        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.showDashboard(stage);
        });

        VBox layout = new VBox(10,
                titleLabel, modelField, brandField, regNumberField,
                yearField, priceField, addButton, messageLabel, backButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 25; -fx-background-color: #fff0f5;");

        Scene scene = new Scene(layout, 370, 460);
        stage.setScene(scene);
        stage.setTitle("Add Car - Car Rental System");
        stage.show();
    }
}
