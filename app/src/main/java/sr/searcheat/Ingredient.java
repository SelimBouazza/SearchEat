package sr.searcheat;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Ingredient extends RealmObject{

    @SerializedName("id_ingredient")
    private int id;

    @SerializedName("nom_ingredient")
    private String nom;

    @SerializedName("id_restaurant")
    private int idRestaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
