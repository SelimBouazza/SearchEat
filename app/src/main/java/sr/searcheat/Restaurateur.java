package sr.searcheat;

/**
 * Created by Sélim on 21/01/2018.
 */
public class Restaurateur extends Profil{

    private int idRestaurateur;


    public Restaurateur(int idRestaurateur)
    {
        super();
        this.idRestaurateur=idRestaurateur;
    }

    public int getIdRestaurateur() {
        return idRestaurateur;
    }

    public void setIdRestaurateur(int idRestaurateur) {
        this.idRestaurateur = idRestaurateur;
    }
}
