package roatis.bogdan.places.model.interfaces;

import android.location.Location;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public interface ILocationModel {

    Location getLastKnownLocation();

    interface OnLocationReceived {
        void onLocationReceived();
    }

}
