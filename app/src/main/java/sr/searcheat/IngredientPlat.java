package sr.searcheat;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Sélim on 03/03/2018.
 */
public class IngredientPlat extends RealmObject {

@SerializedName("id_ingredient")
    private int id;

    @SerializedName("id_plat")
    private int id_plat;

    public IngredientPlat() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_plat() {
        return id_plat;
    }

    public void setId_plat(int id_plat) {
        this.id_plat = id_plat;
    }
}
