package Plate_Ordering;
public class CommercialVehicle extends Vehicle {

    private String businessName;

    public CommercialVehicle(String chassisNumber, String formerPlate,
                             String ownerName, VehicleCategory category, String businessName) {
        super(chassisNumber, formerPlate, ownerName, category);
        this.businessName = businessName;
    }

    @Override
    public String getVehicleInfo() {
        return "Commercial Vehicle | Business: " + businessName + " | Owner: " + ownerName;
    }

    public String getBusinessName() { return businessName; }
}

