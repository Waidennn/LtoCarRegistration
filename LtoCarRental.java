import java.util.*;
import java.security.MessageDigest;

/**
 * Single-file runnable implementation of the LTO Car Rental Registration & Monitoring System (CLI).
 * Save as LtoCarRental.java, then: javac LtoCarRental.java && java LtoCarRental
 */
public class LtoCarRental {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    static class App {
        private final AuthService authService = new AuthService();
        private final Scanner sc = new Scanner(System.in);

        public void run() {
            System.out.println("Starting LTO Car Rental System (single-file demo).");

            while (true) {
                System.out.println("\n--- LTO CAR RENTAL SYSTEM ---");
                System.out.println("1. Login");
                System.out.println("2. Register User");
                System.out.println("3. Register Admin (requires secret)");
                System.out.println("4. View Audit Logs (admin only)");
                System.out.println("5. Exit");
                System.out.print("Choose: ");

                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                int choice;
                try { choice = Integer.parseInt(line); } catch (Exception e) { System.out.println("Invalid option."); continue; }

                switch (choice) {
                    case 1 -> {
                        Account a = authService.login();
                        if (a != null) a.showDashboard();
                    }
                    case 2 -> authService.registerUser();
                    case 3 -> authService.registerAdmin();
                    case 4 -> {
                        System.out.println("\n--- AUDIT LOGS ---");
                        if (Database.auditLogs.isEmpty()) System.out.println("<no logs>");
                        else Database.auditLogs.forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.println("Exiting.");
                        return;
                    }
                    default -> System.out.println("Unknown choice.");
                }
            }
        }
    }


    static abstract class Account {
        protected String username;
        protected String passwordHash;
        protected String role;

        public Account(String username, String passwordHash, String role) {
            this.username = username;
            this.passwordHash = passwordHash;
            this.role = role;
        }

        public String getUsername() { return username; }
        public String getPasswordHash() { return passwordHash; }
        public String getRole() { return role; }

        public abstract void showDashboard();
    }

    static class AdminAccount extends Account {
        private final String adminCode;
        private final AdminService adminService;

        public AdminAccount(String username, String passwordHash, String adminCode) {
            super(username, passwordHash, "ADMIN");
            this.adminCode = adminCode;
            this.adminService = new AdminService(this);
        }

        public String getAdminCode() { return adminCode; }

        @Override
        public void showDashboard() {
            adminService.adminMenu();
        }
    }

    static class UserAccount extends Account {
        private final List<Car> cars = new ArrayList<>();
        private final UserService userService;

        public UserAccount(String username, String passwordHash) {
            super(username, passwordHash, "USER");
            this.userService = new UserService(this);
        }

        public List<Car> getCars() { return cars; }

        @Override
        public void showDashboard() {
            userService.userMenu();
        }
    }

