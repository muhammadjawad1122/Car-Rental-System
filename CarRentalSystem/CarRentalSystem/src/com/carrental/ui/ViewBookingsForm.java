package com.carrental.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

import com.carrental.db.DBConnector;

public class ViewBookingsForm {

    public void showViewBookings(Stage stage) {
        TableView<BookingData> table = new TableView<>();

        // Table Columns
        TableColumn<BookingData, Integer> idCol = new TableColumn<>("Booking ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BookingData, String> carCol = new TableColumn<>("Car");
        carCol.setCellValueFactory(new PropertyValueFactory<>("car"));

        TableColumn<BookingData, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<BookingData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<BookingData, Integer> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));

        // Add all columns to table
        table.getColumns().addAll(idCol, carCol, customerCol, dateCol, daysCol);

        // Style the table
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-background-color: #fff0f5; -fx-border-color: #d63384; -fx-border-width: 2px;");

        // Load data
        ObservableList<BookingData> data = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnector.connect();
            String query = """
                SELECT b.id, c.model AS car, cu.name AS customer, b.booking_date, b.days
                FROM bookings b
                JOIN cars c ON b.car_id = c.id
                JOIN customers cu ON b.customer_id = cu.id
            """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                data.add(new BookingData(
                        rs.getInt("id"),
                        rs.getString("car"),
                        rs.getString("customer"),
                        rs.getDate("booking_date").toString(),
                        rs.getInt("days")
                ));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(data);

        // Back Button
        Button backButton = new Button("⬅ Back to Dashboard");
        backButton.setStyle(
            "-fx-background-color: #ffb6c1; -fx-text-fill: black;" +
            "-fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 6 18; -fx-cursor: hand;"
        );
        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.showDashboard(stage);
        });

        VBox layout = new VBox(15, table, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
            "-fx-padding: 25;" +
            "-fx-background-color: #fff0f5;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        Scene scene = new Scene(layout, 680, 450);
        stage.setScene(scene);
        stage.setTitle("📖 View Bookings");
        stage.show();
    }

    // Booking Data Class
    public static class BookingData {
        private int id;
        private String car;
        private String customer;
        private String date;
        private int days;

        public BookingData(int id, String car, String customer, String date, int days) {
            this.id = id;
            this.car = car;
            this.customer = customer;
            this.date = date;
            this.days = days;
        }

        public int getId() { return id; }
        public String getCar() { return car; }
        public String getCustomer() { return customer; }
        public String getDate() { return date; }
        public int getDays() { return days; }
    }
}
