package roatis.bogdan.places.presenter.concrete;

import roatis.bogdan.places.view.interfaces.IBaseView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public abstract class BaseAbstractPresenter<V extends IBaseView> {

    protected V presentedView;

    public V getView() {
        return presentedView;
    }

    public void setView(V presentedView) {
        this.presentedView = presentedView;
    }

    protected int getResourceInt(int intResourceId) {
        return presentedView.getViewContext().getResources().getInteger(intResourceId);
    }

    protected String getResourceString(int stringResourceId) {
        return presentedView.getViewContext().getString(stringResourceId);
    }

    protected String getResourceString(int stringResourceId, Object... formatArgs) {
        return presentedView.getViewContext().getString(stringResourceId, formatArgs);
    }
}
