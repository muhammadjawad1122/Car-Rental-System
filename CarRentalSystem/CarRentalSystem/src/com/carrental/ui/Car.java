package com.carrental.ui;

public class Car {
    private int id;
    private String model;
    private String brand;
    private int year;
    private double pricePerDay;

    public Car(int id, String model, String brand, int year, double pricePerDay) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.pricePerDay = pricePerDay;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
}
