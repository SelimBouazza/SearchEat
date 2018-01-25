package sr.searcheat;

/**
 * Created by Sélim on 14/01/2018.
 */
public class Restaurant {

    private int idRestaurant;

    private String nomRestaurant;

    private String adrRestaurant;

    private Restaurateur Restaurateur;

    public Restaurant(String nomRestaurant, int idRestaurant, String adrRestaurant, Restaurateur Restaurateur) {
        this.nomRestaurant = nomRestaurant;
        this.idRestaurant = idRestaurant;
        this.adrRestaurant = adrRestaurant;
        this.Restaurateur = Restaurateur;
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
}
