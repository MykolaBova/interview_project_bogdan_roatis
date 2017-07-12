package com.mySampleApplication.shared.model.pojos;

import java.io.Serializable;

/**
 * Created by bogdan on 7/10/17.
 * <p>
 * Class used for parsing the body in the POST call
 */
public class PlacePOJO implements Serializable {

    private int id;
    private String name;
    private String icon;
    private String vicinity;

    private GeometryPOJO geometry;

    public PlacePOJO() {
    }

    public PlacePOJO(String name) {
        this.name = name;
    }

    public PlacePOJO(String name, GeometryPOJO geometry) {
        this.name = name;
        this.geometry = geometry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGeometry(GeometryPOJO geometry) {
        this.geometry = geometry;
    }

    public GeometryPOJO getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
