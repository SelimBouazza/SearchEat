package sr.searcheat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Sélim on 18/02/2018.
 */
public class RestaurantTask{

public static class LoaderServerRestaurantTask   extends AsyncTask<Void, Restaurant, List<Restaurant>>{


    private SplashActivity splashActivity;

    private Context context;

    private SharedPreferences prefs;

    //Récupération de toute les données de la BDD


    public LoaderServerRestaurantTask (SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
        this.context = splashActivity;
    }

    @Override
    protected void onPreExecute() {
        prefs = context.getSharedPreferences(Global.SP_GROUP_DB, Context.MODE_PRIVATE);

        super.onPreExecute();
    }

    @Override
    protected List<Restaurant> doInBackground(Void... params) {

        long updatedTime = prefs.getLong(Global.SP_UPDATETIME, 0);

        if (updatedTime == 0) {
            Realm realm = Realm.getInstance(context);

            realm.beginTransaction();
            realm.clear(Plat.class);
            realm.clear(Restaurant.class);
            realm.clear(Ingredient.class);
            realm.clear(Profil.class);
            realm.clear(Restaurateur.class);

            realm.commitTransaction();
        }
        List<Restaurant> restaurants = null;
        List<Restaurateur> restaurateurs = null;
        List<Ingredient> ingredients = null;
        List<Plat> plats = null;
        List<IngredientPlat> ingredientPlats = null;
        List<Profil> profils = null;



        try {
            restaurants = GestionRestaurant.getInstance().getRestaurants();


           plats = GestionRestaurant.getInstance().getPlat();
            ingredientPlats = GestionRestaurant.getInstance().getIngredientPlat();
            ingredients = GestionRestaurant.getInstance().getIngredient();
            profils = GestionProfil.getInstance().getProfil();
            restaurateurs = GestionRestaurant.getInstance().getRestaurateur();



                    if (restaurants != null && restaurants.size() != 0) {
                        for (Restaurant restaurant : restaurants) {
                            //GeoTools.Position p = new GeoTools.Position();
                            GeoTools.Position p= GeoTools.getLocationPosition(context, restaurant.getAdrRestaurant());
                            restaurant.setLatitude(p.latitude);
                            restaurant.setLongitude(p.longitude);



                            for(IngredientPlat ingredientPlat : ingredientPlats)
                            {
                                for(Ingredient ingredient : ingredients)
                                {
                                    for(Plat plat : plats)
                                    {
                                        if(ingredient.getId()==ingredientPlat.getId() && ingredientPlat.getId_plat()==plat.getIdPlat() && restaurant.getIdRestaurant()==plat.getIdRestaurant())
                                        {
                                            if(!plat.getIngredients().contains(ingredient))
                                            plat.getIngredients().add(ingredient);
                                            if(!restaurant.getPlats().contains(plat))
                                            restaurant.getPlats().add(plat);


                                        }

                                    }

                                }

                            }
                            onProgressUpdate(restaurant);
                        }

                    }


                } catch (Exception e) {
                  Log.i("caaaa",e.getMessage());
                    e.printStackTrace();
        }
        return restaurants;
    }

    @Override
    protected void onProgressUpdate(Restaurant... restaurants) {
        super.onProgressUpdate(restaurants);

        RestaurantTask.addRestaurantToBase(context, restaurants[0]);
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {


        if(splashActivity != null) {

            splashActivity.changePage();

        }
        super.onPostExecute(restaurants);
    }



}


    private static void addRestaurantToBase(Context context, Restaurant restaurant) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(restaurant);

        realm.commitTransaction();

        realm.close();
    }



    }







