package com.mySampleApplication.shared.model.pojos;

import java.io.Serializable;

/**
 * Created by bogdan on 7/10/17.
 */
public class LocationPOJO implements Serializable {

    private double lat;
    private double lng;

    public LocationPOJO() {
    }

    public LocationPOJO(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
