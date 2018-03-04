package sr.searcheat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sélim on 14/01/2018.
 */
public class MenuActivity extends ActionBarActivity {

    private Profil profil;
    private TextView textLoginUser;
    private Boolean isInternetConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        isInternetConnected = Tools.isInternetConnected(this);

    }

    public void changePage(View view) {

        String tag = (String) view.getTag();

        Intent i = null;

        if (tag.equals("user")) {
            if (isInternetConnected) {
                if (profil == null) {
                    i = new Intent(MenuActivity.this, LogInActivity.class);
                } else {
                    i = new Intent(MenuActivity.this, UserActivity.class);
                }
            }else{
                Toast.makeText(this,"Internet required", Toast.LENGTH_LONG).show();
            }
        } else {
            i = new Intent(MenuActivity.this, MainActivity.class);
        }

        if (i != null) {
            i.putExtra("action", tag);
            startActivity(i);
        }
    }
}
