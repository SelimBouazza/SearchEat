package sr.searcheat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sélim on 03/03/2018.
 */
public class GestionProfil extends GestionManager { //Ici c'est tout ce qui concerne la connexion BDD pour les données utilisateurs

    private final static GestionProfil instance = new GestionProfil();

    private Type listType;

    private GestionProfil() {
        listType = new TypeToken<List<Profil>>() {
        }.getType();
    }

    public static GestionProfil getInstance() throws InstantiationException {
        if (GestionManager.isInit) {
            return instance;
        } else {
            throw new InstantiationException("Not init, pls use GestionManager.init(String url) to init");
        }
    }

    public List<Profil> getProfil() throws Exception {
        String json = super.bodyFromURL(super.getUrlBase() + "personne.php");
        return super.getGson().fromJson(json, listType);
    }

    public Profil getAuth(String login, String password) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login", login));
        nameValuePairs.add(new BasicNameValuePair("mdp", password));

        String profil = super.postDataWithResponse(super.getUrlBase() + "auth.php", nameValuePairs, Global.MethodHTML.POST);


        if (profil != null && !profil.isEmpty()) {


            List<Profil> profils = super.getGson().fromJson(profil, listType);
            return profils.get(0);
        }
        return null;
    }

    public  void addProfilToSharedPref(Context context, Profil profil)  {
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_PROFIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String json = super.getGson().toJson(profil,Profil.class);
        editor.putString(Global.SP_PROFIL,json);
        editor.commit();
    }

    public static void removeProfilFromSharedPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_PROFIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Global.SP_PROFIL);
        editor.commit();
    }

    public  Profil getProfilFromSharedPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_PROFIL, Context.MODE_PRIVATE);
        String value = sharedPref.getString(Global.SP_PROFIL, "");
        if(value.equals("")) {
            return null;
        }
        else {
            return super.getGson().fromJson(value, Profil.class);
        }
    }



    public String postProfil(Profil profil) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login", profil.getLogin()));
        nameValuePairs.add(new BasicNameValuePair("mdp", profil.getMdp()));
        nameValuePairs.add(new BasicNameValuePair("first_name", profil.getFirstName()));
        nameValuePairs.add(new BasicNameValuePair("surname", profil.getSurname()));
        nameValuePairs.add(new BasicNameValuePair("email", profil.getEmail()));
       return  super.postDataWithResponse(super.getUrlBase() + "registration.php", nameValuePairs, Global.MethodHTML.POST);
    }

}
