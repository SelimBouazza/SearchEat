package sr.searcheat;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import android.widget.Toast;

/**
 * Created by Sélim on 26/01/2018.
 */
public class SplashActivity extends ActionBarActivity {

//Page de chargment visuel de la BDD

    // OVERRIDE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initManagerAndLoadRestaurant();


    }

    private void initManagerAndLoadRestaurant() {
        try {
            GestionManager.init(Global.DATABASE_URL);
            new RestaurantTask.LoaderServerRestaurantTask(this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void changePage() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_in);
                finish();
            }

        }, 1000);
    }
}
