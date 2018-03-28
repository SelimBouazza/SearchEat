package sr.searcheat;



import android.app.Fragment;
import android.content.Context;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.widget.SwipeRefreshLayout;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by Sélim on 26/01/2018.
 */
public class RestaurantGeoFragment extends Fragment implements LocationListener {
    //Page des restaurants les plus proches

    private View fragmentView;
    private Context context;
    private ListView listView;

    private List<Restaurant> restaurants = new ArrayList<>();

    private Location currentLocation;
    private LocationManager locationManager;


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

        /*swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);*/
        prepareGeolocation();
        loadrestaurants();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Restaurant a proximité");
        super.onResume();
    }



    private void loadrestaurants()  { //Chargment des restaurants

        Realm realm = Realm.getInstance(context);
        int radius = Global.DEFAULT_RADIUS;
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);



        GeoTools geoTools = new GeoTools(currentLocation.getLatitude(), currentLocation.getLongitude(), radius);
        for (Restaurant restaurant : query.findAll()) {

                Log.i("caaaa",String.valueOf(restaurant.getLongitude()));
            if (geoTools.pointIsInRadius(restaurant.getLatitude(),restaurant.getLongitude())) {

                restaurants.add(restaurant);
            }
        }


        listView.setAdapter(new RestaurantsListAdapter(context, R.layout.restaurant_list_item, restaurants,currentLocation));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantFragment restaurantFragment = new RestaurantFragment();

                ((MainActivity) getActivity()).showRestaurantFragment(restaurantFragment, restaurants.get(position).getIdRestaurant());

            }
        });

      /*  if (swipeLayout.isRefreshing())
            swipeLayout.setRefreshing(false);*/
    }

    private void prepareGeolocation() {
        currentLocation = getLastKnownLocation();
    }

    private Location getLastKnownLocation() { //Fonctions qui retourne la position connu la plus récente
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
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


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
