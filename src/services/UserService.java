//UserService. java

package services;

import java.util.Scanner;

import accounts.UserAccount;
import data.Database;
import models.Car;
import models.DriverInfo;
import utils.CodeGenerator;

public class UserService {
    private UserAccount user;
    private Scanner sc = new Scanner(System.in);

    public UserService(UserAccount user) { this.user = user; }

    public void userMenu() {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. View My Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Complete Rental");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> viewCars();
                case 2 -> rentCar();
                case 3 -> completeRental();
                case 4 -> { return; }
            }
        }
    }

    private void viewCars() {
        System.out.println("\n--- MY CARS ---");
        user.getCars().forEach(System.out::println);
    }

    private void rentCar() {
        System.out.print("Enter plate number: ");
        String pn = sc.nextLine();

        Car c = user.getCars().stream()
                .filter(x -> x.getPlateNumber().equals(pn))
                .findFirst().orElse(null);

        if (c == null) {
            System.out.println("Car not found!");
            return;
        }

        System.out.print("Driver Name: ");
        String dn = sc.nextLine();

        System.out.print("License No: ");
        String ln = sc.nextLine();

        String unique = CodeGenerator.generateDriverCode();

        c.rentCar(new DriverInfo(dn, ln, unique));
        Database.addLog("RENTED: " + c.getPlateNumber() + " by " + dn);

        System.out.println("Car rented! Unique Code: " + unique);
    }

    private void completeRental() {
        System.out.print("Enter plate number: ");
        String pn = sc.nextLine();

        Car c = user.getCars().stream()
                .filter(x -> x.getPlateNumber().equals(pn))
                .findFirst().orElse(null);

        if (c == null) {
            System.out.println("Car not found!");
            return;
        }

        c.completeRental();
        Database.addLog("COMPLETED: " + pn);

        System.out.println("Rental completed!");
    }
}