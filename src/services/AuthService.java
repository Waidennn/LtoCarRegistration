package services;

import java.util.Scanner;

import accounts.*;
import data.Database;
import utils.HashUtil;

public class AuthService {
    private Scanner sc = new Scanner(System.in);
    private static final String SECRET_ADMIN_CODE = "LTO-ADMIN-2025";

    public Account login() {
        System.out.print("\nEnter username: ");
        String u = sc.nextLine();

        System.out.print("Enter password: ");
        String p = sc.nextLine();
        String hash = HashUtil.sha256(p);

        for (Account a : Database.accounts) {
            if (a.getUsername().equals(u) && a.getPasswordHash().equals(hash)) {
                return a;
            }
        }

        System.out.println("Invalid credentials.\n");
        return null;
    }

    public void registerAdmin() {
        System.out.print("Enter secret admin code: ");
        if (!sc.nextLine().equals(SECRET_ADMIN_CODE)) {
            System.out.println("Invalid secret code!");
            return;
        }

        System.out.print("New admin username: ");
        String u = sc.nextLine();

        // Check if username already exists
        if (usernameExists(u)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("New admin password: ");
        String p = sc.nextLine();

        Database.accounts.add(new AdminAccount(u, HashUtil.sha256(p), SECRET_ADMIN_CODE));
        System.out.println("Admin created!");
    }

    public void registerUser() {
        System.out.print("New username: ");
        String u = sc.nextLine();

        // Check if username already exists
        if (usernameExists(u)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("New password: ");
        String p = sc.nextLine();

        Database.accounts.add(new UserAccount(u, HashUtil.sha256(p)));
        System.out.println("User created!");
    }

    // Helper method to check username uniqueness
    private boolean usernameExists(String username) {
        return Database.accounts.stream()
                .anyMatch(a -> a.getUsername().equals(username));
    }
}