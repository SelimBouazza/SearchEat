package sr.searcheat;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Sélim on 26/01/2018.
 */
public class RestaurantGeoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LocationListener {
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRefresh() {

    }
}
