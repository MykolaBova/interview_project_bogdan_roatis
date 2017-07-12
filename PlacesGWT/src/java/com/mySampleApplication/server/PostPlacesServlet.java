package com.mySampleApplication.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mySampleApplication.server.dao.PlaceDao;
import com.mySampleApplication.server.util.RequestUtil;
import com.mySampleApplication.shared.model.db.Place;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by bogdan on 7/6/17.
 */
@Singleton
public class PostPlacesServlet extends HttpServlet {

    private Injector injector;

    @Inject
    public PostPlacesServlet(Injector injector) {
        this.injector = injector;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PlacePOJO> placePOJOS = new Gson().fromJson(RequestUtil.getBody(req), new TypeToken<List<PlacePOJO>>() {
        }.getType());
        PlaceDao placeDao = injector.getInstance(PlaceDao.class);
        for (PlacePOJO placePOJO : placePOJOS) {
            Place place = new Place();
            place.setLng(String.valueOf(placePOJO.getGeometry().getLocation().getLng()));
            place.setLat(String.valueOf(placePOJO.getGeometry().getLocation().getLat()));
            place.setName(placePOJO.getName());
            place.setVicinity(placePOJO.getVicinity());
            place.setIcon(placePOJO.getIcon());
            placeDao.create(place);
        }
    }

}
