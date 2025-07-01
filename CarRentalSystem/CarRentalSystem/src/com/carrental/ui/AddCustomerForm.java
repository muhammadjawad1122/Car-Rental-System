package com.carrental.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.carrental.db.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomerForm {

    public void showAddCustomerForm(Stage stage) {
        Label titleLabel = new Label("🧍‍♂️ Add New Customer");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d63384;");

        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");
        nameField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #f783ac;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #f783ac;");

        TextField licenseField = new TextField();
        licenseField.setPromptText("License Number");
        licenseField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #f783ac;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button addButton = new Button("➕ Add Customer");
        addButton.setStyle(
            "-fx-background-color: #ff69b4; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 8 20 8 20; " +
            "-fx-cursor: hand;"
        );

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String license = licenseField.getText();

            if (name.isEmpty() || phone.isEmpty() || license.isEmpty()) {
                messageLabel.setText("❌ Please fill in all fields.");
                return;
            }

            try {
                Connection conn = DBConnector.connect();
                if (conn == null) {
                    messageLabel.setText("❌ Database connection failed.");
                    return;
                }

                String query = "INSERT INTO customers (name, phone, license_number) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, phone);
                stmt.setString(3, license);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText("✅ Customer added successfully!");
                    nameField.clear();
                    phoneField.clear();
                    licenseField.clear();
                }

                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error saving customer.");
            }
        });

        // Back to Dashboard Button
        Button backButton = new Button("⬅ Back to Dashboard");
        backButton.setStyle(
            "-fx-background-color: #f8c9d4; " +
            "-fx-text-fill: black; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 20; " +
            "-fx-padding: 8 16 8 16;"
        );

        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.showDashboard(stage);
        });

        VBox layout = new VBox(12, titleLabel, nameField, phoneField, licenseField, addButton, backButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
            "-fx-padding: 30;" +
            "-fx-background-color: #fff0f5;" +
            "-fx-border-color: #f783ac;" +
            "-fx-border-radius: 15;" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);"
        );

        Scene scene = new Scene(layout, 380, 460);
        stage.setScene(scene);
        stage.setTitle("Add Customer - Car Rental System");
        stage.show();
    }
}

