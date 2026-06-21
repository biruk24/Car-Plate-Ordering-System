package Plate_Ordering;

public class PlateOrder {
    private String orderID;
    private int plateNumber;
    private User userBuyer;
    private String targetVehicle;
    private String Status;

    public PlateOrder(String orderID, int plateNumber, User userBuyer, String targetVehicle, String Status){
        this.orderID = orderID;
        this.plateNumber = plateNumber;
        this.userBuyer = userBuyer;
        this.targetVehicle = targetVehicle;
        this.Status = "pending";

    }

    public String getrderID(){
        return orderID;
    }
    public int getplatenumber(){
        return plateNumber;
    }
    public void setplateNumber(int plateNumber){
        this.plateNumber = plateNumber;
    }
    public User getuserBuyer(){
        return userBuyer;
    }
    public String gettargetVehicle(){
        return targetVehicle;
    }
    public String getStatus(){
        return Status;
    }
    public void setStatus(String Status){
        this.Status = Status;
    }


    public void PlateOrderDisplay (){
        System.out.println("===== PLATE ORDER #" + orderID + " =====");
        System.out.println("STATUS: " + Status);
        System.out.println("REQUESTED PLATE: " + plateNumber);
        System.out.println("CUSTOMER: " + userBuyer.getFullName());
        System.out.println("VEHICLE: " + targetVehicle );
        System.out.println("=============================");
    }

    public static void main(String[] args) {
        User cust = new User("abc", "14qw", "kebe", "0914257861", "buy");

        PlateOrder order = new PlateOrder("1441", 5545, cust, "nissan", "pending");

        order.PlateOrderDisplay();
    }




}
