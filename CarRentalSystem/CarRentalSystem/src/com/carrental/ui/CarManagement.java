package com.carrental.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CarManagement {

    public void showCarWindow() {
        Stage carStage = new Stage();
        carStage.setTitle("Manage Cars");

        Label title = new Label("Add a New Car");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Car Name:");
        TextField nameField = new TextField();

        Label modelLabel = new Label("Model:");
        TextField modelField = new TextField();

        Label plateLabel = new Label("Plate Number:");
        TextField plateField = new TextField();

        Button addBtn = new Button("Add Car");
        Label resultLabel = new Label();

        addBtn.setOnAction(e -> {
            String name = nameField.getText();
            String model = modelField.getText();
            String plate = plateField.getText();

            if (!name.isEmpty() && !model.isEmpty() && !plate.isEmpty()) {
                resultLabel.setText("✅ Car Added: " + name + " (" + model + ")");
                nameField.clear();
                modelField.clear();
                plateField.clear();
            } else {
                resultLabel.setText("❌ Please fill all fields");
            }
        });

        VBox layout = new VBox(10, title, nameLabel, nameField, modelLabel, modelField, plateLabel, plateField, addBtn, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 350);
        carStage.setScene(scene);
        carStage.show();
    }
}
