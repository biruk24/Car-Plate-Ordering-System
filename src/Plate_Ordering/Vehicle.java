package Plate_Ordering;

  public abstract class Vehicle {

        protected String chassisNumber;
        protected String formerPlate;
        protected String ownerName;
        protected VehicleCategory category;


        public Vehicle(String chassisNumber, String formerPlate, String ownerName, VehicleCategory category) {
            this.chassisNumber = chassisNumber;
            this.formerPlate   = formerPlate;
            this.ownerName     = ownerName;
            this.category      = category;
        }


        public abstract String getVehicleInfo();

        public String getChassisNumber() {
            return chassisNumber;
        }
        public String getFormerPlate() {
            return formerPlate;
        }
        public String getOwnerName() {
            return ownerName;
        }
        public VehicleCategory getCategory() {
            return category;
        }
        public int getPrice() {
            return category.getPrice();
        }
    }

