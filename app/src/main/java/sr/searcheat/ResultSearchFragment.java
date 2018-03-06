package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;


/**
 * Created by Sélim on 04/02/2018.
 */
public class ResultSearchFragment extends Fragment {

    private GeoTools.Position position;
    private View fragmentView;
    private ListView listView;
    private List<Restaurant> restaurants = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;
    private Context context;
    private List<Restaurant> restoList;
    private String searchRequest;
    private ArrayList<Restaurant> resultRestaurant = new ArrayList<>();
    private int idRestaurantMax = 0 ;
    private int nbRestaurant = 0;
    private boolean allRestaurantDisplay =false;
    private boolean showDistance = false;
    private boolean locationFound = false;
    private Set<String> searchPlats;
    private ArrayList<String> searchIngredient;
    private Location currentLocation;
    private LocationManager locationManager;


    int radius = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        radius = context.getSharedPreferences(Global.SP_GROUP_USER, Context.MODE_PRIVATE).getInt(Global.SP_USER_RADIUS, Global.DEFAULT_RADIUS);

        fragmentView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        listView = (ListView) fragmentView.findViewById(R.id.listResto);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setEnabled(false);

        prepareGeolocation();

            searchResto(searchRequest, searchIngredient);





        return fragmentView;
    }

    public void searchResto(String searchRequest,ArrayList<String> searchIngredient)
    {
        final Realm realm = Realm.getInstance(context);
        RealmQuery query = realm.where(Restaurant.class);
        restoList = query.findAll();
        RealmList<Plat> listPlats;

        for(Restaurant restaurant : restoList)
        {
            listPlats=restaurant.getPlats();
            if(!searchRequest.equals(""))
            {
                if(restaurant.getNomRestaurant().contains(searchRequest))
                {

                    resultRestaurant.add(restaurant);
                }

                else if(restaurant.getAdrRestaurant().contains(searchRequest))
                {

                    resultRestaurant.add(restaurant);
                }
            }
           if(!searchIngredient.isEmpty())
           {

               for(int j=0;j<listPlats.size();j++)
               {

                       for(int z=0;z<listPlats.get(j).getIngredients().size();z++)
                       {
                           for(int i =0; i<searchIngredient.size();i++)
                           {

                           if(listPlats.get(j).getIngredients().get(z).getNom().contains(searchIngredient.get(i)))
                           {

                               resultRestaurant.add(restaurant);
                           }
                       }

                   }
               }

           }


        }

        listView.setAdapter(new RestaurantsListAdapter(context, R.layout.restaurant_list_item, resultRestaurant, currentLocation));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantFragment restaurantFragment = new RestaurantFragment();

                ((MainActivity) getActivity()).showRestaurantFragment(restaurantFragment, resultRestaurant.get(position).getIdRestaurant());

            }
        });

    }
    private void prepareGeolocation() {
        currentLocation = getLastKnownLocation();
    }

    private Location getLastKnownLocation() {
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
    public void onResume() {
        getActivity().setTitle("Resultats de la recherche");
        ((MainActivity) getActivity()).setVisibilityMenu(false, false, false);

        if (restoList.isEmpty()) {
            Toast.makeText(context, "Aucun restaurant trouvée", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }









    public void setSearchRequest(String s)
    {
        searchRequest=s;
    }

    public void setSearchIngredient(ArrayList<String> s)
    {
        searchIngredient=s;
    }


}