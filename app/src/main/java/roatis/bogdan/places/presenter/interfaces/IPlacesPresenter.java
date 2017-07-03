package roatis.bogdan.places.presenter.interfaces;

import android.support.v7.view.ActionMode;

import roatis.bogdan.places.view.interfaces.IPlacesView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public interface IPlacesPresenter extends IBasePresenter<IPlacesView> {

    void onActionModeStarted(ActionMode actionMode);

    void onActionModeFinished(ActionMode actionMode);

    void onUndoDelete();
}
