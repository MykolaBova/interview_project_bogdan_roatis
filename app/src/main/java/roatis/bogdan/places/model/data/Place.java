package roatis.bogdan.places.model.data;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class Place {

    private String name;
    private String icon;

    public Place(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
