package com.mySampleApplication.client.ui.place.listcell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mySampleApplication.client.ui.place.form.PlaceForm;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

/**
 * Created by bogdan on 7/12/17.
 */
public class CwCellList extends CellList<PlacePOJO> {

    Widget rootElement;

//    @UiField
    PlaceForm placeForm;

    interface CwCellListUiBinder extends UiBinder<Widget, CwCellList> {
    }

    private static CwCellListUiBinder ourUiBinder = GWT.create(CwCellListUiBinder.class);

    public CwCellList(PlaceForm placeForm) {
        super(new PlaceCell());
        this.placeForm = placeForm;
//        getElement().getStyle().setWidth(200, com.google.gwt.dom.client.Style.Unit.PX);
//        getElement().getStyle().setHeight(200, com.google.gwt.dom.client.Style.Unit.PX);
//        getElement().getStyle().setPropertyPx("height", 200);

        final SingleSelectionModel<PlacePOJO> selectionModel = new SingleSelectionModel<PlacePOJO>(new ProvidesKey<PlacePOJO>() {
            @Override
            public Object getKey(PlacePOJO item) {
                return item == null ? 0 : item.getId();
            }
        });

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                GWT.log("Setting contact");
                placeForm.setContact(selectionModel.getSelectedObject());
            }
        });
        setSelectionModel(selectionModel);
    }

    @Override
    protected void initWidget(Widget widget) {
        rootElement = ourUiBinder.createAndBindUi(this);
        super.initWidget(rootElement);
    }
}