package com.mySampleApplication.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.mySampleApplication.server.util.Constants;

/**
 * Created by bogdan on 7/7/17.
 */
public class SerlvetContext extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(createServletModule());
    }

    private ServletModule createServletModule() {
        return new ServletModule() {
            @Override
            protected void configureServlets() {
                // 1. Note the argument to the JpaPersistModule constructor must match the name of the persistence unit in persistence.xml.

                install(new JpaPersistModule(Constants.PU));

                // 1. Make Guice-Servlet aware of our filter.
                // Filters need to be defined in SINGLETON scope (otherwise guice-persist will barf)
                bind(PersistFilter.class).in(Scopes.SINGLETON);

//                bind(AbstractDao.class).to(PlaceDao.class);

                // 2. Make Guice-Servlet aware of our servlet.
                // Servlets need to be defined in SINGLETON scope (otherwise guice-persist will barf)
                bind(PostPlacesServlet.class).in(Scopes.SINGLETON);

                // 3. Make Guice-Servlet aware of the url mapping for our filter.
                filter("/*").through(PersistFilter.class);

                // 4. Make Guice-Servlet aware of the url mapping for our servlet.
                serve("/set_users").with(PostPlacesServlet.class);

                serve("/MySampleApplicationService").with(MySampleApplicationServiceImpl.class);

            }
        };
    }
}
