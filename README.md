# 🚗 LTO Car Rental Registration & Monitoring System

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-Educational-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-success.svg)]()

A command-line interface (CLI) application designed to streamline car rental management operations with secure authentication, real-time rental tracking, and comprehensive audit logging.

---

## Table of Contents

- [Description](#-description)
- [Features](#-features)
- [OOP Concepts Applied](#-oop-concepts-applied)
- [Program Structure](#-program-structure)
- [Installation & Setup](#-installation--setup)
- [How to Run](#-how-to-run)
- [Usage Guide](#-usage-guide)
- [Sample Output](#-sample-output)
- [Authors](#-authors)
- [Future Enhancements](#-future-enhancements)
- [References](#-references)
- [License](#-license)

---

## 📖 Description

The **LTO Car Rental Registration & Monitoring System** is a Java-based application that enables administrators to oversee user accounts and vehicle registrations, while allowing regular users to manage their car inventory, track rental status, and maintain driver information.

### Problem Statement
Manual car rental tracking is inefficient and prone to errors. This system solves that by providing:
- Secure authentication with SHA-256 password hashing
- Real-time rental status updates
- Automated driver code generation
- Comprehensive audit logging for accountability

---

## ✨ Features

### 👨‍💼 Admin Features
- View all registered users
- Add cars to user accounts
- Delete cars from user accounts
- Access comprehensive audit logs
- Secure admin registration with secret code (`LTO-ADMIN-2025`)

### 👤 User Features
- View personal car inventory
- Add new cars to account
- Rent out cars to drivers with unique codes
- Complete rental transactions
- Track rental status (AVAILABLE/RENTED)

### System Features
- SHA-256 password hashing for security
- Audit logging for all transactions
- Role-based authentication (Admin/User)
- Unique 8-character driver code generation
- Duplicate plate number prevention

---

## OOP Concepts Applied

### 1️⃣ Inheritance

The project implements a hierarchical class structure with an abstract `Account` class serving as the parent for `AdminAccount` and `UserAccount`.

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

### 2️⃣ Polymorphism

Method overriding demonstrates polymorphism - both account types provide their own implementations of `showDashboard()`.

```java
// Polymorphic behavior
Account account = authService.login();
if (account != null) {
    account.showDashboard(); // Calls appropriate implementation
}
```

### 3️⃣ Encapsulation

Private and protected access modifiers encapsulate data with controlled access through getter methods.

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

### 4️⃣ Abstraction

Abstract classes and service layers hide implementation details from the main application logic.

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
| **Main** | Entry point containing the main method that starts the application |
| **AuthService** | Handles user authentication and registration for both users and admins |
| **AdminService** | Provides administrative functionality and menu operations |
| **UserService** | Manages user-specific operations and menu |
| **Account** (abstract) | Base class for all account types with abstract showDashboard() |
| **AdminAccount** | Administrative privileges and operations |
| **UserAccount** | User-specific car and rental management |
| **Car** | Vehicle entity with rental status tracking |
| **DriverInfo** | Driver details with unique rental codes |
| **Database** | In-memory storage for accounts and audit logs |
| **HashUtil** | SHA-256 password hashing utility |
| **CodeGenerator** | Unique driver code generation |

### Project Architecture

```
src/
├── accounts/
│   ├── Account.java          # Abstract base class
│   ├── AdminAccount.java     # Admin implementation
│   └── UserAccount.java      # User implementation
├── models/
│   ├── Car.java              # Car entity
│   └── DriverInfo.java       # Driver information
├── services/
│   ├── AdminService.java     # Admin operations
│   ├── AuthService.java      # Authentication
│   └── UserService.java      # User operations
├── utils/
│   ├── CodeGenerator.java    # Code generation
│   └── HashUtil.java         # Password hashing
├── data/
│   └── Database.java         # Data storage
└── Main.java                 # Application entry point
```

---

## Installation & Setup

### Prerequisites
- **Java Development Kit (JDK) 11 or higher**
- Command line interface (CMD, PowerShell, Terminal)

### Verify Java Installation
```bash
java -version
javac -version
```

---

## 🚀 How to Run

### Prerequisites Setup

**Ensure proper folder structure:**
   ```
   LTOCarRental/
   ├── src/
   │   ├── accounts/
   │   ├── models/
   │   ├── services/
   │   ├── utils/
   │   ├── data/
   │   └── Main.java
   └── bin/ (will be created during compilation)
   ```

### Compilation Steps

1. **Navigate to project root directory:**
   ```bash
   cd path/to/LTOCarRental
   ```

2. **Compile all Java files:**
   
   **Windows:**
   ```powershell
   javac -d bin -sourcepath src src\Main.java src\accounts\*.java src\models\*.java src\services\*.java src\utils\*.java src\data\*.java
   ```
   
   **Mac/Linux:**
   ```bash
   javac -d bin -sourcepath src src/Main.java src/accounts/*.java src/models/*.java src/services/*.java src/utils/*.java src/data/*.java
   ```

3. **Run the application:**
   ```bash
   java -cp bin Main
   ```

### Alternative: Single File Version

If you prefer a simpler approach without package structure, you can use the all-in-one file:

```bash
javac LtoCarRental.java
java LtoCarRental
```

**Note:** This project uses the multi-file structure with `Main.java` as the entry point.

---

## 📚 Usage Guide

### Default Credentials

The system does **not** come with pre-seeded accounts. You need to register first:

| Action | Steps |
|--------|-------|
| **Register User** | Choose option 2 → Enter username and password |
| **Register Admin** | Choose option 3 → Enter secret code: `LTO-ADMIN-2025` → Enter username and password |
| **Login** | Choose option 1 → Enter your credentials |

### Main Menu Options

```
1. Login
2. Register User
3. Register Admin (requires secret code: LTO-ADMIN-2025)
4. View Audit Logs (admin only)
5. Exit
```

### Admin Dashboard

```
1. View All Users
2. Add Car To User
3. Delete Car From User
4. View Audit Logs
5. Logout
```

### User Dashboard

```
1. View My Cars
2. Add Car (to my account)
3. Mark Car as Rented
4. Mark Rental as Completed
5. Logout
```

### Sample Workflow

1. **Register as User** → Create account with username and password
2. **Login as User** → Access user dashboard
3. **Add a Car** → Enter plate number, brand, model, and year
4. **Rent the Car** → Provide driver name and license number
5. **Complete Rental** → Mark car as available again

---

## Sample Output

```
====================================
   LTO CAR RENTAL SYSTEM
====================================

--- MAIN MENU ---
1. Login
2. Register User
3. Register Admin
4. View Audit Logs
5. Exit
Choose: 2

New username: john
New password: pass123
User created!

--- MAIN MENU ---
1. Login
2. Register User
3. Register Admin
4. View Audit Logs
5. Exit
Choose: 1

Enter username: john
Enter password: pass123

Welcome, john!

--- USER MENU (john) ---
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

--- USER MENU (john) ---
1. View My Cars
2. Add Car (to my account)
3. Mark Car as Rented
4. Mark Rental as Completed
5. Logout
Choose: 1

--- MY CARS ---
ABC123 | Toyota Corolla (2022) | Status: AVAILABLE | Driver: None

--- USER MENU (john) ---
1. View My Cars
2. Add Car (to my account)
3. Mark Car as Rented
4. Mark Rental as Completed
5. Logout
Choose: 3

Enter plate number: ABC123
Driver Name: Juan Dela Cruz
License No: N01-23-456789
Car rented! Unique Code: 7A3F9B2E

--- USER MENU (john) ---
Choose: 1

--- MY CARS ---
ABC123 | Toyota Corolla (2022) | Status: RENTED | Driver: Juan Dela Cruz | License: N01-23-456789 | Code: 7A3F9B2E

--- USER MENU (john) ---
Choose: 5

--- MAIN MENU ---
Choose: 5

Thank you for using LTO Car Rental System!
Goodbye!
```

---

## 👥 Authors

**Project Team:**
- **Zeus Dave De Chavez** - Developer
- **Mikko Garcia** - Developer
- **Don Mclaire Gonzales** - Developer

**Academic Details:**
- **Course:** IT-2111 Object Oriented Programming
- **Institution:** Batangas State University
- **Instructor:** Ms. Christiana Grace Alib
- **Date:** December 2024

---

## 🚀 Future Enhancements

- [ ] Persistent database storage (MySQL/PostgreSQL)
- [ ] Graphical User Interface (JavaFX/Swing)
- [ ] Rental pricing calculations and payment tracking
- [ ] Date/time tracking with automatic late fees
- [ ] Advanced search and filter capabilities
- [ ] Email/SMS notifications
- [ ] Export reports to CSV/PDF
- [ ] Multi-branch support
- [ ] Mobile application integration
- [ ] Dashboard analytics and statistics

---

## 📚 References

- [Java Documentation](https://docs.oracle.com/en/java/)
- [MessageDigest (SHA-256) API](https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html)
- [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/)
- Object-Oriented Programming Course Materials - Batangas State University
- Software Design Patterns Best Practices

---

## 📄 License

This project is created for **educational purposes** as part of the IT-2111 Object-Oriented Programming course at Batangas State University.

---

## Contributing

This is an academic project, but feedback and suggestions are welcome! Feel free to:
- Report bugs via Issues
- Suggest improvements
- Fork and experiment

---

## 📞 Contact

For questions or collaboration:
- **GitHub:** MikkoGarcia, Waidennn, ZeusDave
- **Email:** 24-02205@g.batstate-u.edu.ph , 24-01367@g.batstate-u.edu.ph , 24-04169@g.batstate-u.edu.ph

---

<div align="center">

### ⭐ If you find this project helpful, please consider giving it a star!

**Made with ☕ by BatStateU IT Students**

</div>
