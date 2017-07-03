package roatis.bogdan.places.model.concrete;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import roatis.bogdan.places.model.data.Place;
import roatis.bogdan.places.model.interfaces.IPlaceModel;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class PlaceModelImpl implements IPlaceModel {

    //    "https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters"
    private static final String URL_BASE = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private static final String URL_OUTPUT_TYPE = "json?";

    // parameters
    private static final String URL_LOCATION = "location=";
    private static final String URL_RADIUS = "radius=";
    private static final String URL_TYPE = "type=";
    private static final String URL_KEYWORD = "keyword=";
    private static final String URL_KEY = "key=";
    private static final String AND = "&";

    private static final int DEFAULT_RADIUS = 500;
    private static final Type DEFAULT_TYPE = Type.RESTAURANTS;
    private static final String DEFAULT_KEY = "AIzaSyCw2XNwulXOdZgiSbvxALBvDkcSlbSiFgo";

    private static PlaceModelImpl instance;

    private OkHttpClient client;
    private Gson gson;

    private PlaceModelImpl() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public static PlaceModelImpl getInstance() {
        if (instance == null) {
            synchronized (PlaceModelImpl.class) {
                if (instance == null) {
                    instance = new PlaceModelImpl();
                }
            }
        }
        return instance;
    }

    private void run(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }

    @Override
    public void getPlaces(Location location, OnPlacesCallback onPlacesCallback) throws IOException {
        getPlaces(location, DEFAULT_RADIUS, onPlacesCallback);
    }

    @Override
    public void getPlaces(Location location, Type type, OnPlacesCallback onPlacesCallback) throws IOException {
        getPlaces(location, DEFAULT_RADIUS, type, onPlacesCallback);
    }

    @Override
    public void getPlaces(Location location, int radiusInMeters, OnPlacesCallback onPlacesCallback) throws IOException {
        getPlaces(location, radiusInMeters, DEFAULT_TYPE, onPlacesCallback);
    }

    @Override
    public void getPlaces(Location location, int radiusInMeters, Type type, final OnPlacesCallback onPlacesCallback) throws IOException {
        if (location == null) {
            throw new Error("The coordinates are null");
        }

        if (radiusInMeters <= 0) {
            throw new Error("The radius is lower or equals to 0");
        }

        run(getUrl(location, radiusInMeters, type), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (onPlacesCallback != null) {
                    try {
//                        String res = response.body().toString();
//                        JSONObject jsonObject = new JSONObject(res.substring(1, res.length() - 1));
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        onPlacesCallback.onPlacesReceived((List<Place>) gson.fromJson(jsonObject.getJSONArray("results").toString(), new TypeToken<List<Place>>() {}.getType()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getUrl(Location location, int radiusInMeters, Type type) {
        StringBuilder builder = new StringBuilder();
        builder.append(URL_BASE)
                .append(URL_OUTPUT_TYPE)
                .append(URL_LOCATION)
                .append(location.getLatitude())
                .append(", ")
                .append(location.getLongitude())
                .append(AND)
                .append(URL_RADIUS)
                .append(radiusInMeters)
                .append(AND)
                .append(URL_TYPE)
                .append(type.name().toLowerCase())
                .append(AND)
                .append(URL_KEY)
                .append(DEFAULT_KEY);
        return builder.toString();
    }

}
