package sr.searcheat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Sélim on 14/01/2018.
 */
public class MenuActivity extends ActionBarActivity {

    private Profil profil;
    private TextView textLoginUser;
    private Boolean isInternetConnected = false;
    private Button deconnexion;
    private  SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        isInternetConnected = Tools.isInternetConnected(this);
        textLoginUser = (TextView)findViewById(R.id.textLoginUser);
        deconnexion = (Button) findViewById(R.id.deco);


    }
    @Override
    protected void onResume() {
        isInternetConnected = Tools.isInternetConnected(this);

       if (isInternetConnected ) {


           try {
               profil = GestionProfil.getInstance().getProfilFromSharedPref(getApplicationContext());
           } catch (InstantiationException e) {
               e.printStackTrace();
           }
         //  Log.i("piiii",profil.getLogin());
            if (profil != null && profil.getLogin() != "") {
                textLoginUser.setText(profil.getLogin());
                deconnexion.setVisibility(VISIBLE);
            }
            else
            {
                textLoginUser.setText("");
               deconnexion.setVisibility(INVISIBLE);
            }
      }
        super.onResume();
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

   public void createAlertDialogDisconnect(View v) {
        //Creation of the AlertDialog
        if (profil != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            builder.setTitle("Voulez-vous vous déconnectez?");
            builder.setCancelable(true);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            profil = null;
                            GestionProfil.removeProfilFromSharedPref(getApplicationContext());
                            textLoginUser.setText("");
                            deconnexion.setVisibility(INVISIBLE);

                        }
                    }
            );
            //No
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }
            );
            //Show AlertDialog
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
