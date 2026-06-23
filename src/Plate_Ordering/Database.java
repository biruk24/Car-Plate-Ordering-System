package Plate_Ordering;
import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance = null;
    private Connection conn;

    private Database() {
        conn = DBConnection.getConnection();
        seedAdminIfEmpty();
        initOrderCounter();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    private void seedAdminIfEmpty() {
        if (getAllUsers().isEmpty()) {
            addUser(new User("admin", "admin123", "System Admin", "0900000000", "ADMIN"));
            addUser(new User("abebe", "pass123", "Abebe Kebede", "0911234567", "USER"));
        }
    }

    private void initOrderCounter() {
        int max = 1000;
        String sql = "SELECT orderNumber FROM orders";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                try {
                    int n = Integer.parseInt(rs.getString("orderNumber").replace("ETH-", ""));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) {}
            }
        } catch (SQLException e) {
            System.out.println("Error reading order counter: " + e.getMessage());
        }
        PlateOrder.setCounter(max);
    }


    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE LOWER(username) = LOWER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
            return false;
        }
    }

    public void addUser(User u) {
        String sql = "INSERT INTO users (username, password, fullName, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return list;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullName"),
                rs.getString("phone"),
                rs.getString("role")
        );
    }


    public void addOrder(PlateOrder o) {
        String sql = "INSERT INTO orders (orderNumber, username, categoryName, chassisNumber, " +
                "formerPlate, ownerName, virtualPlate, price, status, orderDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getOrderNumber());
            ps.setString(2, o.getUsername());
            ps.setString(3, o.getCategoryName());
            ps.setString(4, o.getChassisNumber());
            ps.setString(5, o.getFormerPlate());
            ps.setString(6, o.getOwnerName());
            ps.setString(7, o.getVirtualPlate());
            ps.setInt(8, o.getPrice());
            ps.setString(9, o.getStatus());
            ps.setString(10, o.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding order: " + e.getMessage());
        }
    }

    public List<PlateOrder> getOrdersByUser(String username) {
        List<PlateOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return list;
    }

    public List<PlateOrder> getAllOrders() {
        List<PlateOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error loading all orders: " + e.getMessage());
        }
        return list;
    }

    public PlateOrder findOrder(String orderNumber) {
        String sql = "SELECT * FROM orders WHERE UPPER(orderNumber) = UPPER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding order: " + e.getMessage());
        }
        return null;
    }

    public void updateStatus(String orderNumber, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE UPPER(orderNumber) = UPPER(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, orderNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    private PlateOrder mapOrder(ResultSet rs) throws SQLException {
        return new PlateOrder(
                rs.getString("orderNumber"),
                rs.getString("username"),
                rs.getString("categoryName"),
                rs.getString("chassisNumber"),
                rs.getString("formerPlate"),
                rs.getString("ownerName"),
                rs.getString("virtualPlate"),
                rs.getInt("price"),
                rs.getString("status"),
                rs.getString("orderDate")
        );
    }
}

