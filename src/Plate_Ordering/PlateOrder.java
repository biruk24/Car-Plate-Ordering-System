package Plate_Ordering;

public class PlateOrder {
    private String orderID;
    private int plateNumber;
    private User userBuyer;
    private Vehicle Vehicletype;
    private String Status;

    public PlateOrder(String orderID, int plateNumber, User userBuyer, Vehicle Vehicletype, String Status){
        this.orderID = orderID;
        this.plateNumber = plateNumber;
        this.userBuyer = userBuyer;
        this.Vehicletype = Vehicletype;
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
    public Vehicle getVehicletype(){
        return Vehicletype;
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
        System.out.println("VEHICLE: " + Vehicletype.getCategory());
        System.out.println("=============================");
    }

    




}
