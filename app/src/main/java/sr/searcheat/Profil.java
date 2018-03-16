package sr.searcheat;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Sélim on 14/01/2018.
 */
public class Profil extends RealmObject{

    @SerializedName("Id_pers")
    private long id ;

    @SerializedName("login")
    private String login;

    @SerializedName("mdp")
    private String mdp;

    @SerializedName("prenom")
    private String firstName;

    @SerializedName("nom")
    private String surname;

    private String email;



    public Profil(){

    }

    public Profil(long id, boolean actived, String email, String surname, String country, String firstName, String mdp, String login) {
        this.id = id;
        this.email = email;
        this.surname = surname;
        this.firstName = firstName;
        this.mdp = mdp;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
