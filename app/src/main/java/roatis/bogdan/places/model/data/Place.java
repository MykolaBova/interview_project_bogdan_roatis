package roatis.bogdan.places.model.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class Place {

    private String name;
    private String type;
    private LatLng coordinates;

    public Place(String name, String type, LatLng coordinates) {
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }
}