    static class Car {
        private final String plateNumber;
        private final String brand;
        private final String model;
        private final int year;
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
            String driverSummary = (driver == null) ? "-" : driver.toString();
            return plateNumber + " | " + brand + " " + model + " | " + year + " | " + status + " | Driver: " + driverSummary;
        }
    }

    static class DriverInfo {
        private final String name;
        private final String licenseNumber;
        private final String uniqueCode;

        public DriverInfo(String name, String licenseNumber, String uniqueCode) {
            this.name = name;
            this.licenseNumber = licenseNumber;
            this.uniqueCode = uniqueCode;
        }

        @Override
        public String toString() {
            return name + " (Lic: " + licenseNumber + ", Code: " + uniqueCode + ")";
        }
    }

    static class Database {
        public static final List<Account> accounts = new ArrayList<>();
        public static final List<String> auditLogs = new ArrayList<>();

        static {
            // seed one admin and one user so system is immediately usable
            String adminPw = HashUtil.sha256("admin123");
            accounts.add(new AdminAccount("admin", adminPw, AuthService.SECRET_ADMIN_CODE));
            String userPw = HashUtil.sha256("user123");
            accounts.add(new UserAccount("user", userPw));
        }

        public static void addLog(String l) { auditLogs.add(l); }
    }

    static class HashUtil {
        public static String sha256(String input) {
            if (input == null) return null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] bytes = md.digest(input.getBytes("UTF-8"));
                StringBuilder sb = new StringBuilder();
                for (byte b : bytes) sb.append(String.format("%02x", b));
                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class CodeGenerator {
        public static String generateDriverCode() {
            return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        }
    }

    static class AuthService {
        // public so AdminService/Admin registration can check if needed
        public static final String SECRET_ADMIN_CODE = "LTO-ADMIN-2025";
        private final Scanner sc = new Scanner(System.in);

        public Account login() {
            System.out.print("\nEnter username: ");
            String u = sc.nextLine().trim();
            System.out.print("Enter password: ");
            String p = sc.nextLine();
            String hash = HashUtil.sha256(p);

            for (Account a : Database.accounts) {
                if (a.getUsername().equals(u) && a.getPasswordHash().equals(hash)) {
                    System.out.println("Login successful. Role: " + a.getRole());
                    return a;
                }
            }
            System.out.println("Invalid credentials.");
            return null;
        }

        public void registerAdmin() {
            System.out.print("Enter secret admin code: ");
            String code = sc.nextLine().trim();
            if (!SECRET_ADMIN_CODE.equals(code)) {
                System.out.println("Invalid secret code! Not allowed.");
                return;
            }
            System.out.print("New admin username: ");
            String u = sc.nextLine().trim();

            if (existsUsername(u)) { System.out.println("Username already exists."); return; }

            System.out.print("New admin password: ");
            String p = sc.nextLine();
            Database.accounts.add(new AdminAccount(u, HashUtil.sha256(p), SECRET_ADMIN_CODE));
            System.out.println("Admin created!");
        }

        public void registerUser() {
            System.out.print("New username: ");
            String u = sc.nextLine().trim();

            if (existsUsername(u)) { System.out.println("Username already exists."); return; }

            System.out.print("New password: ");
            String p = sc.nextLine();
            Database.accounts.add(new UserAccount(u, HashUtil.sha256(p)));
            System.out.println("User created!");
        }

        private boolean existsUsername(String u) {
            return Database.accounts.stream().anyMatch(a -> a.getUsername().equals(u));
        }
    }

    static class AdminService {
        private final AdminAccount admin;
        private final Scanner sc = new Scanner(System.in);

        public AdminService(AdminAccount admin) { this.admin = admin; }

        public void adminMenu() {
            while (true) {
                System.out.println("\n--- ADMIN MENU (" + admin.getUsername() + ") ---");
                System.out.println("1. View All Users");
                System.out.println("2. Add Car To User");
                System.out.println("3. Delete Car From User");
                System.out.println("4. View Audit Logs");
                System.out.println("5. Logout");
                System.out.print("Choose: ");
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                int c;
                try { c = Integer.parseInt(line); } catch (Exception e) { System.out.println("Invalid."); continue; }
                switch (c) {
                    case 1 -> viewUsers();
                    case 2 -> addCarToUser();
                    case 3 -> deleteCarFromUser();
                    case 4 -> Database.auditLogs.forEach(System.out::println);
                    case 5 -> { return; }
                    default -> System.out.println("Unknown.");
                }
            }
        }

        private void viewUsers() {
            System.out.println("\n--- USERS ---");
            Database.accounts.stream()
                    .filter(a -> "USER".equals(a.getRole()))
                    .forEach(a -> System.out.println("- " + a.getUsername()));
        }

        private void addCarToUser() {
            System.out.print("Enter username to assign a car: ");
            String u = sc.nextLine().trim();
            Account acc = Database.accounts.stream()
                    .filter(a -> a.getUsername().equals(u) && "USER".equals(a.getRole()))
                    .findFirst().orElse(null);

            if (acc == null) { System.out.println("User not found."); return; }

            UserAccount ua = (UserAccount) acc;

            System.out.print("Plate Number: ");
            String pn = sc.nextLine().trim();
            // check duplicate plate
            boolean duplicate = Database.accounts.stream()
                    .filter(a -> a instanceof UserAccount)
                    .flatMap(a -> ((UserAccount) a).getCars().stream())
                    .anyMatch(car -> car.getPlateNumber().equalsIgnoreCase(pn));
            if (duplicate) { System.out.println("Plate number already exists in system."); return; }

            System.out.print("Brand: ");
            String b = sc.nextLine().trim();
            System.out.print("Model: ");
            String m = sc.nextLine().trim();
            System.out.print("Year: ");
            int y;
            try { y = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { System.out.println("Invalid year."); return; }

            ua.getCars().add(new Car(pn, b, m, y));
            System.out.println("Car assigned to user " + u + ".");
            Database.addLog("ADMIN " + admin.getUsername() + " assigned car " + pn + " to " + u);
        }

        private void deleteCarFromUser() {
            System.out.print("Enter username who owns car: ");
            String u = sc.nextLine().trim();
            Account acc = Database.accounts.stream()
                    .filter(a -> a.getUsername().equals(u) && "USER".equals(a.getRole()))
                    .findFirst().orElse(null);
            if (acc == null) { System.out.println("User not found."); return; }
            UserAccount ua = (UserAccount) acc;

            System.out.print("Enter plate number to delete: ");
            String pn = sc.nextLine().trim();
            Optional<Car> maybe = ua.getCars().stream().filter(c -> c.getPlateNumber().equalsIgnoreCase(pn)).findFirst();
            if (maybe.isEmpty()) { System.out.println("Car not found for this user."); return; }
            ua.getCars().remove(maybe.get());
            System.out.println("Car removed.");
            Database.addLog("ADMIN " + admin.getUsername() + " removed car " + pn + " from " + u);
        }
    }

    static class UserService {
        private final UserAccount user;
        private final Scanner sc = new Scanner(System.in);

        public UserService(UserAccount user) { this.user = user; }

        public void userMenu() {
            while (true) {
                System.out.println("\n--- USER MENU (" + user.getUsername() + ") ---");
                System.out.println("1. View My Cars");
                System.out.println("2. Add Car (to my account)");
                System.out.println("3. Mark Car as Rented");
                System.out.println("4. Mark Rental as Completed");
                System.out.println("5. Logout");
                System.out.print("Choose: ");
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                int c;
                try { c = Integer.parseInt(line); } catch (Exception e) { System.out.println("Invalid."); continue; }
                switch (c) {
                    case 1 -> viewCars();
                    case 2 -> addCar();
                    case 3 -> rentCar();
                    case 4 -> completeRental();
                    case 5 -> { return; }
                    default -> System.out.println("Unknown.");
                }
            }
        }

        private void viewCars() {
            System.out.println("\n--- MY CARS ---");
            if (user.getCars().isEmpty()) System.out.println("<no cars>");
            else user.getCars().forEach(System.out::println);
        }

        private void addCar() {
            System.out.print("Plate Number: ");
            String pn = sc.nextLine().trim();
            // duplicate check across all users
            boolean duplicate = Database.accounts.stream()
                    .filter(a -> a instanceof UserAccount)
                    .flatMap(a -> ((UserAccount) a).getCars().stream())
                    .anyMatch(car -> car.getPlateNumber().equalsIgnoreCase(pn));
            if (duplicate) { System.out.println("Plate number already exists in system."); return; }

            System.out.print("Brand: ");
            String b = sc.nextLine().trim();
            System.out.print("Model: ");
            String m = sc.nextLine().trim();
            System.out.print("Year: ");
            int y;
            try { y = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { System.out.println("Invalid year."); return; }

            user.getCars().add(new Car(pn, b, m, y));
            System.out.println("Car added to your account.");
            Database.addLog("USER " + user.getUsername() + " added car " + pn);
        }

        private void rentCar() {
            System.out.print("Enter plate number: ");
            String pn = sc.nextLine().trim();
            Optional<Car> maybe = user.getCars().stream().filter(c -> c.getPlateNumber().equalsIgnoreCase(pn)).findFirst();
            if (maybe.isEmpty()) { System.out.println("Car not found in your account."); return; }
            Car c = maybe.get();

            if ("RENTED".equalsIgnoreCase(c.getStatus())) { System.out.println("Car already rented."); return; }

            System.out.print("Driver Name: ");
            String dn = sc.nextLine().trim();
            System.out.print("License No: ");
            String ln = sc.nextLine().trim();
            String unique = CodeGenerator.generateDriverCode();

            c.rentCar(new DriverInfo(dn, ln, unique));
            Database.addLog("RENTED: " + c.getPlateNumber() + " by " + dn + " (user " + user.getUsername() + ")");
            System.out.println("Car rented! Unique Code: " + unique);
        }

        private void completeRental() {
            System.out.print("Enter plate number: ");
            String pn = sc.nextLine().trim();
            Optional<Car> maybe = user.getCars().stream().filter(c -> c.getPlateNumber().equalsIgnoreCase(pn)).findFirst();
            if (maybe.isEmpty()) { System.out.println("Car not found in your account."); return; }
            Car c = maybe.get();

            if (!"RENTED".equalsIgnoreCase(c.getStatus())) { System.out.println("Car is not currently rented."); return; }

            String prevDriver = (c.getDriver() == null) ? "unknown" : c.getDriver().toString();
            c.completeRental();
            Database.addLog("COMPLETED: " + pn + " previous driver: " + prevDriver);
            System.out.println("Rental completed and car returned to AVAILABLE.");
        }
    }
}
