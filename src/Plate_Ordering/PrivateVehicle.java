package Plate_Ordering;


public class PrivateVehicle extends Vehicle {

    private String color;


    public PrivateVehicle(String chassisNumber, String formerPlate,
                          String ownerName, VehicleCategory category, String color) {
        super(chassisNumber, formerPlate, ownerName, category);
        this.color = color;
    }


    @Override
    public String getVehicleInfo() {
        return "Private Vehicle | Owner: " + ownerName + " | Color: " + color;
    }

    public String getColor() { return color; }
}
