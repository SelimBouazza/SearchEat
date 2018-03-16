package sr.searcheat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Plat extends RealmObject {

    @SerializedName("id_plat")
    private int idPlat;
    @SerializedName("nom_plat")
    private String nomPlat;

    @SerializedName("id_restaurant")
    private int idRestaurant;

    private RealmList<Ingredient> ingredients = new RealmList<>();


    public Plat(){
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }


    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public RealmList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
