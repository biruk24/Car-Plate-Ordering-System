package Plate_Ordering;


import java.io.*;
import java.util.*;

public class Filee {

    private static final String USERS_FILE = "users.txt";
    private static final String ORDERS_FILE = "orders.txt";


    public static void saveUsers(List<User> users) {
        try {
            FileWriter fw = new FileWriter(USERS_FILE);
            BufferedWriter bw = new BufferedWriter(fw);

            for (User u : users) {
                bw.write(u.toFileLine());
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    public static List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists()) return list;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    User u = User.fromFileLine(line);
                    if (u != null) list.add(u);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return list;
    }

    public static void saveOrders(List<PlateOrder> orders) {
        try {
            FileWriter fw = new FileWriter(ORDERS_FILE);
            BufferedWriter bw = new BufferedWriter(fw);

            for (PlateOrder o : orders) {
                bw.write(o.toFileLine());
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }


    public static List<PlateOrder> loadOrders() {
        List<PlateOrder> list = new ArrayList<>();
        File file = new File(ORDERS_FILE);

        if (!file.exists()) return list;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    PlateOrder o = PlateOrder.fromFileLine(line);
                    if (o != null) list.add(o);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }

        return list;
    }

    public static void saveReceipt(String content, String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
            System.out.println("Receipt saved as: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}

