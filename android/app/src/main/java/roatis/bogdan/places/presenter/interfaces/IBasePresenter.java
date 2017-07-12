package roatis.bogdan.places.presenter.interfaces;

import roatis.bogdan.places.view.interfaces.IBaseView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public interface IBasePresenter<V extends IBaseView> {
    void setView(V view);
}
