LTO Car Rental Registration & Monitoring System
<p align="center"> <img src="https://img.shields.io/badge/Java-25-blue?style=flat-square" alt="Java Version"> <img src="https://img.shields.io/github/stars/yourusername/LTOCarRental?style=flat-square" alt="Stars"> <img src="https://img.shields.io/github/license/yourusername/LTOCarRental?style=flat-square" alt="License"> </p>

Overview
CLI app for managing car rentals with secure authentication, audit logs, and OOP principles.

Features
Secure login (SHA‑256 hashing)

Car inventory management

Admin dashboard with audit logs

Real‑time rental status updates

Class Diagram

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


Quickstart
<details> <summary><strong>Show setup instructions</strong></summary>

# Compile
javac LtoCarRental.java

# Run
java LtoCarRental

Default credentials:

Admin → admin / admin123

User → user / user123 </details>

Screenshots
<p align="center"> <img src="docs/demo.gif" alt="Demo preview" width="600"> </p>

Future Enhancements
Persistent DB (MySQL/PostgreSQL)

GUI with JavaFX/Swing

Rental pricing + payment tracking

Export logs to CSV/PDF

Authors
Zeus Dave De Chavez
Mikko Garcia
Don McLaire Gonzales BatStateU • December 2025
