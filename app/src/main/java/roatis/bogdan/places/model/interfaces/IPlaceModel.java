package roatis.bogdan.places.model.interfaces;

import android.location.Location;

import java.io.IOException;
import java.util.List;

import roatis.bogdan.places.model.data.Place;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public interface IPlaceModel {

    void getPlaces(Location location, OnPlacesCallback onPlacesCallback) throws IOException;

    void getPlaces(Location location, Type type, OnPlacesCallback onPlacesCallback) throws IOException;

    void getPlaces(Location location, int radiusInMeters, OnPlacesCallback onPlacesCallback) throws IOException;

    void getPlaces(Location location, int radiusInMeters, Type type, OnPlacesCallback onPlacesCallback) throws IOException;

    interface OnPlacesCallback {
        void onPlacesReceived(List<Place> places);
    }

    enum Type {
        ACCOUNTING,
        AIRPORT,
        AMUSEMENT_PARK,
        AQUARIUM,
        ART_GALLERY,
        ATM,
        BAKERY,
        BANK,
        BAR,
        BEAUTY_SALON,
        BICYCLE_STORE,
        BOOK_STORE,
        BOWLING_ALLEY,
        BUS_STATION,
        CAFE,
        CAMPGROUND,
        CAR_DEALER,
        CAR_RENTAL,
        CAR_REPAIR,
        CAR_WASH,
        CASINO,
        CEMETERY,
        CHURCH,
        CITY_HALL,
        CLOTHING_STORE,
        CONVENIENCE_STORE,
        COURTHOUSE,
        DENTIST,
        DEPARTMENT_STORE,
        DOCTOR,
        ELECTRICIAN,
        ELECTRONICS_STORE,
        EMBASSY,
        FIRE_STATION,
        FLORIST,
        FUNERAL_HOME,
        FURNITURE_STORE,
        GAS_STATION,
        GYM,
        HAIR_CARE,
        HARDWARE_STORE,
        HINDU_TEMPLE,
        HOME_GOODS_STORE,
        RESTAURANTS
    }
}
