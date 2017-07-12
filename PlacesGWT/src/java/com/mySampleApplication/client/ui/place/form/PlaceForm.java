package com.mySampleApplication.client.ui.place.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

/**
 * Created by bogdan on 7/12/17.
 */
public class PlaceForm extends Composite {

    private PlacePOJO place;

    @UiField
    TextBox locationName;

    @UiField
    TextBox locationAddress;

    @UiField
    TextBox locationLatitude;

    @UiField
    TextBox locationLongitude;

    @UiField
    TextBox locationIcon;

    interface PlaceFormUiBinder extends UiBinder<HTMLPanel, PlaceForm> {
    }

    private static PlaceFormUiBinder ourUiBinder = GWT.create(PlaceFormUiBinder.class);

    public PlaceForm() {
        initWidget(ourUiBinder.createAndBindUi(this));
        locationName.setReadOnly(true);
    }

    public void setContact(PlacePOJO place) {
        this.place = place;
        if (place != null) {
            locationName.setText(place.getName());
            locationAddress.setText(place.getVicinity());
            locationIcon.setText(place.getIcon());
            locationLatitude.setText(String.valueOf(place.getGeometry().getLocation().getLat()));
            locationLongitude.setText(String.valueOf(place.getGeometry().getLocation().getLng()));
        }
    }
}