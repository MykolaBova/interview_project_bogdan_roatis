package com.mySampleApplication.shared.model.db;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bogdan on 7/5/17.
 */
@Entity
@Table(name = "places", schema = "public")
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "vicinity")
    private String vicinity;

    public Place() {
    }

    public Place(int id, String lat, String lng, String name, String icon, String address) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.icon = icon;
        this.vicinity = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public int getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getVicinity() {
        return vicinity;
    }
}
