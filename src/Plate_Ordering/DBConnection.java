package Plate_Ordering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_URL = "jdbc:sqlite:carplate.db";
    private static Connection connection = null;

    private DBConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                createTables();
            } catch (SQLException e) {
                System.out.println("Could not connect to database: " + e.getMessage());
            }
        }
        return connection;
    }

    private static void createTables() {
        String usersTable =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "    username TEXT PRIMARY KEY," +
                        "    password TEXT NOT NULL," +
                        "    fullName TEXT NOT NULL," +
                        "    phone    TEXT NOT NULL," +
                        "    role     TEXT NOT NULL" +
                        ")";

        String ordersTable =
                "CREATE TABLE IF NOT EXISTS orders (" +
                        "    orderNumber   TEXT PRIMARY KEY," +
                        "    username      TEXT NOT NULL," +
                        "    categoryName  TEXT NOT NULL," +
                        "    chassisNumber TEXT NOT NULL," +
                        "    formerPlate   TEXT NOT NULL," +
                        "    ownerName     TEXT NOT NULL," +
                        "    virtualPlate  TEXT NOT NULL," +
                        "    price         INTEGER NOT NULL," +
                        "    status        TEXT NOT NULL," +
                        "    orderDate     TEXT NOT NULL," +
                        "    FOREIGN KEY(username) REFERENCES users(username)" +
                        ")";

        try (Statement st = connection.createStatement()) {
            st.execute(usersTable);
            st.execute(ordersTable);
        } catch (SQLException e) {
            System.out.println("Could not create tables: " + e.getMessage());
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}