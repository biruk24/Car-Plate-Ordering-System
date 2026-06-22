package Plate_Ordering;
public class PlateOrder {

   
    private static int counter = 1000;

    private String orderNumber;
    private String username;
    private String categoryName;
    private String chassisNumber;
    private String formerPlate;
    private String ownerName;
    private String virtualPlate;
    private int    price;
    private String status;
    private String date;

   
    public PlateOrder(String username, VehicleCategory category,
                      String chassis, String formerPlate, String ownerName, String date) {
        counter++;
        this.orderNumber  = "ETH-" + counter;
        this.username     = username;
        this.categoryName = category.getName();
        this.chassisNumber= chassis;
        this.formerPlate  = formerPlate;
        this.ownerName    = ownerName;
        this.price        = category.getPrice();
        this.status       = "PENDING";
        this.date         = date;
        // Generate virtual plate number
        this.virtualPlate = category.getPrefix() + "-" + (10000 + (int)(Math.random() * 89999));
    }

    
    public PlateOrder(String orderNumber, String username, String categoryName,
                      String chassis, String formerPlate, String ownerName,
                      String virtualPlate, int price, String status, String date) {
        this.orderNumber   = orderNumber;
        this.username      = username;
        this.categoryName  = categoryName;
        this.chassisNumber = chassis;
        this.formerPlate   = formerPlate;
        this.ownerName     = ownerName;
        this.virtualPlate  = virtualPlate;
        this.price         = price;
        this.status        = status;
        this.date          = date;
    }

    
    public String getOrderNumber()   { return orderNumber; }
    public String getUsername()      { return username; }
    public String getCategoryName()  { return categoryName; }
    public String getChassisNumber() { return chassisNumber; }
    public String getFormerPlate()   { return formerPlate; }
    public String getOwnerName()     { return ownerName; }
    public String getVirtualPlate()  { return virtualPlate; }
    public int    getPrice()         { return price; }
    public String getStatus()        { return status; }
    public String getDate()          { return date; }

    public void setStatus(String status) { this.status = status; }

    public static void setCounter(int c) { counter = c; }

    
    public String toFileLine() {
        return orderNumber + "|" + username + "|" + categoryName + "|" +
               chassisNumber + "|" + formerPlate + "|" + ownerName + "|" +
               virtualPlate + "|" + price + "|" + status + "|" + date;
    }

    
    public static PlateOrder fromFileLine(String line) {
        String[] p = line.split("\\|");
        if (p.length == 10) {
            return new PlateOrder(p[0], p[1], p[2], p[3], p[4],
                                  p[5], p[6], Integer.parseInt(p[7]), p[8], p[9]);
        }
        return null;
    }
}
