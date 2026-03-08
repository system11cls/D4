package org.d4.Dto;

public class FlightInfo {
    public String id;
    public double economyPrice;
    public double comfortPrice;
    public double businessPrice;

    public FlightInfo(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{ flight_id = " + id + "; ePrice = " + (economyPrice != 0 ? economyPrice : "No") + "; cPrice = " +
                (comfortPrice != 0 ? comfortPrice : "No") + "; bPrice = " +
                (businessPrice != 0 ? businessPrice : "No") + "}";
    }
}
