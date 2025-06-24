# SmartServe – Smart Canteen Automation System

SmartServe is a standalone command-line and GUI-based food ordering and canteen management system developed in Java. Designed to replicate a real-world canteen environment, the system supports customer and admin roles with features like menu browsing, order processing, user management, and persistent data storage.

---

## Overview

SmartServe was developed focusing on object-oriented programming, file-based data handling, and software testing practices. The system provides a complete digital interface for managing canteen operations while ensuring data persistence and user role differentiation.

---

## Features

### Customer Functionalities
- **Registration and Login**: Users can register and log in through a secure CLI interface.
- **VIP and Regular Modes**: VIP customers receive priority in order processing.
- **Menu Browsing**: View menu items with filtering, sorting, and category-based searches.
- **Cart Management**: Add, remove, or update item quantities before checkout.
- **Order Placement**: Confirm orders and receive real-time status updates.
- **Order Tracking**: Monitor current order status and view historical orders.
- **Review System**: Submit and read reviews for individual menu items.

### Admin Functionalities
- **Menu Management**: Add, update, or delete menu items.
- **Order Handling**: View and update order statuses, process refunds, and handle special requests.
- **Daily Reports**: Generate sales and performance reports for the day.

### GUI Functionality
- **Read-Only Interface**: GUI is designed for displaying menu items and pending orders.
- **Navigation Support**: Switch between menu and order views via buttons.
- **Sync with CLI**: GUI reflects changes made through CLI using I/O file exchange.

### File I/O Persistence
- **User Data Storage**: User details are stored and retrieved from file-based storage.
- **Order History**: Individual order histories are saved for each user.
- **Cart Persistence**: Cart data is stored during user sessions for recovery and consistency.

### Testing (JUnit)
- **Login Validation**: Handles invalid login attempts.
- **Cart Validations**: Includes tests for cart operations like price update, negative quantity prevention, etc.

---

## Architecture

- **CLI**: Full-featured command-line interface for operations.
- **GUI (JavaFX)**: Read-only interface for menu and orders.
- **Role-Based Classes**: Separate logic for Admin and Customers (VIP/Regular).
- **Data Layer**: File I/O for user and order persistence.
- **Test Layer**: JUnit-based test cases for key scenarios.

---

## Technology Stack

- **Java**
- **JavaFX**
- **JUnit 5**
- **Java Collections Framework**
- **File I/O Streams**

---

## Getting Started

### Prerequisites
- Java 17+ and JavaFX installed
- JUnit 5 (for testing)

### Steps to Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/palak363/SmartServe
   cd SmartServe
