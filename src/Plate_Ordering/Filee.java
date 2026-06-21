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

}
