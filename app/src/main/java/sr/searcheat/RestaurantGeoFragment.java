package sr.searcheat;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

/**
 * Created by Sélim on 26/01/2018.
 */
public class RestaurantGeoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LocationListener {

    private View fragmentView;
    private Context context;
    private ListView listView;

    private List<Restaurant> restaurants = new ArrayList<>();

    private Location currentLocation;
    private LocationManager locationManager;

    private SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        fragmentView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        listView = (ListView) fragmentView.findViewById(R.id.listResto);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ?
                        0 : listView.getChildAt(0).getTop();
                swipeLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }

        });


        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        new LoaderRestosGeo().execute();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Restaurant a proximité");
        ((MainActivity) getActivity()).setVisibilityMenu(true, false, false);

        new LoaderRestosGeo().execute();

        super.onResume();
    }

    @Override
    public void onRefresh() {
        new LoaderRestosGeo().execute();
    }

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

    private void loadrestaurants()  {

        restaurants = new ArrayList<>();

        int radius = Global.DEFAULT_RADIUS;

        Restaurant r = new Restaurant(1, "McDonald", "14 rue Edgar Faure 21000 Dijon");
        Restaurant r2 = new Restaurant(1, "Flunch", "10 rue de la Liberté 21000 Dijon");
        r.setLatitude(48.862725);
        r.setLongitude(2.287592);
        r2.setLongitude(47.32317439999999);
        r2.setLatitude(5.035278999999946);

        GeoTools geoTools = new GeoTools(currentLocation.getLatitude(), currentLocation.getLongitude(), radius);

        if (geoTools.isOnRadius(r2.getLatitude(),r2.getLongitude())) {
            restaurants.add(r2);
        }
        restaurants.add(r);
        listView.setAdapter(new RestaurantsListAdapter(context, R.layout.restaurant_list_item, restaurants,currentLocation));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantFragment restaurantFragment = new RestaurantFragment();

                ((MainActivity) getActivity()).showRestaurantFragment(restaurantFragment, restaurants.get(position).getIdRestaurant());

            }
        });

        if (swipeLayout.isRefreshing())
            swipeLayout.setRefreshing(false);
    }

    private void prepareGeolocation() {
        currentLocation = getLastKnownLocation();
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);

            }
            Log.i("coucou","édshj");
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;

    }

    private class LoaderRestosGeo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (!swipeLayout.isRefreshing())
                swipeLayout.setRefreshing(true);

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {


            while (currentLocation == null) {

                prepareGeolocation();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            loadrestaurants();

            super.onPostExecute(aVoid);
        }
    }
}
