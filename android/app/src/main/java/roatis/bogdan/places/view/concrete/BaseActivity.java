package roatis.bogdan.places.view.concrete;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import roatis.bogdan.places.view.interfaces.IBaseView;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {

    @Override
    public Context getViewContext() {
        return this;
    }
}
