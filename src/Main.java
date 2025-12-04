import services.AuthService;
import accounts.Account;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== LTO CAR RENTAL SYSTEM ===\n");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Login");
            System.out.println("2. Register User");
            System.out.println("3. Register Admin");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    Account account = authService.login();
                    if (account != null) {
                        account.showDashboard();
                    }
                }
                case 2 -> authService.registerUser();
                case 3 -> authService.registerAdmin();
                case 4 -> {
                    System.out.println("Thank you for using LTO Car Rental System!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}