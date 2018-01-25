package sr.searcheat;

import java.util.ArrayList;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Menu {

    private int idMenu;
    private int idRestaurant;
    private ArrayList<Plat> Plats;

    public Menu(int idMenu, ArrayList<Plat> plats, int idRestaurant) {
        this.idMenu = idMenu;
        Plats = plats;
        this.idRestaurant = idRestaurant;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public ArrayList<Plat> getPlats() {
        return Plats;
    }

    public void setPlats(ArrayList<Plat> plats) {
        Plats = plats;
    }
}
