package com.carrental.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dashboard {

    public void showDashboard(Stage stage) {
        // 💖 Title
        Text title = new Text("🚗 Car Rental Dashboard");
        title.setFont(Font.font("Arial", 24));
        title.setFill(Color.HOTPINK);

        // 🎀 All main buttons
        Button addCarButton = createStyledButton("➕ Add Car");
        Button viewCarsButton = createStyledButton("📋 View Cars");
        Button addCustomerButton = createStyledButton("👤 Add Customer");
        Button viewCustomersButton = createStyledButton("👥 View Customers");
        Button bookCarButton = createStyledButton("📅 Book a Car");
        Button viewBookingsButton = createStyledButton("📖 View Bookings");

        // ✅ Functional button actions
        addCarButton.setOnAction(e -> {
            AddCarForm form = new AddCarForm();
            form.showAddCarForm(stage);
        });

        viewCarsButton.setOnAction(e -> {
            ViewCarsForm form = new ViewCarsForm();
            form.showViewCars(stage);
        });

        addCustomerButton.setOnAction(e -> {
            AddCustomerForm form = new AddCustomerForm();
            form.showAddCustomerForm(stage);
        });

        viewCustomersButton.setOnAction(e -> {
            ViewCustomersForm form = new ViewCustomersForm();
            form.showViewCustomers(stage);
        });

        bookCarButton.setOnAction(e -> {
            BookCarForm form = new BookCarForm();
            form.showBookingForm(stage);
        });

        viewBookingsButton.setOnAction(e -> {
            ViewBookingsForm form = new ViewBookingsForm();
            form.showViewBookings(stage);
        });

        // 💗 Layout
        VBox layout = new VBox(15);
        layout.getChildren().addAll(
                title,
                addCarButton,
                viewCarsButton,
                addCustomerButton,
                viewCustomersButton,
                bookCarButton,
                viewBookingsButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #ffe6f0;"); // soft pink

        // 🌸 Scene setup
        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Dashboard - Car Rental System");
        stage.show();
    }

    // 🎨 Reusable pink button style method
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #ff4da6;" +  // deep pink
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 8;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #ff80bf;" +  // light pink on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 8;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #ff4da6;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 8;"
        ));

        return button;
    }
}

