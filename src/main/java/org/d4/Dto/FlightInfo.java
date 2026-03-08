package org.d4;

import java.util.HashSet;
import java.util.Set;

public class FlightInfo {
    public String id;
    public Set<Float> ecnnopyPrices = new HashSet<>();
    public Set<Float> comfortPrices = new HashSet<>();
    public Set<Float> BuissnessPrices = new HashSet<>();

    FlightInfo(String id) {
        this.id = id;
    }
}
