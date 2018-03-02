package sr.searcheat;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpHeaders;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDeleteHC4;

import org.apache.http.client.methods.HttpGetHC4;

import org.apache.http.client.methods.HttpPostHC4;

import org.apache.http.client.methods.HttpPutHC4;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import java.io.IOException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.RealmObject;

import io.realm.RealmObject;

/**
 * Created by Sélim on 18/02/2018.
 */
public class GestionManager {

    private Gson gson;

    protected static String dateFormat = "yyyy-MM-dd";

    protected static String urlBase;

    protected static boolean isInit = false;

    protected static SimpleDateFormat formater;

    protected static String language = "en";

    public GestionManager() {
        gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).setDateFormat(dateFormat)
                .create();
    }

    public static void init(String url) {
        if (!isInit) {
            isInit = true;
            urlBase = url;
            formater = new SimpleDateFormat(dateFormat);
        }
    }

    protected String bodyFromURL(String url) throws IOException,IllegalStateException,NullPointerException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGetHC4 http = new HttpGetHC4(url);
        http.setHeader(HttpHeaders.CONTENT_LANGUAGE, language);
        HttpResponse response = httpclient.execute(http);
        return EntityUtils.toString(response.getEntity());
    }


    public String getUrlBase() throws Exception {
        return urlBase;
    }

    public Gson getGson() {
        return gson;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        GestionManager.language = language;
    }
}
