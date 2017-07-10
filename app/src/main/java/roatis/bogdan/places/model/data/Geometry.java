package roatis.bogdan.places.model.data;

/**
 * Created by Bogdan Roatis on 7/10/17.
 */

public class Geometry {

    private Location location;

    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
