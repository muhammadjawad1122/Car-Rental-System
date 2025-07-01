package com.carrental.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BookingManagement {

    public void showBookingWindow() {
        Stage bookingStage = new Stage();
        bookingStage.setTitle("Car Booking");

        Label title = new Label("Book a Car");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label customerLabel = new Label("Customer Name:");
        TextField customerField = new TextField();

        Label carLabel = new Label("Car Name:");
        TextField carField = new TextField();

        Label dateLabel = new Label("Booking Date:");
        TextField dateField = new TextField();
        dateField.setPromptText("e.g., 2025-06-12");

        Button bookBtn = new Button("Book Now");
        Label resultLabel = new Label();

        bookBtn.setOnAction(e -> {
            String customer = customerField.getText();
            String car = carField.getText();
            String date = dateField.getText();

            if (!customer.isEmpty() && !car.isEmpty() && !date.isEmpty()) {
                resultLabel.setText("✅ Booking Confirmed for " + customer + " (Car: " + car + ")");
                customerField.clear();
                carField.clear();
                dateField.clear();
            } else {
                resultLabel.setText("❌ Please fill all fields");
            }
        });

        VBox layout = new VBox(10, title, customerLabel, customerField, carLabel, carField, dateLabel, dateField, bookBtn, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        bookingStage.setScene(scene);
        bookingStage.show();
    }
}

