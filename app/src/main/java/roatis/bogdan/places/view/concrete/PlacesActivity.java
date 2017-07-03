package roatis.bogdan.places.view.concrete;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import roatis.bogdan.places.R;
import roatis.bogdan.places.presenter.concrete.PlacesPresenterImpl;
import roatis.bogdan.places.presenter.interfaces.IPlacesPresenter;
import roatis.bogdan.places.view.interfaces.IPlacesView;

public class PlacesActivity extends BaseActivity implements IPlacesView {

    private RecyclerView mRvPlaces;
    private Snackbar snackbar;
    private IPlacesPresenter mPlacesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        mRvPlaces = (RecyclerView) findViewById(R.id.rv_places);
        mRvPlaces.setLayoutManager(new LinearLayoutManager(this));

        snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.item_delete, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPlacesPresenter.onUndoDelete();
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));


        mPlacesPresenter = new PlacesPresenterImpl(this);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter mAdapter) {
        mRvPlaces.setAdapter(mAdapter);
    }

    @Override
    public void showDeleteUndoAction() {
        snackbar.show();
    }

    @Override
    public void startActionMode(ActionMode.Callback actionModeCallback) {
        startSupportActionMode(actionModeCallback);
    }

    @Override
    public void requestNecessaryPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 27);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPlacesPresenter.onPermissionsResult(grantResults);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        mPlacesPresenter.onActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        mPlacesPresenter.onActionModeFinished(mode);
    }
}
