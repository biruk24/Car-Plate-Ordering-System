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

## Running the Application

1. Open the project in IntelliJ IDEA.
2. Run `Main.java` (`Plate_Ordering` package).
3. On first run, the program automatically:
   - Connects to (and creates, if needed) `carplate.db`
   - Creates the `users` and `orders` tables
   - Seeds one admin account and one sample user account
4. Follow the on-screen menu to log in, register, or exit.

## Default Accounts

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `abebe` | `pass123` | USER |

> These are seeded automatically the first time the program runs against an
> empty database.

## Usage Walkthrough

**As a regular user:**
1. Register a new account or log in with `abebe` / `pass123`.
2. Choose **Order New Plate**, pick a vehicle category (1–13), and enter the
   chassis number, former plate, owner name, and color.
3. Receive an order number (e.g. `ETH-1001`) and a randomly generated virtual
   plate.
4. Use **My Orders** to view all your past orders, or **Track Order** to look
   up any order by its order number.

**As an admin:**
1. Log in with `admin` / `admin123`.
2. Use **View All Orders** to see every order in the system.
3. Use **Update Order Status** to move an order through
   `PENDING → APPROVED → READY → COLLECTED`.
4. Use **View All Users** to see every registered account.

## Database Schema

```sql
CREATE TABLE users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    fullName TEXT NOT NULL,
    phone    TEXT NOT NULL,
    role     TEXT NOT NULL
);

CREATE TABLE orders (
    orderNumber   TEXT PRIMARY KEY,
    username      TEXT NOT NULL,
    categoryName  TEXT NOT NULL,
    chassisNumber TEXT NOT NULL,
    formerPlate   TEXT NOT NULL,
    ownerName     TEXT NOT NULL,
    virtualPlate  TEXT NOT NULL,
    price         INTEGER NOT NULL,
    status        TEXT NOT NULL,
    orderDate     TEXT NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);
```

## Known Limitations / Future Improvements

- Passwords are stored in plain text; a future version should hash them
  (e.g. SHA-256) before storing.
- The connection opened in `DBConnection` is never explicitly closed; fine for
  a short-lived console app, but a GUI/long-running version should close it
  on exit.
- No GUI — built as a console application to keep focus on the core OOP and
  JDBC concepts.
- `carplate.db` is excluded from version control (see `.gitignore`) so each
  environment starts with a fresh, freshly-seeded database.
