package sr.searcheat;

/**
 * Created by Sélim on 02/02/2018.
 */
public class Global {

    public static final String SP_GROUP_USER = "groupUser";
    public static final String SP_USER_RADIUS = "pref_radius";
    public static final int DEFAULT_RADIUS = 500000000;
    public static final String SP_GROUP_DB = "groupDB";
    public static final String SP_UPDATETIME = "updateTime";
    public static final String DATABASE_URL = "https://bouazzaselim.000webhostapp.com/";

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
