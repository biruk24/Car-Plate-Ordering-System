package Plate_Ordering;

public class PlateOrder {
    private String orderID;
    private int plateNumber;
    private String userBuyer;
    private String targetVehicle;
    private String Status;

    public PlateOrder(String orderID, int plateNumber, String userBuyer, String targetVehicle, String Status){
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
    public String getuserBuyer(){
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





}
