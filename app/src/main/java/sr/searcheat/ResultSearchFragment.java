package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private ArrayList<Restaurant> mapList;
    private int idRestaurantMax = 0 ;
    private int nbRestaurant = 0;
    private boolean allRestaurantDisplay =false;
    private boolean showDistance = false;
    private boolean locationFound = false;
    private Set<String> searchPlats;


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

        if (searchRequest != null) {

            searchResto(searchRequest);


        }


        return fragmentView;
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


    private void searchResto(final String request) {



        final Realm realm = Realm.getInstance(context);
        RealmQuery query = realm.where(Restaurant.class);
        restoList = query.findAll();

        new AsyncTask<Void, Void, GeoTools>() {

            protected GeoTools doInBackground(Void... params) {
                try {
                    position = GeoTools.getLocationPosition(context, request);
                    return new GeoTools(position.latitude, position.longitude, 15);
                }catch (Exception e){ }

                return null;
            }

            protected void onPostExecute(GeoTools mapPosition) {
                if (mapPosition != null) {

                    mapList = new ArrayList<>();

                    loop:  for (int i = idRestaurantMax; i < restoList.size(); i++) {

                        if (mapPosition.pointIsInRadius(restoList.get(i).getLatitude(), restoList.get(i).getLongitude())&&!restaurants.contains(restoList.get(i))) {

                            mapList.add(restoList.get(i));
                            nbRestaurant++;
                        }


                        if((nbRestaurant!=0) && (nbRestaurant%20==0))
                        {
                            nbRestaurant++;
                            idRestaurantMax = i;
                            break loop;

                        }else if(i==restoList.size()-1)
                        {

                            allRestaurantDisplay =true;
                        }
                    }


                    searchResult();
                }

                if(!mapList.isEmpty()){
                    locationFound = true;
                }
            }
        }.execute();
    }

    public void showSearchResult(final Set<String> plats) {

        if (mapList.size() != 0) {

            for (Restaurant restaurant : mapList) {



                RealmList<Plat> platRestaurant = restaurant.getPlats();

                if (platRestaurant.size() != 0) {
                    int count = 0;
                    for (Plat plat : platRestaurant) {
                        if (searchPlats.contains(plat.getNomPlat())) {
                            count++;

                            if (restaurants.contains(restaurant)) {

                            } else if (count == searchPlats.size()) {

                                restaurants.add(restaurant);
                            }

                        }
                    }
                } else {
                    restaurants.add(restaurant);
                }
            }
        }


        if (restaurants.size() != 0) {

            final RestaurantsListAdapter adapter = new RestaurantsListAdapter(context, R.layout.restaurant_list_item, restaurants);
            if(position != null){
                adapter.setCurrentPosition(position);
            }

            adapter.setShowDistance(showDistance);



            listView.setAdapter(adapter);

            listView.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {

                    adapter.setCurrentPosition(position);

                    if (allRestaurantDisplay) {

                    } else if (locationFound) {
                        searchResto(searchRequest);

                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RestaurantFragment restaurantFragment = new RestaurantFragment();
                    ((MainActivity) getActivity()).showRestaurantFragment(restaurantFragment, ResultSearchFragment.this.restaurants.get(position).getIdRestaurant());
                }
            });

        } else {

            Toast.makeText(context,"Aucun restaurant trouvée", Toast.LENGTH_SHORT).show();
        }


    }


    public void searchResult() {

        ArrayList<String[]> referenceList = new ArrayList<>();

        if (mapList.size() != 0) {
            String[] requestWords = searchRequest.split(" ");
            int numberOfWords = requestWords.length;

            if (numberOfWords == 1) {
                showDistance = true;

                showSearchResult(searchPlats);

            } else if (numberOfWords == 2) {

                for (int i = 0; i < mapList.size(); i++) {
                    Restaurant restaurant = mapList.get(i);
                    String[] itemReferenceList = new String[]{String.valueOf(restaurant.getIdRestaurant()), restaurant.getCity()};
                    referenceList.add(itemReferenceList);

                }

                if (getItemIdInListFromStrings(searchRequest, referenceList) != "-1") {
                    showDistance = true;

                    showSearchResult(searchPlats);

                } else {
                    referenceList.removeAll(referenceList);

                    for (int i = 0; i < mapList.size(); i++) {
                        Restaurant restaurant = mapList.get(i);
                        String[] itemReferenceList = new String[]{String.valueOf(restaurant.getIdRestaurant()), restaurant.getCity(), restaurant.getNomRestaurant()};
                        referenceList.add(itemReferenceList);
                    }

                    if (getItemIdInListFromStrings(searchRequest, referenceList) != "-1") {
                        showSearchResult(searchPlats);

                    }
                }
            }
                if (getItemIdInListFromStrings(searchRequest, referenceList) != "-1") {

                    ArrayList<String> temp = getListInListFromStrings(searchRequest, referenceList);
                    for (int i = 0; i < restoList.size(); i++) {
                        for (int j = 0; j < temp.size(); j++) {
                            if (temp.get(j).equals(restoList.get(j).getIdRestaurant())) {
                                mapList.add(restoList.get(j));
                            }
                        }
                    }
                    showSearchResult(searchPlats);
                }
            }
        }



    public ArrayList<String> getListInListFromStrings(String request, ArrayList<String[]> referenceList) {

        String[] requestWords = request.split(" ");
        ArrayList<String> relevantList = new ArrayList<>();

        for (int i = 0; i < referenceList.size(); i++) {

            boolean isIncluded = false;
            int numberSameWords = 0;

            for (int j = 0; j < requestWords.length; j++) {

                for (int k = 1; k < referenceList.get(i).length; k++) {

                    if (requestWords[j].toUpperCase().equals(referenceList.get(i)[k].toUpperCase())) {
                        isIncluded = true;
                    } else if (referenceList.get(i)[k].indexOf(' ') > -1) {
                        String[] subList = referenceList.get(i)[k].split(" ");
                        for (int l = 0; l < subList.length; l++) {
                            if (requestWords[j].toUpperCase().equals(subList[l].toUpperCase())) {
                                isIncluded = true;
                            }
                        }
                    }
                }
                if (isIncluded) {
                    numberSameWords += 1;
                    isIncluded = false;
                }
            }
            if (numberSameWords >= requestWords.length) {

                relevantList.add(referenceList.get(i)[0]);
            }
        }
        return relevantList;
    }




    public String getItemIdInListFromStrings(String request, ArrayList<String[]> ReferenceList) {

        String[] requestWords = request.split(" ");

        for (int i = 0; i < ReferenceList.size(); i++) {
            boolean isIncluded = false;
            int numberSameWords = 0;

            for (int j = 0; j < requestWords.length; j++) {

                for (int k = 1; k < ReferenceList.get(i).length; k++) {

                    if (requestWords[j].toUpperCase().equals(ReferenceList.get(i)[k].toUpperCase())) {
                        isIncluded = true;
                    } else if (ReferenceList.get(i)[k].indexOf(' ') > -1) {

                        String[] subList = ReferenceList.get(i)[k].split(" ");
                        for (int l = 0; l < subList.length; l++) {
                            if (requestWords[j].toUpperCase().equals(subList[l].toUpperCase())) {
                                isIncluded = true;
                            }
                        }
                    }
                }
                if (isIncluded) {
                    numberSameWords += 1;
                    isIncluded = false;

                }
            }

            if (numberSameWords >= requestWords.length) {
                return ReferenceList.get(i)[0];
            }
        }

        return "-1";
    }
}