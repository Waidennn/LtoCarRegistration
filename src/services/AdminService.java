package services;

import java.util.Scanner;

import accounts.AdminAccount;
import data.Database;
import models.Car;

public class AdminService {
    private AdminAccount admin;
    private Scanner sc = new Scanner(System.in);

    public AdminService(AdminAccount admin) {
        this.admin = admin;
    }

    public void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add Car to User");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> viewUsers();
                case 2 -> addCarToUser();
                case 3 -> { return; }
            }
        }
    }

    private void viewUsers() {
        System.out.println("\n--- USERS ---");
        Database.accounts.stream()
                .filter(a -> a.getRole().equals("USER"))
                .forEach(a -> System.out.println(a.getUsername()));
    }

    private void addCarToUser() {
        System.out.print("Enter username to assign a car: ");
        String u = sc.nextLine();

        var user = Database.accounts.stream()
                .filter(a -> a.getUsername().equals(u) && a.getRole().equals("USER"))
                .findFirst().orElse(null);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.print("Plate Number: ");
        String pn = sc.nextLine();
        System.out.print("Brand: ");
        String b = sc.nextLine();
        System.out.print("Model: ");
        String m = sc.nextLine();
        System.out.print("Year: ");
        int y = Integer.parseInt(sc.nextLine());

        ((accounts.UserAccount) user).getCars().add(
                new Car(pn, b, m, y)
        );

        System.out.println("Car assigned!");
    }
}