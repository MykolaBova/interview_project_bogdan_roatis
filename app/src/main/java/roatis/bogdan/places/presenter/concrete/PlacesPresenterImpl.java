package roatis.bogdan.places.presenter.concrete;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import roatis.bogdan.places.R;
import roatis.bogdan.places.model.concrete.LocationModelImpl;
import roatis.bogdan.places.model.concrete.PlaceModelImpl;
import roatis.bogdan.places.model.data.Place;
import roatis.bogdan.places.model.interfaces.ILocationModel;
import roatis.bogdan.places.model.interfaces.IPlaceModel;
import roatis.bogdan.places.presenter.interfaces.IPlacesPresenter;
import roatis.bogdan.places.view.custom.PlaceItem;
import roatis.bogdan.places.view.interfaces.IPlacesView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class PlacesPresenterImpl extends BaseAbstractPresenter<IPlacesView> implements IPlacesPresenter, ILocationModel.OnLocationReceived, IPlaceModel.OnPlacesCallback {

    private CitiesAdapter mPlacesAdapter;
    private IPlaceModel mPlaceModel;
    private ILocationModel mLocationModel;
    private ActionMode mActionMode;

    public PlacesPresenterImpl(IPlacesView iPlacesView) {
        setView(iPlacesView);

        mPlaceModel = PlaceModelImpl.getInstance();
        if (ActivityCompat.checkSelfPermission(presentedView.getViewContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(presentedView.getViewContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            presentedView.requestNecessaryPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            mLocationModel = LocationModelImpl.getInstance(presentedView.getViewContext());
            ((LocationModelImpl) mLocationModel).setOnLocationReceived(this);
        }

        mPlacesAdapter = new CitiesAdapter();
        presentedView.setAdapter(mPlacesAdapter);
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
        mPlacesAdapter.restoreDeletedItems();
    }

    @Override
    public void onPermissionsResult(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationModel = LocationModelImpl.getInstance(presentedView.getViewContext());
            ((LocationModelImpl) mLocationModel).setOnLocationReceived(this);
        }
    }

    @Override
    public void onLocationReceived() {
        try {
            mPlaceModel.getPlaces(mLocationModel.getLastKnownLocation(), IPlaceModel.Type.RESTAURANTS, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlacesReceived(final List<Place> places) {
        ((Activity) presentedView.getViewContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlacesAdapter.setPlaces(places);
            }
        });
    }

    private class CitiesAdapter extends RecyclerView.Adapter<PlaceItem> implements PlaceItem.OnPlaceClickListener, PlaceItem.OnPlaceLongClickListener {

        private static final int DELETE_NOTIFICATION_TIME = 5000;

        private List<Place> places;
        private List<Place> selectedPlaces;
        private List<Place> pendingDeletedItems;
        private Handler deleteHandler;
        private SparseBooleanArray selectedItems;

        public CitiesAdapter() {
            this(new ArrayList<Place>());
        }

        private CitiesAdapter(List<Place> places) {
            this.places = places == null ? new ArrayList<Place>() : places;
            deleteHandler = new Handler();
            selectedItems = new SparseBooleanArray();
            selectedPlaces = new ArrayList<>();
            pendingDeletedItems = new ArrayList<>();
        }

        public void setPlaces(List<Place> cities) {
            this.places = cities == null ? new ArrayList<Place>() : cities;
            selectedPlaces.clear();
            pendingDeletedItems.clear();
            notifyDataSetChanged();
        }

        @Override
        public PlaceItem onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlaceItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
        }

        @Override
        public void onBindViewHolder(PlaceItem holder, int position) {
            Place place = places.get(position);
            holder.setTitle(place.getName());
            holder.setIcon(place.getIcon());
            holder.setStatus(getSelectedItems().contains(position));
            holder.setOnPlaceClickListener(this);
            holder.setOnPlaceLongClickListener(this);
        }

        @Override
        public int getItemCount() {
            return places.size();
        }

        @Override
        public void onPlaceClick(View view, int position) {
            if (mActionMode != null) {
                toggleSelection(position);
            } else {
                Place place = places.get(position);
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

        public List<Place> getSelectedPlaces() {
            for (int i = 0; i < selectedItems.size(); i++) {
                selectedPlaces.add(places.get(selectedItems.keyAt(i)));
            }
            return selectedPlaces;
        }

        public void deleteAllSelected() {
            pendingDeletedItems.clear();
            for (Place place : places) {
                pendingDeletedItems.add(place);
            }
            places.removeAll(getSelectedPlaces());
            notifyDataSetChanged();
            deleteHandler.postDelayed(deleteRunnable, DELETE_NOTIFICATION_TIME);
        }

        public void restoreDeletedItems() {
            deleteHandler.removeCallbacks(deleteRunnable);
            places.clear();
            for (Place place : pendingDeletedItems) {
                places.add(place);
            }
            pendingDeletedItems.clear();
            notifyDataSetChanged();
        }

        private Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                //TODO use getSelectedPlaces to delete the places
//                iCityModel.deleteCities(selectedPlaces);
                mPlacesAdapter.clearSelections();
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
                    mPlacesAdapter.deleteAllSelected();
                    presentedView.showDeleteUndoAction();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
            mPlacesAdapter.clearSelections();
        }
    };
}
