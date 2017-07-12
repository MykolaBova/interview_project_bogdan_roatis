package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface MySampleApplicationService extends RemoteService {
    // Sample interface method of remote interface
//    List<PlacePOJO> getPlaces(String msg);

    String getResult();

    /**
     * Utility/Convenience class.
     * Use MySampleApplicationService.App.getInstance() to access static instance of MySampleApplicationServiceAsync
     */
    public static class App {

        private static final String serviceEndpoint = getAppBaseUrl() + "/MySampleApplicationService";

        private static MySampleApplicationServiceAsync ourInstance = GWT.create(MySampleApplicationService.class);

        public static synchronized MySampleApplicationServiceAsync getInstance() {
            GWT.log("Getting instance with " + serviceEndpoint);
            ((ServiceDefTarget) ourInstance).setServiceEntryPoint(serviceEndpoint);
            return ourInstance;
        }

        public static String getAppBaseUrl() {
            String baseUrl = GWT.getModuleBaseURL();

            baseUrl = baseUrl.substring(0, baseUrl.length() - 2);
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));

            return baseUrl;
        }
    }
}
