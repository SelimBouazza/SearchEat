package sr.searcheat;

import java.util.ArrayList;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Plat {

    private int idPlat;
    private String nomPlat;
    private ArrayList<Ingredient> ingredients;

    public Plat(int idPlat, ArrayList<Ingredient> ingredients, String nomPlat) {
        this.idPlat = idPlat;
        this.ingredients = ingredients;
        this.nomPlat = nomPlat;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }
}
