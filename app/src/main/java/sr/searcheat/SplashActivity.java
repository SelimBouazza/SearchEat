package sr.searcheat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Sélim on 26/01/2018.
 */
public class SplashActivity extends AppCompatActivity {

    private Boolean isInternetConnected = false;

    // OVERRIDE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isInternetConnected = Tools.isInternetConnected(this);
        changePage();


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
