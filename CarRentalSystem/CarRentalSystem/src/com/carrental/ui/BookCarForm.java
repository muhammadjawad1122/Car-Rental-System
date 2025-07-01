package com.carrental.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

import com.carrental.db.DBConnector;

public class BookCarForm {

    public void showBookingForm(Stage stage) {
        Label title = new Label("📅 Book a Car");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #d63384;");

        ComboBox<String> carDropdown = new ComboBox<>();
        carDropdown.setPromptText("🚗 Select a Car");

        ComboBox<String> customerDropdown = new ComboBox<>();
        customerDropdown.setPromptText("👤 Select a Customer");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("📅 Select Booking Date");

        TextField daysField = new TextField();
        daysField.setPromptText("🕒 Number of Days");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        Button bookButton = new Button("✅ Confirm Booking");
        bookButton.setStyle(
            "-fx-background-color: #ff69b4; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 8 20; -fx-cursor: hand;"
        );

        Button backButton = new Button("⬅ Back to Dashboard");
        backButton.setStyle(
            "-fx-background-color: #ffc0cb; -fx-text-fill: black;" +
            "-fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 6 18; -fx-cursor: hand;"
        );

        // Load dropdown data
        loadAvailableCars(carDropdown);
        loadCustomers(customerDropdown);

        bookButton.setOnAction(e -> {
            String carInfo = carDropdown.getValue();
            String customerInfo = customerDropdown.getValue();
            LocalDate date = datePicker.getValue();
            int days;

            if (carInfo == null || customerInfo == null || date == null || daysField.getText().isEmpty()) {
                messageLabel.setText("❌ Please fill all fields.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                days = Integer.parseInt(daysField.getText());
                int carId = Integer.parseInt(carInfo.split("-")[0].trim());
                int customerId = Integer.parseInt(customerInfo.split("-")[0].trim());

                Connection conn = DBConnector.connect();

                String checkQuery = "SELECT * FROM bookings WHERE car_id = ? AND booking_date = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, carId);
                checkStmt.setDate(2, Date.valueOf(date));
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    messageLabel.setText("❌ This car is already booked on this date.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    conn.close();
                    return;
                }

                String insertQuery = "INSERT INTO bookings (car_id, customer_id, booking_date, days) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, carId);
                insertStmt.setInt(2, customerId);
                insertStmt.setDate(3, Date.valueOf(date));
                insertStmt.setInt(4, days);

                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    messageLabel.setText("✅ Booking confirmed!");
                    messageLabel.setStyle("-fx-text-fill: green;");
                    daysField.clear();
                    datePicker.setValue(null);
                    carDropdown.setValue(null);
                    customerDropdown.setValue(null);
                }

                conn.close();

            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Please enter a valid number of days.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("❌ Error during booking.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.showDashboard(stage);
        });

        VBox layout = new VBox(12, title, carDropdown, customerDropdown, datePicker, daysField, bookButton, messageLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
            "-fx-padding: 25;" +
            "-fx-background-color: #fff0f5;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        Scene scene = new Scene(layout, 420, 500);
        stage.setScene(scene);
        stage.setTitle("Book a Car - Car Rental System");
        stage.show();
    }

    private void loadAvailableCars(ComboBox<String> carDropdown) {
        ObservableList<String> carList = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnector.connect();
            String query = "SELECT id, model FROM cars";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("model");
                carList.add(id + " - " + model);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        carDropdown.setItems(carList);
    }

    private void loadCustomers(ComboBox<String> customerDropdown) {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnector.connect();
            String query = "SELECT id, name FROM customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                customerList.add(id + " - " + name);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerDropdown.setItems(customerList);
    }
}
