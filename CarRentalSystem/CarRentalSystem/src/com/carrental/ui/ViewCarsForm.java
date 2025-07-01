package com.carrental.ui;

import com.carrental.db.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.*;

public class ViewCarsForm {

    public void showViewCars(Stage stage) {
        TableView<Car> tableView = new TableView<>();
        ObservableList<Car> carList = FXCollections.observableArrayList();

        // Columns
        TableColumn<Car, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Car, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Car, Double> priceCol = new TableColumn<>("Price/Day");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        // Edit button column
        TableColumn<Car, Void> editCol = new TableColumn<>("✏️ Edit");
        editCol.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-background-radius: 6;");
                editButton.setOnAction(e -> {
                    Car selectedCar = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Edit clicked for car ID: " + selectedCar.getId());
                    alert.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Delete button column
        TableColumn<Car, Void> deleteCol = new TableColumn<>("🗑️ Delete");
        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 6;");
                deleteButton.setOnAction(e -> {
                    Car selectedCar = getTableView().getItems().get(getIndex());
                    deleteCar(selectedCar.getId());
                    getTableView().getItems().remove(selectedCar);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.getColumns().addAll(idCol, modelCol, brandCol, yearCol, priceCol, editCol, deleteCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(400);

        loadCars(carList);
        tableView.setItems(carList);

        // Back Button
        Button backButton = new Button("⬅ Back to Dashboard");
        backButton.setStyle("-fx-background-color: #f8c9d4; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.showDashboard(stage);
        });

        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.CENTER);

        // Layout
        VBox root = new VBox(20, tableView, backBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #fff0f5;");

        Scene scene = new Scene(root, 850, 550);
        stage.setScene(scene);
        stage.setTitle("🚘 View Cars - Car Rental System");
        stage.show();
    }

    private void loadCars(ObservableList<Car> carList) {
        try (Connection conn = DBConnector.connect()) {
            String query = "SELECT * FROM cars";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("brand"),
                        rs.getInt("year"),
                        rs.getDouble("price_per_day")
                );
                carList.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCar(int carId) {
        try (Connection conn = DBConnector.connect()) {
            String query = "DELETE FROM cars WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, carId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
