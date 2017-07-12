package com.mySampleApplication;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.mySampleApplication.server.dao.PlaceDao;
import com.mySampleApplication.shared.model.db.Place;

import java.sql.SQLException;

/**
 * Created by bogdan on 7/6/17.
 */
public class PlaceTest {

    private static final String TEST_PU_NAME = "examplePU";

//    @Test
    public void testDb() throws SQLException {
        // inject stuff
        Injector injector = Guice.createInjector(new JpaPersistModule(TEST_PU_NAME));
//        injector.getInstance(JpaInitializer.class);
        PlaceDao personDao = injector.getInstance(PlaceDao.class);

        // persist people
        Place p1 = new Place();
        p1.setName("Place 1");
        personDao.create(p1);

        Place p2 = new Place();
        p2.setName("Place 2");
        personDao.create(p2);

        Place p3 = new Place();
        p3.setName("Place 3");
        personDao.create(p3);

        // retrieve one person from the persisted places
        Place retrieved = personDao.getByName("");

        // validate results
//        Assert.assertEquals(p2.getId(), retrieved.getId());
//        Assert.assertEquals(p2.getName(), retrieved.getName());
    }

}
