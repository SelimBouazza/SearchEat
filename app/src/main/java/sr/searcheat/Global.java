package sr.searcheat;

/**
 * Created by Sélim on 02/02/2018.
 */
public class Global { //Stockage de variable globale pour les methode de connexion et de stockage interne

    public static final String SP_GROUP_USER = "groupUser";
    public static final String SP_USER_RADIUS = "pref_radius";
    public static final int DEFAULT_RADIUS = 6;
    public static final String SP_GROUP_DB = "groupDB";
    public static final String SP_UPDATETIME = "updateTime";
    public static final String DATABASE_URL = "https://bouazzaselim.000webhostapp.com/";
    public static final String SP_PROFIL = "profil";
    public static final String SP_FAV = "restoFav";

    public enum MethodHTML {
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String name = "";

        MethodHTML(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}
