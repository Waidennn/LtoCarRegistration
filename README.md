<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LTO Car Rental System - README</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            border-bottom: 3px solid #3498db;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        h2 {
            color: #2980b9;
            margin-top: 30px;
            margin-bottom: 15px;
            border-left: 4px solid #3498db;
            padding-left: 10px;
        }
        h3 {
            color: #34495e;
            margin-top: 20px;
            margin-bottom: 10px;
        }
        p {
            margin-bottom: 15px;
            text-align: justify;
        }
        ul, ol {
            margin-bottom: 15px;
            padding-left: 30px;
        }
        li {
            margin-bottom: 8px;
        }
        code {
            background-color: #f4f4f4;
            padding: 2px 6px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
            font-size: 0.9em;
        }
        pre {
            background-color: #2c3e50;
            color: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
            overflow-x: auto;
            margin: 15px 0;
        }
        pre code {
            background-color: transparent;
            color: #ecf0f1;
            padding: 0;
        }
        .class-diagram {
            background-color: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
            font-family: 'Courier New', monospace;
            white-space: pre;
            overflow-x: auto;
            margin: 15px 0;
        }
        .author-section {
            background-color: #e8f4f8;
            padding: 20px;
            border-radius: 5px;
            margin-top: 30px;
        }
        .note {
            background-color: #fff3cd;
            border-left: 4px solid #ffc107;
            padding: 12px;
            margin: 15px 0;
        }
        strong {
            color: #2c3e50;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>LTO Car Rental Registration & Monitoring System</h1>

        <h2>Description/Overview</h2>
        <p>
            The LTO Car Rental Registration & Monitoring System is a command-line interface (CLI) 
            application designed to streamline car rental management operations. This system enables 
            administrators to oversee user accounts and vehicle registrations, while allowing regular 
            users to manage their car inventory, track rental status, and maintain driver information. 
            The application solves the problem of manual car rental tracking by providing secure 
            authentication, real-time rental status updates, automated driver code generation, and 
            comprehensive audit logging for accountability and monitoring.
        </p>

        <h2>OOP Concepts Applied</h2>

        <h3>Inheritance</h3>
        <p>
            The project implements a hierarchical class structure with an abstract <code>Account</code> 
            class serving as the parent for two child classes: <code>AdminAccount</code> and 
            <code>UserAccount</code>. Both subclasses inherit common properties (<code>username</code>, 
            <code>passwordHash</code>, <code>role</code>) and methods (<code>getUsername()</code>, 
            <code>getPasswordHash()</code>, <code>getRole()</code>) while providing their own 
            implementations of the abstract <code>showDashboard()</code> method.
        </p>

        <h3>Polymorphism</h3>
        <p>
            Polymorphism is demonstrated through method overriding, where both <code>AdminAccount</code> 
            and <code>UserAccount</code> provide their own implementations of the abstract 
            <code>showDashboard()</code> method from the parent <code>Account</code> class. The 
            <code>toString()</code> method is also overridden in both <code>Car</code> and 
            <code>DriverInfo</code> classes to provide custom string representations.
        </p>

        <h3>Encapsulation</h3>
        <p>
            Private and protected access modifiers encapsulate data within classes. For example, the 
            <code>Account</code> class uses <code>protected</code> fields for <code>username</code>, 
            <code>passwordHash</code>, and <code>role</code>, accessible only within the class hierarchy, 
            while the <code>Car</code> class uses <code>private</code> fields like <code>plateNumber</code>, 
            <code>brand</code>, <code>model</code>, and <code>year</code> with public getter methods 
            providing controlled access.
        </p>

        <h3>Abstraction</h3>
        <p>
            The abstract <code>Account</code> class defines the contract for all account types through 
            the abstract <code>showDashboard()</code> method, hiding implementation details from the 
            main application logic. Service classes (<code>AuthService</code>, <code>AdminService</code>, 
            <code>UserService</code>) abstract complex business logic operations, separating concerns 
            from the presentation layer.
        </p>

        <h2>Program Structure</h2>

        <h3>Main Classes and Roles</h3>
        <ul>
            <li><strong>LtoCarRental</strong>: Entry point class containing the main method that 
                initializes and runs the application</li>
            <li><strong>App</strong>: Manages the main application loop and displays the primary menu 
                for login, registration, audit logs, and exit options</li>
            <li><strong>Account</strong> (abstract): Base class defining common properties and behaviors 
                for all account types with an abstract <code>showDashboard()</code> method</li>
            <li><strong>AdminAccount</strong>: Extends Account to provide administrative privileges 
                including user management, car assignment/deletion, and audit log access</li>
            <li><strong>UserAccount</strong>: Extends Account to allow users to manage their personal 
                car inventory and rental operations</li>
            <li><strong>Car</strong>: Represents a vehicle with properties like plate number, brand, 
                model, year, rental status, and associated driver information</li>
            <li><strong>DriverInfo</strong>: Stores driver details including name, license number, 
                and a unique rental code</li>
            <li><strong>Database</strong>: Simulates a database using static lists to store accounts 
                and audit logs, pre-seeded with default admin and user accounts</li>
            <li><strong>AuthService</strong>: Handles user authentication, registration for both users 
                and admins with password hashing via SHA-256</li>
            <li><strong>AdminService</strong>: Provides administrative functionality including viewing 
                users, managing cars, and accessing audit logs</li>
            <li><strong>UserService</strong>: Manages user-specific operations such as viewing cars, 
                adding cars, marking rentals, and completing rentals</li>
            <li><strong>HashUtil</strong>: Utility class for SHA-256 password hashing to ensure secure 
                credential storage</li>
            <li><strong>CodeGenerator</strong>: Generates unique 8-character alphanumeric codes for 
                driver identification</li>
        </ul>

        <h3>Class Relationships</h3>
        <div class="class-diagram">LtoCarRental (main class)
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
CodeGenerator (utility)</div>

        <h2>How to Run the Program</h2>
        <p>Follow these step-by-step instructions to compile and run the program from the command line:</p>

        <ol>
            <li><strong>Ensure Java is installed</strong>: Verify that Java Development Kit (JDK) 11 
                or higher is installed by running:
                <pre><code>java -version</code></pre>
            </li>

            <li><strong>Save the file</strong>: Ensure the file is saved as <code>LtoCarRental.java</code> 
                in your working directory.</li>

            <li><strong>Compile the program</strong>: Navigate to the directory containing the file and run:
                <pre><code>javac LtoCarRental.java</code></pre>
            </li>

            <li><strong>Run the program</strong>: After successful compilation, execute:
                <pre><code>java LtoCarRental</code></pre>
            </li>

            <li><strong>Default credentials</strong>: The system is pre-seeded with:
                <ul>
                    <li>Admin - Username: <code>admin</code>, Password: <code>admin123</code></li>
                    <li>User - Username: <code>user</code>, Password: <code>user123</code></li>
                </ul>
            </li>
        </ol>

        <h2>Sample Output</h2>
        <pre><code>Starting LTO Car Rental System (single-file demo).

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
Car added to your account.</code></pre>

        <div class="author-section">
            <h2>Author and Acknowledgements</h2>
            <p>
                <strong>Author:</strong> [Your Name]<br>
                <strong>Course:</strong> [Course Name/Code]<br>
                <strong>Institution:</strong> [Your Institution]<br>
                <strong>Date:</strong> December 2025
            </p>
            <p>
                Special thanks to my instructors and classmates for their guidance and support 
                throughout this project.
            </p>
        </div>

        <h2>Future Enhancements</h2>
        <ul>
            <li>Implement persistent database storage using MySQL or PostgreSQL instead of in-memory lists</li>
            <li>Add a graphical user interface (GUI) using JavaFX or Swing</li>
            <li>Implement rental pricing calculations and payment tracking</li>
            <li>Add date/time tracking for rental periods with automatic late fee calculation</li>
            <li>Implement search and filter capabilities for cars and users</li>
            <li>Add email notifications for rental confirmations and reminders</li>
            <li>Export audit logs and reports to CSV or PDF format</li>
        </ul>

        <h2>References</h2>
        <ul>
            <li>Java Documentation: <a href="https://docs.oracle.com/en/java/" target="_blank">https://docs.oracle.com/en/java/</a></li>
            <li>MessageDigest (SHA-256): <a href="https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html" target="_blank">Java MessageDigest API</a></li>
            <li>Object-Oriented Programming Principles: Course materials and lecture notes</li>
        </ul>
    </div>
</body>
</html>
