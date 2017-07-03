package roatis.bogdan.places.presenter.concrete;

import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import roatis.bogdan.places.R;
import roatis.bogdan.places.model.concrete.PlaceModelImpl;
import roatis.bogdan.places.model.data.Place;
import roatis.bogdan.places.model.interfaces.IPlaceModel;
import roatis.bogdan.places.presenter.interfaces.IPlacesPresenter;
import roatis.bogdan.places.view.custom.PlaceItem;
import roatis.bogdan.places.view.interfaces.IPlacesView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class PlacesPresenterImpl extends BaseAbstractPresenter<IPlacesView> implements IPlacesPresenter {

    private CitiesAdapter mCitiesAdapter;
    private IPlaceModel iPlaceModel;
    private ActionMode mActionMode;

    public PlacesPresenterImpl(IPlacesView iPlacesView) {
        setView(iPlacesView);

        iPlaceModel = new PlaceModelImpl();
        mCitiesAdapter = new CitiesAdapter(getMockedCities());
        presentedView.setAdapter(mCitiesAdapter);
    }

    private List<Place> getMockedCities() {
        List<Place> cities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cities.add(new Place("City " + i, "Locality", new LatLng(30, 30)));
        }
        return cities;
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
        mActionMode = actionMode;
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
//        mAccountsAdapter.clearSelections();
        mActionMode = null;
    }

    @Override
    public void onUndoDelete() {
        mCitiesAdapter.restoreDeletedItems();
    }

    private class CitiesAdapter extends RecyclerView.Adapter<PlaceItem> implements PlaceItem.OnPlaceClickListener, PlaceItem.OnPlaceLongClickListener {

        private static final int DELETE_NOTIFICATION_TIME = 5000;

        private List<Place> cities;
        private List<Place> selectedCities;
        private List<Place> pendingDeletedItems;
        private Handler deleteHandler;
        private SparseBooleanArray selectedItems;

        public CitiesAdapter() {
            this(new ArrayList<Place>());
        }

        private CitiesAdapter(List<Place> cities) {
            this.cities = cities == null ? new ArrayList<Place>() : cities;
            deleteHandler = new Handler();
            selectedItems = new SparseBooleanArray();
            selectedCities = new ArrayList<>();
            pendingDeletedItems = new ArrayList<>();
        }

        public void setCities(List<Place> cities) {
            this.cities = cities == null ? new ArrayList<Place>() : cities;
            selectedCities.clear();
            pendingDeletedItems.clear();
            notifyDataSetChanged();
        }

        @Override
        public PlaceItem onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlaceItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
        }

        @Override
        public void onBindViewHolder(PlaceItem holder, int position) {
            holder.setTitle(cities.get(position).getName());
            holder.setStatus(getSelectedItems().contains(position));
            holder.setOnPlaceClickListener(this);
            holder.setOnPlaceLongClickListener(this);
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }

        @Override
        public void onPlaceClick(View view, int position) {
            if (mActionMode != null) {
                toggleSelection(position);
            } else {
                Place place = cities.get(position);
            }
        }

        @Override
        public void onPlaceLongClick(View view, int position) {
            if (mActionMode == null) {
                presentedView.startActionMode(actionModeCallback);
                toggleSelection(position);
            }
        }

        /**
         * Indicates if the item at position position is selected
         *
         * @param position Position of the item to check
         * @return true if the item is selected, false otherwise
         */
        public boolean isSelected(int position) {
            return getSelectedItems().contains(position);
        }

        public void toggleSelection(int pos) {
            if (selectedItems.get(pos, false)) {
                selectedItems.delete(pos);
            } else {
                selectedItems.put(pos, true);
            }
            int count = getSelectedItemCount();

            if (mActionMode != null) {
                if (count == 0) {
                    mActionMode.finish();
                } else {
                    mActionMode.setTitle(String.valueOf(count));
                }
            }
            notifyItemChanged(pos);
        }

        public void clearSelections() {
            selectedItems.clear();
            notifyDataSetChanged();
        }

        public int getSelectedItemCount() {
            return selectedItems.size();
        }

        public List<Integer> getSelectedItems() {
            List<Integer> items = new ArrayList<>(selectedItems.size());
            for (int i = 0; i < selectedItems.size(); i++) {
                items.add(selectedItems.keyAt(i));
            }
            return items;
        }

        public List<Place> getSelectedCities() {
            for (int i = 0; i < selectedItems.size(); i++) {
                selectedCities.add(cities.get(selectedItems.keyAt(i)));
            }
            return selectedCities;
        }

        public void deleteAllSelected() {
            pendingDeletedItems.clear();
            for (Place place : cities) {
                pendingDeletedItems.add(place);
            }
            cities.removeAll(getSelectedCities());
            notifyDataSetChanged();
            deleteHandler.postDelayed(deleteRunnable, DELETE_NOTIFICATION_TIME);
        }

        public void restoreDeletedItems() {
            deleteHandler.removeCallbacks(deleteRunnable);
            cities.clear();
            for (Place place : pendingDeletedItems) {
                cities.add(place);
            }
            pendingDeletedItems.clear();
            notifyDataSetChanged();
        }

        private Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                //TODO use getSelectedCities to delete the cities
//                iCityModel.deleteCities(selectedCities);
                mCitiesAdapter.clearSelections();
            }
        };
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_menu_screen_cities, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    mCitiesAdapter.deleteAllSelected();
                    presentedView.showDeleteUndoAction();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
            mCitiesAdapter.clearSelections();
        }
    };
}
