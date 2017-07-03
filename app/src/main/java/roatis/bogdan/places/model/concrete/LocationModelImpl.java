package roatis.bogdan.places.model.concrete;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import roatis.bogdan.places.model.interfaces.ILocationModel;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class LocationModelImpl implements ILocationModel {

    private static LocationModelImpl instance;

    private Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private ILocationModel.OnLocationReceived mOnLocationReceived;

    public static LocationModelImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (LocationModelImpl.class) {
                if (instance == null) {
                    instance = new LocationModelImpl(context);
                }
            }
        }
        return instance;
    }

    private LocationModelImpl(Context context) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestNecessaryPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestNecessaryPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLocation = location;
                            if (mOnLocationReceived != null) {
                                mOnLocationReceived.onLocationReceived();
                            }
                        }
                    }
                });
    }

    public void setOnLocationReceived(OnLocationReceived mOnLocationReceived) {
        this.mOnLocationReceived = mOnLocationReceived;
    }

    @Override
    public Location getLastKnownLocation() {
        return mLocation;
    }

}
