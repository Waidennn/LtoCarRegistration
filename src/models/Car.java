package models;

public class Car {
    private String plateNumber;
    private String brand;
    private String model;
    private int year;
    private String status = "AVAILABLE";
    private DriverInfo driver;

    public Car(String plateNumber, String brand, String model, int year) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getPlateNumber() { return plateNumber; }
    public String getStatus() { return status; }
    public DriverInfo getDriver() { return driver; }

    public void rentCar(DriverInfo driver) {
        this.driver = driver;
        this.status = "RENTED";
    }

    public void completeRental() {
        this.status = "AVAILABLE";
        this.driver = null;
    }

    @Override
    public String toString() {
        String driverInfo = (driver == null) ? "None" : driver.toString();
        return plateNumber + " | " + brand + " " + model + " (" + year + ") | Status: " + status + " | Driver: " + driverInfo;
    }
}