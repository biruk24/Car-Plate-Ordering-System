package Plate_Ordering;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String role;


   public User(String username, String password, String fullName,String phone,String role) {
       this.username = username;
       this.password = password;
       this.fullName = fullName;
       this.phone = phone;
       this.role = role;
   }
   public String getUsername(){
       return username;
   }
   public String getPassword(){
       return password;
   }
   public String getFullName(){
       return fullName;
   }
    public String getPhone(){
        return phone;
    }
   public String getRole(){
       return role;
   }
   public boolean isAdmin(){
       return role.equals("ADMIN");
   }
   public String toFileLine(){
       return username + "|" + password + "|" + fullName + "|" + phone + "|" + role;
   }
    public static User fromFileLine(String line) {
        String[] p = line.split("\\|");
        if (p.length == 5) {
            return new User(p[0], p[1], p[2], p[3], p[4]);
        }
        return null;
    }
}
