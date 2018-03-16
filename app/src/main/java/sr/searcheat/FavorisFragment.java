package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

/**
 * Created by Sélim on 21/01/2018.
 */
public class FavorisFragment extends Fragment {
    private View fragmentView;
    private Context context;
    private ListView listView;
    private List<Restaurant> restaurants;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        fragmentView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        listView = (ListView) fragmentView.findViewById(R.id.listResto);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantFragment restaurantFragment = new RestaurantFragment();
                ((MainActivity) getActivity()).showRestaurantFragment(restaurantFragment, restaurants.get(position).getIdRestaurant());
            }
        });


        loadFavoritesRestaurant();
        return fragmentView;
    }


    public void loadFavoritesRestaurant() {



        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_FAV, Context.MODE_PRIVATE);
        Set<String> restoIds = sharedPref.getStringSet(Global.SP_FAV, new HashSet<String>());

        restaurants = new ArrayList<>();

        Set<String> availaibleResto = new HashSet<>();

        Realm realm = Realm.getInstance(context);

        for (String restoId : restoIds) {
            Restaurant restaurant = realm.where(Restaurant.class).equalTo("idRestaurant", Long.valueOf(restoId)).findFirst();
            if(restaurant != null) {
                restaurants.add(restaurant);
                availaibleResto.add(restoId);
            }
        }

        sharedPref.edit().putStringSet(Global.SP_FAV, availaibleResto).commit();

        listView.setAdapter(new RestaurantsListAdapter(context, R.layout.restaurant_list_item, restaurants,false));
        listView.invalidateViews();
        listView.invalidate();


    }
}
