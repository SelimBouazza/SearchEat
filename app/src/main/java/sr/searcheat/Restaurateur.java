package sr.searcheat;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Restaurateur extends RealmObject{

    @SerializedName("Id_pers")
    private int id_pers;
    @SerializedName("id_restaurateur")
    private int idRestaurateur;


    public Restaurateur()
    {

    }

    public int getIdRestaurateur() {
        return idRestaurateur;
    }

    public void setIdRestaurateur(int idRestaurateur) {
        this.idRestaurateur = idRestaurateur;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_pers) {
        this.id_pers = id_pers;
    }
}
