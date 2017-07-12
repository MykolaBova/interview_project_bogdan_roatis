package com.mySampleApplication.server.dao;

import com.mySampleApplication.shared.model.db.Place;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by bogdan on 7/6/17.
 */
public class PlaceDao extends AbstractDao<Place> {

    @Inject
    public PlaceDao() {
        super(Place.class);
    }

    public Place getByName(String name) {
        String queryStr = "SELECT p FROM Place p where name=:name";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("name", name);
        List results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return (Place) results.get(0);
        }
    }

    public List<Place> getAll() {
        String queryStr = "SELECT p FROM Place p";
        return getEntityManager().createQuery(queryStr).getResultList();
    }
}
