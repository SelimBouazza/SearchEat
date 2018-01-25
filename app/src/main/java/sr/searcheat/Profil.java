package sr.searcheat;


/**
 * Created by Sélim on 14/01/2018.
 */
public class Profil {


    private long id ;


    private String login;


    private String mdp;


    private String firstName;


    private String country;


    private String surname;


    private String email;

    private boolean actived;

    public Profil(){

    }

    public Profil(long id, boolean actived, String email, String surname, String country, String firstName, String mdp, String login) {
        this.id = id;
        this.actived = actived;
        this.email = email;
        this.surname = surname;
        this.country = country;
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

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
