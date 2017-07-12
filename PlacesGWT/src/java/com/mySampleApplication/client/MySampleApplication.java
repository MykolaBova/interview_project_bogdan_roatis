package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mySampleApplication.client.mappers.PlaceMapper;
import com.mySampleApplication.client.ui.place.form.PlaceForm;
import com.mySampleApplication.client.ui.place.listcell.CwCellList;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    PlaceForm placeForm = new PlaceForm();
    CwCellList cwCellList = new CwCellList(placeForm);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("slot1").add(cwCellList);
        RootPanel.get("slot2").add(placeForm);
        MySampleApplicationService.App.getInstance().getResult(new MyAsyncCallback(cwCellList));
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private CellList<PlacePOJO> cellList;

        public MyAsyncCallback(CellList<PlacePOJO> cellList) {
            this.cellList = cellList;
        }

        public void onFailure(Throwable throwable) {
            GWT.log(throwable.getMessage());
        }

        @Override
        public void onSuccess(String result) {
            PlaceMapper mapper = GWT.create(PlaceMapper.class);

            cellList.setRowData(mapper.read(result));
        }

    }
}
