# LTO Car Rental Registration & Monitoring System

## Description/Overview

The LTO Car Rental Registration & Monitoring System is a command-line interface (CLI) application designed to streamline car rental management operations. This system enables administrators to oversee user accounts and vehicle registrations, while allowing regular users to manage their car inventory, track rental status, and maintain driver information. 

The application solves the problem of manual car rental tracking by providing secure authentication, real-time rental status updates, automated driver code generation, and comprehensive audit logging for accountability and monitoring.

---

## OOP Concepts Applied

### 1. Inheritance

The project implements a hierarchical class structure with an abstract `Account` class serving as the parent for two child classes: `AdminAccount` and `UserAccount`. Both subclasses inherit common properties (`username`, `passwordHash`, `role`) and methods (`getUsername()`, `getPasswordHash()`, `getRole()`) while providing their own implementations of the abstract `showDashboard()` method.

**Code Example:**
```java
abstract class Account {
    protected String username;
    protected String passwordHash;
    protected String role;
    
    public abstract void showDashboard();
}

class AdminAccount extends Account {
    @Override
    public void showDashboard() {
        adminService.adminMenu();
    }
}

class UserAccount extends Account {
    @Override
    public void showDashboard() {
        userService.userMenu();
    }
}
```

### 2. Polymorphism

Polymorphism is demonstrated through method overriding, where both `AdminAccount` and `UserAccount` provide their own implementations of the abstract `showDashboard()` method from the parent `Account` class. The `toString()` method is also overridden in both `Car` and `DriverInfo` classes to provide custom string representations.

**Code Example:**
```java
// Polymorphic behavior
Account account = authService.login();
if (account != null) {
    account.showDashboard(); // Calls appropriate implementation
}
```

### 3. Encapsulation

Private and protected access modifiers encapsulate data within classes. For example, the `Account` class uses `protected` fields for `username`, `passwordHash`, and `role`, accessible only within the class hierarchy, while the `Car` class uses `private` fields like `plateNumber`, `brand`, `model`, and `year` with public getter methods providing controlled access.

**Code Example:**
```java
class Car {
    private final String plateNumber;
    private final String brand;
    private final String model;
    private final int year;
    private String status = "AVAILABLE";
    
    public String getPlateNumber() { return plateNumber; }
    public String getStatus() { return status; }
}
```

### 4. Abstraction

The abstract `Account` class defines the contract for all account types through the abstract `showDashboard()` method, hiding implementation details from the main application logic. Service classes (`AuthService`, `AdminService`, `UserService`) abstract complex business logic operations, separating concerns from the presentation layer.

**Code Example:**
```java
abstract class Account {
    public abstract void showDashboard(); // Contract for all accounts
}
```

---

## Program Structure

### Main Classes and Their Roles

| Class | Role |
|-------|------|
| **LtoCarRental** | Entry point class containing the main method that initializes and runs the application |
| **App** | Manages the main application loop and displays the primary menu for login, registration, audit logs, and exit options |
| **Account** (abstract) | Base class defining common properties and behaviors for all account types with an abstract `showDashboard()` method |
| **AdminAccount** | Extends Account to provide administrative privileges including user management, car assignment/deletion, and audit log access |
| **UserAccount** | Extends Account to allow users to manage their personal car inventory and rental operations |
| **Car** | Represents a vehicle with properties like plate number, brand, model, year, rental status, and associated driver information |
| **DriverInfo** | Stores driver details including name, license number, and a unique rental code |
| **Database** | Simulates a database using static lists to store accounts and audit logs, pre-seeded with default admin and user accounts |
| **AuthService** | Handles user authentication, registration for both users and admins with password hashing via SHA-256 |
| **AdminService** | Provides administrative functionality including viewing users, managing cars, and accessing audit logs |
| **UserService** | Manages user-specific operations such as viewing cars, adding cars, marking rentals, and completing rentals |
| **HashUtil** | Utility class for SHA-256 password hashing to ensure secure credential storage |
| **CodeGenerator** | Generates unique 8-character alphanumeric codes for driver identification |

### Class Relationships

```
LtoCarRental (main class)
  └── App
       ├── AuthService
       └── Account (abstract)
            ├── AdminAccount
            │    └── AdminService
            └── UserAccount
                 ├── UserService
                 └── Car
                      └── DriverInfo

Database (static storage)
HashUtil (utility)
CodeGenerator (utility)
```

---

## How to Run the Program

Follow these step-by-step instructions to compile and run the program from the command line:

### Step 1: Ensure Java is Installed
Verify that Java Development Kit (JDK) 11 or higher is installed by running:
```bash
java -version
```

### Step 2: Save the File
Ensure the file is saved as `LtoCarRental.java` in your working directory.

### Step 3: Compile the Program
Navigate to the directory containing the file and run:
```bash
javac LtoCarRental.java
```

### Step 4: Run the Program
After successful compilation, execute:
```bash
java LtoCarRental
```

### Step 5: Default Credentials
The system is pre-seeded with:
- **Admin** - Username: `admin`, Password: `admin123`
- **User** - Username: `user`, Password: `user123`

---

## Sample Output

```
Starting LTO Car Rental System (single-file demo).

--- LTO CAR RENTAL SYSTEM ---
1. Login
2. Register User
3. Register Admin (requires secret)
4. View Audit Logs (admin only)
5. Exit
Choose: 1

Enter username: user
Enter password: user123
Login successful. Role: USER

--- USER MENU (user) ---
1. View My Cars
2. Add Car (to my account)
3. Mark Car as Rented
4. Mark Rental as Completed
5. Logout
Choose: 2

Plate Number: ABC123
Brand: Toyota
Model: Corolla
Year: 2022
Car added to your account.

--- USER MENU (user) ---
1. View My Cars
2. Add Car (to my account)
3. Mark Car as Rented
4. Mark Rental as Completed
5. Logout
Choose: 1

--- MY CARS ---
ABC123 | Toyota Corolla | 2022 | AVAILABLE | Driver: -
```

---

## Authors and Acknowledgements

**Project Members:**
- Zeus Dave De Chavez
- Mikko Garcia
- Don Mclaire Gonzales

**Course:** IT-2111 Object Oriented Programming  
**Institution:** Batangas State University  
**Date:** December 2025

Special thanks to our instructor Ms. Christiana Grace Alib.

---

## Future Enhancements

- Implement persistent database storage using MySQL or PostgreSQL instead of in-memory lists
- Add a graphical user interface (GUI) using JavaFX or Swing
- Implement rental pricing calculations and payment tracking
- Add date/time tracking for rental periods with automatic late fee calculation
- Implement search and filter capabilities for cars and users
- Add email notifications for rental confirmations and reminders
- Export audit logs and reports to CSV or PDF format
- Multi-branch support for car rental franchises
- Mobile application integration
- Real-time SMS notifications for rental updates

---

## References

- [Java Documentation](https://docs.oracle.com/en/java/)
- [MessageDigest (SHA-256) - Java API](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
- Object-Oriented Programming Principles: Course materials and lecture notes
- Java Collections Framework Documentation
- Best Practices in Software Design Patterns

---

## License

This project is created for educational purposes as part of IT-2111 Object Oriented Programming course at Batangas State University.

---

**⭐ If you find this project helpful, please consider giving it a star!**
