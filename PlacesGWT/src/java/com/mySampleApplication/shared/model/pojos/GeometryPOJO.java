package com.mySampleApplication.shared.model.pojos;

import java.io.Serializable;

/**
 * Created by bogdan on 7/10/17.
 */
public class GeometryPOJO implements Serializable {

    private LocationPOJO location;

    public GeometryPOJO() {
    }

    public GeometryPOJO(LocationPOJO location) {
        this.location = location;
    }

    public LocationPOJO getLocation() {
        return location;
    }

    public void setLocation(LocationPOJO location) {
        this.location = location;
    }
}
