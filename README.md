# Ethiopia Car Plate Ordering System

A console-based Java application that simulates how a citizen orders a vehicle
plate through Ethiopia's Ministry of Transport and Logistics (MOTL). Built as
the final project for the Object-Oriented Programming course — the system was
designed so that every chapter from the course is demonstrated through one
working, integrated application rather than as separate disconnected exercises.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [How Each Course Chapter Is Covered](#how-each-course-chapter-is-covered)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [Default Accounts](#default-accounts)
- [Usage Walkthrough](#usage-walkthrough)
- [Database Schema](#database-schema)
- [Known Limitations / Future Improvements](#known-limitations--future-improvements)

## Features

- User registration and login, with a separate admin role
- Plate ordering across 13 official vehicle categories (Private, Commercial,
  Taxi, Bus, Government, Diplomatic, Electric, and more), each with its own
  price and plate prefix
- Automatic order number and virtual plate generation
- Order tracking by order number
- Admin dashboard: view all orders, update order status, view all users
- Persistent storage using a real SQLite database via JDBC
- Plain-text receipt generation using Java file streams
- Input validation and custom checked exceptions throughout

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Database | SQLite (via JDBC) |
| Persistence | `java.sql` (JDBC), `java.io` (File Streams) |
| Interface | Console / Command-line |
| IDE | IntelliJ IDEA |

## Project Structure

```
Car Plate Ordering System/
├── src/Plate_Ordering/
│   ├── Main.java                 # Entry point, menus, user interaction
│   ├── User.java                 # Represents one account
│   ├── Vehicle.java              # Abstract base class for vehicles
│   ├── PrivateVehicle.java       # Vehicle subtype
│   ├── CommercialVehicle.java    # Vehicle subtype
│   ├── VehicleCategory.java      # Enum of the 13 plate categories
│   ├── PlateOrder.java           # Represents one submitted order
│   ├── ValidationException.java # Custom checked exceptions
│   ├── FileHelper.java           # Writes plain-text receipt files
│   ├── DBConnection.java         # Opens the JDBC connection, creates tables
│   └── Database.java             # All JDBC SQL queries
├── carplate.db                   # SQLite database file (created at runtime)
└── README.md
```

## How Each Course Chapter Is Covered

| Chapter | Demonstrated in | What to look at |
|---|---|---|
| 1. OOP Basics (encapsulation) | `User`, `PlateOrder`, `VehicleCategory` | All fields are `private`; accessed only through getters/setters |
| 2. OOP Fundamentals (constructors, `static`, enums) | `PlateOrder` (overloaded constructors), `Database` (Singleton via `getInstance()`), `VehicleCategory` (enum with fields) | The Singleton pattern and the 13-value enum, each carrying its own price/prefix |
| 3. Inheritance | `Vehicle` (abstract parent), `PrivateVehicle`, `CommercialVehicle` | `super(...)` calls in both subclass constructors |
| 4. Polymorphism | `Vehicle.getVehicleInfo()` is abstract; overridden differently per subclass, called polymorphically in `Main.orderNewPlate()` | Same method call, different output depending on the real object type |
| 5. Exception Handling | `LoginException`, `ValidationException` (custom checked exceptions), used throughout `Main.java` | Try invalid input (blank fields, bad category number) — handled gracefully, no crash |
| 6. Files & Streams | `FileHelper.java`, used by `Main.buildReceipt()` | `FileWriter`/`BufferedWriter` writing a `.txt` receipt to disk |
| 7. JDBC (Database Connectivity) | `DBConnection.java`, `Database.java` | Real SQL via `PreparedStatement`/`ResultSet`, stored in `carplate.db` |

## Setup & Installation

### Prerequisites

- Java JDK 8 or higher
- IntelliJ IDEA (or any Java IDE)
- SQLite JDBC driver (`sqlite-jdbc-3.x.x.jar`)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/Car-Plate-Ordering-System.git
   cd "Car Plate Ordering System"
   ```
2. Download the SQLite JDBC driver jar from
   [Maven Central](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc) (used
   version: `3.53.2.0`).
3. Add it to the project in IntelliJ:
   `File → Project Structure → Libraries → + → Java`, then select the
   downloaded jar.
4. Make sure `src` is marked as the Sources Root.

