package sr.searcheat;


import com.google.gson.reflect.TypeToken;



import java.lang.reflect.Type;
import java.net.URLEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


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
        String json = super.bodyFromURL(super.getUrlBase() + "restauranteur.php");
        return super.getGson().fromJson(json, listType);
    }

}



