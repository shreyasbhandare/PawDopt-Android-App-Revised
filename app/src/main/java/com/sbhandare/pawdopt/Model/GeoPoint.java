package com.sbhandare.pawdopt.Model;

import java.math.BigDecimal;

public class GeoPoint{
    private double lattitude;
    private double longitude;

    public GeoPoint(double lat, double lon){
        this.lattitude = lat;
        this.longitude = lon;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
