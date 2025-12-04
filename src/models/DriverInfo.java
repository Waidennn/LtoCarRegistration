//DriverInfo.java

package models;

public class DriverInfo {
    private String name;
    private String licenseNumber;
    private String uniqueCode;

    public DriverInfo(String name, String licenseNumber, String uniqueCode) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.uniqueCode = uniqueCode;
    }

    @Override
    public String toString() {
        return name + " | License: " + licenseNumber + " | Code: " + uniqueCode;
    }
}