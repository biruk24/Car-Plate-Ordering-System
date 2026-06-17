package Plate_Ordering;

public enum VehicleCategory {
    BUS("BUS", "AAL(B)", 9400),
    TAXI("Taxi", "AAL(T)", 11700),
    PERSONAL_DISABILITY("Personal Disability", "AAL(PD)", 9400),
    PRIVATE_CAR("Private Car", "AAL(P)", 56000),
    COMMERCIAL("Commercial Car", "AAL(C)",56000),
    GOVERNMENT("Government Car", "AAL(G)", 9400),
    CROSS_BORDER_CARGO("Cross Border Cargo(CA)", "AAL", 28500),
    INTERNATIONAL_ORG("International or Continential Organization", "AAL(IO)", 56000),
    DIPLOMATIC("CODiplomat", "AAL", 56000),
    AID_ORGANIZATION("AID Organization", "AAL(AO)", 56000),
    BAJAJ("Bajaj", "AAL(BA)", 9400),
    MOTORCYCLE("Motorcycle", "AAL(M)", 4700),
    ELECTRIC_CAR("Electric Car", "AAA(EV)", 44500);


    private String name;
    private String prefix;
    private int price;

    VehicleCategory(String name, String prefix, int price){
        this.name = name;
        this.prefix = prefix;
        this.price = price;
    }

    public String getName(){
        return name;
    }
    public String getPrefix(){
        return prefix;
    }
    public int getPrice(){
        return price;
    }

}
