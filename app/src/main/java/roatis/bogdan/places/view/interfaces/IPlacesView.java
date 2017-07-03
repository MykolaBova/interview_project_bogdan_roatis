package roatis.bogdan.places.view.interfaces;

import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public interface IPlacesView extends IBaseView {

    void setAdapter(RecyclerView.Adapter mAdapter);

    void showDeleteUndoAction();

    void startActionMode(ActionMode.Callback actionModeCallback);

}
