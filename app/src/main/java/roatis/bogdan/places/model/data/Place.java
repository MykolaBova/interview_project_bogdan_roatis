package roatis.bogdan.places.model.data;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class Place {

    private String name;
    private String icon;
    private Geometry geometry;

    public Place(String name, String icon, Geometry geometry) {
        this.name = name;
        this.icon = icon;
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
