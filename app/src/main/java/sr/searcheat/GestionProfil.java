package sr.searcheat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sélim on 03/03/2018.
 */
public class GestionProfil extends GestionManager {

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

}
