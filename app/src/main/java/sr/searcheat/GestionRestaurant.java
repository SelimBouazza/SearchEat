package sr.searcheat;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;



import java.lang.reflect.Type;
import java.net.URLEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import io.realm.RealmList;

/**
 * Created by Sélim on 18/02/2018.
 */
public class GestionRestaurant extends GestionManager {

    private final static GestionRestaurant instance = new GestionRestaurant();

    private Type listType;

    private GestionRestaurant() {
        listType = new TypeToken<List<Restaurant>>() {
        }.getType();
    }

    public static GestionRestaurant getInstance() throws InstantiationException {
        if (GestionManager.isInit) {
            return instance;
        } else {
            throw new InstantiationException("Not init, pls use GestionController.init(String url) to init");
        }
    }

    public List<Restaurant> getRestaurants() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "restaurant.php");
        return super.getGson().fromJson(json, listType);
    }

    public List<Restaurateur> getRestaurateur() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "restaurateur.php");
        Type type = new TypeToken<RealmList<Restaurateur>>() {
        }.getType();
        return super.getGson().fromJson(json, type);
    }
    public List<Ingredient> getIngredient() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "ingredient.php");
        Type type = new TypeToken<RealmList<Ingredient>>() {
        }.getType();
        return super.getGson().fromJson(json, type);
    }

    public List<Plat> getPlat() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "plat.php");

        Type type = new TypeToken<RealmList<Plat>>() {
        }.getType();
        return super.getGson().fromJson(json, type);
    }



    public List<IngredientPlat> getIngredientPlat() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "ingredientPlat.php");
        Type type = new TypeToken<RealmList<IngredientPlat>>() {
        }.getType();
        return super.getGson().fromJson(json, type);
    }

    public void removeRestoFromFav(Context context, Restaurant restaurant) {
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_FAV, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> hotelsIds = sharedPref.getStringSet(Global.SP_FAV, new HashSet<String>());
        hotelsIds.remove(String.valueOf(restaurant.getIdRestaurant()));
        editor.clear();
        editor.putStringSet(Global.SP_FAV, hotelsIds);
        editor.commit();

        Toast.makeText(context,"Supprimé", Toast.LENGTH_SHORT).show();
    }


    public void addRestoFav(Context context, Restaurant restaurant) {
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_FAV, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> hotelsIds = sharedPref.getStringSet(Global.SP_FAV, new HashSet<String>());
        hotelsIds.add(String.valueOf(restaurant.getIdRestaurant()));
        editor.clear();
        editor.putStringSet(Global.SP_FAV, hotelsIds);
        editor.commit();

        Toast.makeText(context,"Ajouté", Toast.LENGTH_SHORT).show();
    }



}



