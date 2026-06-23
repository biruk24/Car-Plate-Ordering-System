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

