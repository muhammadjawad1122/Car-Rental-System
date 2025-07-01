package com.carrental.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CustomerManagement {

    public void showCustomerWindow() {
        Stage customerStage = new Stage();
        customerStage.setTitle("Manage Customers");

        Label title = new Label("Add a New Customer");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();

        Label contactLabel = new Label("Contact Number:");
        TextField contactField = new TextField();

        Label licenseLabel = new Label("License Number:");
        TextField licenseField = new TextField();

        Button addBtn = new Button("Add Customer");
        Label resultLabel = new Label();

        addBtn.setOnAction(e -> {
            String name = nameField.getText();
            String contact = contactField.getText();
            String license = licenseField.getText();

            if (!name.isEmpty() && !contact.isEmpty() && !license.isEmpty()) {
                resultLabel.setText("✅ Customer Added: " + name);
                nameField.clear();
                contactField.clear();
                licenseField.clear();
            } else {
                resultLabel.setText("❌ Please fill all fields");
            }
        });

        VBox layout = new VBox(10, title, nameLabel, nameField, contactLabel, contactField, licenseLabel, licenseField, addBtn, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        customerStage.setScene(scene);
        customerStage.show();
    }
}

