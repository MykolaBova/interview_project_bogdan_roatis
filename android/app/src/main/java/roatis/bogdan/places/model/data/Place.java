package roatis.bogdan.places.model.data;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class Place {

    private String name;
    private String icon;
    private String vicinity;
    private Geometry geometry;

    public Place() {
    }

    public Place(String name, String icon, String vicinity, Geometry geometry) {
        this.name = name;
        this.icon = icon;
        this.vicinity = vicinity;
        this.geometry = geometry;
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

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
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

    public Geometry getGeometry() {
        return geometry;
    }
}
