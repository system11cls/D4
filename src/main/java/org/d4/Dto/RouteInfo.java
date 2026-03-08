package org.d4.Dto;


import java.util.List;

public class RouteInfo {
    public String id;
    public List<FlightInfo> flights;
    public double economyPrice = 0;
    public double comfortPrice = 0;
    public double businessPrice = 0;


    public RouteInfo(String id, List<FlightInfo> flights) {
        this.id = id;
        this.flights = flights;

        var economyCnt = 0;
        var businessCnt = 0;
        var comfortCnt = 0;

        for (var flight : flights) {
            if (flight.economyPrice != 0) {
                economyCnt++;
                this.economyPrice += flight.economyPrice;
            }
            if (flight.comfortPrice != 0) {
                comfortCnt++;
                this.comfortPrice += flight.comfortPrice;
            }
            if (flight.businessPrice != 0) {
                businessCnt++;
                this.businessPrice += flight.businessPrice;
            }
        }


        if (economyCnt != 0) this.economyPrice /= economyCnt;
        if (comfortCnt != 0) this.comfortPrice /= comfortCnt;
        if (businessCnt != 0) this.businessPrice /= businessCnt;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ route_id = ").append(id)
                .append("; ePrice = ").append(economyPrice != 0 ? economyPrice : "No")
                .append("; cPrice = ").append(comfortPrice != 0 ? comfortPrice : "No")
                .append("; bPrice = ").append(businessPrice != 0 ? businessPrice : "No")
                .append("\n");
        for (var info : flights) {
            builder.append("\t").append(info).append("\n");
        }
        builder.append("}\n");
        return builder.toString();
    }
}
