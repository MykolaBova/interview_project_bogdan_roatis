package com.mySampleApplication.server;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Injector;
import com.mySampleApplication.client.MySampleApplicationService;
import com.mySampleApplication.server.dao.PlaceDao;
import com.mySampleApplication.shared.model.pojos.GeometryPOJO;
import com.mySampleApplication.shared.model.pojos.LocationPOJO;
import com.mySampleApplication.shared.model.db.Place;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {

    private Injector injector;

    // Implementation of sample interface method

    @Inject
    public MySampleApplicationServiceImpl(Injector injector) {
        this.injector = injector;
    }

    @Override
    public String getResult() {
//        com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException: The response could not be deserialized
        PlaceDao placeDao = injector.getInstance(PlaceDao.class);
        List<PlacePOJO> places = new ArrayList<>();
        for (Place place : placeDao.getAll()) {
            PlacePOJO placePOJO = new PlacePOJO(place.getName());
            placePOJO.setGeometry(new GeometryPOJO(new LocationPOJO(Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()))));
            placePOJO.setVicinity(place.getVicinity());
            placePOJO.setIcon(place.getIcon());
            placePOJO.setId(place.getId());
            places.add(placePOJO);
        }

        return new Gson().toJson(places);
    }

}