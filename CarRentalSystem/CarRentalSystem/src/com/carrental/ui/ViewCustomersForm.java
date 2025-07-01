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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.carrental.db.DBConnector;

public class ViewCustomersForm {

    public static class Customer {
        private String name;
        private String phone;
        private String email;
        private String licenseNumber;

        public Customer(String name, String phone, String email, String licenseNumber) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.licenseNumber = licenseNumber;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }
    }

    public void showViewCustomers(Stage stage) {
        TableView<Customer> table = new TableView<>();

        TableColumn<Customer, String> nameCol = new TableColumn<>("👤 Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> phoneCol = new TableColumn<>("📞 Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Customer, String> emailCol = new TableColumn<>("📧 Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, String> licenseCol = new TableColumn<>("🆔 License Number");
        licenseCol.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));

        nameCol.setPrefWidth(130);
        phoneCol.setPrefWidth(130);
        emailCol.setPrefWidth(160);
        licenseCol.setPrefWidth(160);

        table.getColumns().addAll(nameCol, phoneCol, emailCol, licenseCol);

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnector.connect();
            String query = "SELECT * FROM customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String licenseNumber = rs.getString("license_number");
                customers.add(new Customer(name, phone, email, licenseNumber));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(customers);
        table.setStyle("-fx-font-size: 13px;");

        Button backButton = new Button("⬅ Back to Dashboard");
        backButton.setStyle(
            "-fx-background-color: #ff69b4;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 15;" +
            "-fx-padding: 8 18 8 18;" +
            "-fx-cursor: hand;"
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
            "-fx-border-radius: 15;" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );

        Scene scene = new Scene(layout, 600, 450);
        stage.setScene(scene);
        stage.setTitle("View Customers - Car Rental System");
        stage.show();
    }
}
