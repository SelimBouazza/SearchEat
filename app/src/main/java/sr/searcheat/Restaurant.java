package sr.searcheat;


import android.location.Address;
import android.location.Geocoder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sélim on 14/01/2018.
 */
public class Restaurant  extends RealmObject{


    @SerializedName("id_restaurant")  @PrimaryKey
    private int idRestaurant;

    @SerializedName("nom_restaurant")
    private String nomRestaurant;

    @SerializedName("adr_restaurant")
    private String adrRestaurant;

    @SerializedName("id_restaurateur")
    private int idRestaurateur;

    private double longitude;

    private double latitude;

    private String phoneNumber;



    private RealmList<Plat> plats;

    private String city;

    public Restaurant(){

    }

    public String getAdrRestaurant() {
        return adrRestaurant;
    }

    public void setAdrRestaurant(String adrRestaurant) {
        this.adrRestaurant = adrRestaurant;
    }


    public int getIdRestaurateur() {
        return idRestaurateur;
    }

    public void setIdRestaurateur(int idRestaurateur) {
        this.idRestaurateur = idRestaurateur;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RealmList<Plat> getPlats() {
        return plats;
    }

    public void setPlats(RealmList<Plat> plats) {
        this.plats = plats;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
