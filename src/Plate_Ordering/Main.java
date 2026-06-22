import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {

    static Scanner scanner = new Scanner(System.in);


    static User currentUser = null;

    public static void main(String[] args) {


        Database.getInstance();

        System.out.println("==============================================");
        System.out.println("   ETHIOPIA CAR PLATE ORDERING SYSTEM");
        System.out.println("   Ethiopian Transport Authority (ETA)");
        System.out.println("==============================================");


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


        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
    String username = scanner.nextLine().trim();
        System.out.print("Password: ");
    String password = scanner.nextLine().trim();

        try {
        // Chapter 5:
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
        System.out.println("Login failed: " + e.getMessage());
    }
}


static void register() {
    System.out.println("\n--- REGISTER NEW ACCOUNT ---");

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

