package Plate_Ordering;

import Plate_Ordering.User;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    static User currentUser = null;

    public static void main(String[] args) {
        Database.getInstance();

        System.out.println(" ");
        System.out.println("   ETHIOPIA CAR PLATE ORDERING SYSTEM");
        System.out.println("   Minister of Transport and Logistic(MOTL)");
        System.out.println(" ");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                login();
            } else if (choice.equals("2")) {
                register();
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void login() {
        System.out.println("\n LOGIN ");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        try {
            if (username.isEmpty() || password.isEmpty()) {
                throw new LoginException("Username and password cannot be empty.");
            }

            User user = Database.getInstance().login(username, password);

            if (user == null) {
                throw new LoginException("Wrong username or password.");
            }

            currentUser = user;
            System.out.println("\nWelcome, " + user.getFullName() + "! (" + user.getRole() + ")");

            if (user.isAdmin()) {
                adminMenu();
            } else {
                userMenu();
            }

        } catch (LoginException e) {
            // Chapter 5: catch and print the error
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    static void register() {
        System.out.println("\n REGISTER NEW ACCOUNT ");

        System.out.print("Full Name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Confirm Password: ");
        String confirm = scanner.nextLine().trim();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine().trim();

        try {
            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                throw new ValidationException("All fields are required.");
            }
            if (username.length() < 4) {
                throw new ValidationException("Username must be at least 4 characters.");
            }
            if (password.length() < 6) {
                throw new ValidationException("Password must be at least 6 characters.");
            }
            if (!password.equals(confirm)) {
                throw new ValidationException("Passwords do not match.");
            }
            if (phone.length() < 10) {
                throw new ValidationException("Phone must be at least 10 digits.");
            }
            if (Database.getInstance().usernameExists(username)) {
                throw new ValidationException("Username '" + username + "' is already taken.");
            }

            User newUser = new User(username, password, fullName, phone, "USER");
            Database.getInstance().addUser(newUser);

            System.out.println("Account created! You can now login with username: " + username);

        } catch (ValidationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    static void userMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Order New Plate");
            System.out.println("2. My Orders");
            System.out.println("3. Track Order");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) orderNewPlate();
            else if (choice.equals("2")) myOrders();
            else if (choice.equals("3")) trackOrder();
            else if (choice.equals("4")) {
                currentUser = null;
                running = false;
            } else System.out.println("Invalid choice.");
        }
    }

    static void orderNewPlate() {
        System.out.println("\n--- ORDER NEW PLATE ---");

        // Show all 13 categories
        System.out.println("\nVehicle Categories:");
        VehicleCategory[] cats = VehicleCategory.values();
        for (int i = 0; i < cats.length; i++) {
            String price = "ETB " + cats[i].getPrice();
            System.out.println((i + 1) + ". " + cats[i].getName() + " - " + price);
        }

        System.out.print("\nChoose category (1-13): ");
        String catInput = scanner.nextLine().trim();

        System.out.print("Chassis Number: ");
        String chassis = scanner.nextLine().trim();

        System.out.print("Former Plate Number: ");
        String formerPlate = scanner.nextLine().trim();

        System.out.print("Vehicle Owner Name: ");
        String ownerName = scanner.nextLine().trim();

        System.out.print("Vehicle Color: ");
        String color = scanner.nextLine().trim();

        try {
            int catIndex;
            try {
                catIndex = Integer.parseInt(catInput) - 1;
            } catch (NumberFormatException e) {
                throw new ValidationException("Please enter a number between 1 and 13.");
            }

            if (catIndex < 0 || catIndex >= cats.length) {
                throw new ValidationException("Category must be between 1 and 13.");
            }
            if (chassis.isEmpty() || formerPlate.isEmpty() || ownerName.isEmpty()) {
                throw new ValidationException("Chassis, former plate, and owner name are required.");
            }
            if (chassis.length() < 5) {
                throw new ValidationException("Chassis number must be at least 5 characters.");
            }

            VehicleCategory selectedCat = cats[catIndex];

            Vehicle vehicle;
            if (selectedCat == VehicleCategory.COMMERCIAL || selectedCat == VehicleCategory.CROSS_BORDER_CARGO) {
                vehicle = new CommercialVehicle(chassis, formerPlate, ownerName, selectedCat, ownerName + " Co.");
            } else {
                vehicle = new PrivateVehicle(chassis, formerPlate, ownerName, selectedCat, color.isEmpty() ? "Unknown" : color);
            }

            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            PlateOrder order = new PlateOrder(currentUser.getUsername(), selectedCat,
                    chassis, formerPlate, ownerName, date);

            Database.getInstance().addOrder(order);

            System.out.println("\nVehicle Info: " + vehicle.getVehicleInfo());
            System.out.println("\n==============================================");
            System.out.println("  ORDER SUBMITTED SUCCESSFULLY!");
            System.out.println("==============================================");
            System.out.println("  Order Number  : " + order.getOrderNumber());
            System.out.println("  Virtual Plate : " + order.getVirtualPlate());
            System.out.println("  Category      : " + order.getCategoryName());
            System.out.println("  Amount        : " + ("ETB " + order.getPrice()));
            System.out.println("  Date          : " + order.getDate());
            System.out.println("==============================================");
            System.out.println("  Save your ORDER NUMBER to collect your");
            System.out.println("  physical plate at the Transport Authority.");
            System.out.println("==============================================");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void myOrders() {
        System.out.println("\n--- MY ORDERS ---");

        List<PlateOrder> orders = Database.getInstance().getOrdersByUser(currentUser.getUsername());

        if (orders.isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-25s %-15s %-15s %-10s %-18s%n",
                "Order #", "Category", "Owner Name", "Virtual Plate", "Amount", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        for (PlateOrder o : orders) {
            System.out.printf("%-12s %-25s %-15s %-15s %-10s %-18s%n",
                    o.getOrderNumber(),
                    o.getCategoryName(),
                    o.getOwnerName(),
                    o.getVirtualPlate(),
                    o.getPrice() == 0 ? "FREE" : "ETB " + o.getPrice(),
                    o.getStatus());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
    }

    static void trackOrder() {
        System.out.println("\n--- TRACK ORDER ---");
        System.out.print("Enter Order Number (e.g. ETH-1001): ");
        String orderNum = scanner.nextLine().trim().toUpperCase();

        try {
            if (orderNum.isEmpty()) {
                throw new ValidationException("Please enter an order number.");
            }

            PlateOrder o = Database.getInstance().findOrder(orderNum);

            if (o == null) {
                throw new ValidationException("Order not found: " + orderNum);
            }

            System.out.println("\n==============================================");
            System.out.println("   ETHIOPIA PLATE ORDER DETAILS");
            System.out.println("==============================================");
            System.out.println("  Order Number  : " + o.getOrderNumber());
            System.out.println("  Date          : " + o.getDate());
            System.out.println("----------------------------------------------");
            System.out.println("  Owner Name    : " + o.getOwnerName());
            System.out.println("  Category      : " + o.getCategoryName());
            System.out.println("  Chassis #     : " + o.getChassisNumber());
            System.out.println("  Former Plate  : " + o.getFormerPlate());
            System.out.println("----------------------------------------------");
            System.out.println("  Virtual Plate : " + o.getVirtualPlate());
            System.out.println("  Amount        : " + (o.getPrice() == 0 ? "FREE" : "ETB " + o.getPrice()));
            System.out.println("  STATUS        : " + o.getStatus());
            System.out.println("==============================================");

            if (o.getStatus().equals("READY") || o.getStatus().equals("APPROVED")) {
                System.out.println("  Your plate is READY. Visit the Transport");
                System.out.println("  Authority office with your order number.");
            } else if (o.getStatus().equals("PENDING")) {
                System.out.println("  Your order is being processed. Please wait.");
            } else if (o.getStatus().equals("COLLECTED")) {
                System.out.println("  You have already collected this plate.");
            }
            System.out.println("==============================================");

        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View All Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. View All Users");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if      (choice.equals("1")) viewAllOrders();
            else if (choice.equals("2")) updateOrderStatus();
            else if (choice.equals("3")) viewAllUsers();
            else if (choice.equals("4")) { currentUser = null; running = false; }
            else System.out.println("Invalid choice.");
        }
    }

    static void viewAllOrders() {
        System.out.println("\n--- ALL ORDERS ---");
        List<PlateOrder> orders = Database.getInstance().getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-12s %-22s %-15s %-15s %-8s %-12s%n",
                "Order #", "Username", "Category", "Owner", "Virtual Plate", "Amount", "Status");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        for (PlateOrder o : orders) {
            System.out.printf("%-12s %-12s %-22s %-15s %-15s %-8s %-12s%n",
                    o.getOrderNumber(),
                    o.getUsername(),
                    o.getCategoryName(),
                    o.getOwnerName(),
                    o.getVirtualPlate(),
                    o.getPrice() == 0 ? "FREE" : "" + o.getPrice(),
                    o.getStatus());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    static void updateOrderStatus() {
        System.out.println("\n--- UPDATE ORDER STATUS ---");
        System.out.print("Enter Order Number: ");
        String orderNum = scanner.nextLine().trim().toUpperCase();

        try {
            PlateOrder o = Database.getInstance().findOrder(orderNum);
            if (o == null) {
                throw new ValidationException("Order not found: " + orderNum);
            }

            System.out.println("Current status: " + o.getStatus());
            System.out.println("1. PENDING");
            System.out.println("2. APPROVED");
            System.out.println("3. READY");
            System.out.println("4. COLLECTED");
            System.out.print("Choose new status: ");
            String choice = scanner.nextLine().trim();

            String newStatus;
            if      (choice.equals("1")) newStatus = "PENDING";
            else if (choice.equals("2")) newStatus = "APPROVED";
            else if (choice.equals("3")) newStatus = "READY";
            else if (choice.equals("4")) newStatus = "COLLECTED";
            else throw new ValidationException("Invalid choice. Enter 1-4.");

            Database.getInstance().updateStatus(orderNum, newStatus);
            System.out.println("Status updated to: " + newStatus);

        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAllUsers() {
        System.out.println("\n--- ALL USERS ---");
        List<User> users = Database.getInstance().getAllUsers();

        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-8s%n", "Username", "Full Name", "Phone", "Role");
        System.out.println("-----------------------------------------------------------");

        for (User u : users) {
            System.out.printf("%-15s %-20s %-15s %-8s%n",
                    u.getUsername(), u.getFullName(), u.getPhone(), u.getRole());
        }
        System.out.println("-----------------------------------------------------------");
    }

    static String buildReceipt(PlateOrder o) {
        return "================================================\n"
                + "   ETHIOPIA CAR PLATE RECEIPT\n"
                + "   Minister of Transport and Logistic(MOTL)"
                + "================================================\n"
                + "Order Number  : " + o.getOrderNumber() + "\n"
                + "Date          : " + o.getDate() + "\n"
                + "Applicant     : " + currentUser.getFullName() + "\n"
                + "Phone         : " + currentUser.getPhone() + "\n"
                + "------------------------------------------------\n"
                + "Owner Name    : " + o.getOwnerName() + "\n"
                + "Category      : " + o.getCategoryName() + "\n"
                + "Chassis #     : " + o.getChassisNumber() + "\n"
                + "Former Plate  : " + o.getFormerPlate() + "\n"
                + "------------------------------------------------\n"
                + "Virtual Plate : " + o.getVirtualPlate() + "\n"
                + "Amount        : " + (o.getPrice() == 0 ? "FREE" : "ETB " + o.getPrice()) + "\n"
                + "Status        : " + o.getStatus() + "\n"
                + "================================================\n"
                + "Present this receipt at the ETA office to\n"
                + "collect your physical plate.\n"
                + "================================================\n";
    }
}



