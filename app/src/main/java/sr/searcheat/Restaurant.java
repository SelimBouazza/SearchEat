package sr.searcheat;

/**
 * Created by Sélim on 14/01/2018.
 */
public class Restaurant {

    private int idRestaurant;

    private String nomRestaurant;

    private String adrRestaurant;

    private double longitude;

    private double latitude;

    private Restaurateur Restaurateur;

    public Restaurant(int idRestaurant, String nomRestaurant, String adrRestaurant) {
        this.nomRestaurant = nomRestaurant;
        this.idRestaurant = idRestaurant;
        this.adrRestaurant = adrRestaurant;

    }

    public String getAdrRestaurant() {
        return adrRestaurant;
    }

    public void setAdrRestaurant(String adrRestaurant) {
        this.adrRestaurant = adrRestaurant;
    }

    public sr.searcheat.Restaurateur getRestaurateur() {
        return Restaurateur;
    }

    public void setRestaurateur(sr.searcheat.Restaurateur restaurateur) {
        Restaurateur = restaurateur;
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
}
