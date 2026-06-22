package Plate_Ordering;
import java.util.*;


public class Database {

    
    private static Database instance = null;

    
    private List<User>       users;
    private List<PlateOrder> orders;

   
    private Database() {
        
        users  = FileHelper.loadUsers();
        orders = FileHelper.loadOrders();

        
        int max = 1000;
        for (PlateOrder o : orders) {
            try {
                int n = Integer.parseInt(o.getOrderNumber().replace("ETH-", ""));
                if (n > max) max = n;
            } catch (Exception e) {}
        }
        PlateOrder.setCounter(max);

      
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", "System Admin",   "0900000000", "ADMIN"));
            users.add(new User("abebe", "pass123",  "Abebe Kebede",   "0911234567", "USER"));
            FileHelper.saveUsers(users);
        }
    }

   
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

   
    public boolean usernameExists(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    
    public void addUser(User u) {
        users.add(u);
        FileHelper.saveUsers(users);
    }

    
    public List<User> getAllUsers() { return users; }

  
    public void addOrder(PlateOrder o) {
        orders.add(o);
        FileHelper.saveOrders(orders);
    }

    
    public List<PlateOrder> getOrdersByUser(String username) {
        List<PlateOrder> result = new ArrayList<>();
        for (PlateOrder o : orders) {
            if (o.getUsername().equals(username)) result.add(o);
        }
        return result;
    }

    
    public List<PlateOrder> getAllOrders() { return orders; }

    
    public PlateOrder findOrder(String orderNumber) {
        for (PlateOrder o : orders) {
            if (o.getOrderNumber().equalsIgnoreCase(orderNumber)) return o;
        }
        return null;
    }

    
    public void updateStatus(String orderNumber, String newStatus) {
        for (PlateOrder o : orders) {
            if (o.getOrderNumber().equalsIgnoreCase(orderNumber)) {
                o.setStatus(newStatus);
                break;
            }
        }
        FileHelper.saveOrders(orders);
    }
}
