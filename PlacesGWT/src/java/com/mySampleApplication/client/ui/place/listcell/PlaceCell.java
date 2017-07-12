package com.mySampleApplication.client.ui.place.listcell;

/**
 * Created by bogdan on 7/12/17.
 */

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

/**
 * The Cell used to render a {@link PlacePOJO}.
 */
public class PlaceCell extends AbstractCell<PlacePOJO> {

    public PlaceCell() {

    }

    @Override
    public void render(Context context, PlacePOJO value, SafeHtmlBuilder sb) {
        // Value can be null, so do a null check..
        if (value == null) {
            return;
        }

        sb.appendHtmlConstant("<table>");

        // Add the contact image.
        sb.appendHtmlConstant("<tr><td rowspan='3'>");
        sb.appendHtmlConstant("</td>");

        // Add the name and address.
        sb.appendHtmlConstant("<td style='font-size:95%;'>");
        sb.appendEscaped(value.getName());
        sb.appendHtmlConstant("</td></tr><tr><td>");
        sb.appendEscaped("Address: " + value.getVicinity());
        sb.appendHtmlConstant("</td></tr></table>");
    }
}
