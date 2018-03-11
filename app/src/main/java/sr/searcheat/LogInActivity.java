package sr.searcheat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sélim on 14/01/2018.
 */
public class LogInActivity extends ActionBarActivity{

    private EditText logInTextUsername;
    private EditText logInTextPassword;
    private Button registration;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();

        logInTextUsername = (EditText) findViewById(R.id.textUsername);
        logInTextPassword = (EditText) findViewById(R.id.textPassword);
        registration = (Button)findViewById(R.id.buttonRegistration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RegistrationActivity.class);
                startActivity(i);
            }
        });
        final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Profil>() {

                    protected Profil doInBackground(Void... params) {
                        Profil profil = null;
                        try {
                            profil = GestionProfil.getInstance().getAuth(logInTextUsername.getText().toString(), logInTextPassword.getText().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return profil;
                    }

                   @Override
                    protected void onPostExecute(Profil profil) {
                        if (profil != null) {

                         //       GestionProfil.addProfilToSharedPref(getApplicationContext(), profil);

                                Toast.makeText(context,"Connexion reussis", Toast.LENGTH_SHORT).show();

                                LogInActivity.this.finish();

                        } else {
                            Toast.makeText(context,"Connexion refusée", Toast.LENGTH_SHORT).show();

                            logInTextPassword.setText("");
                        }
                        super.onPostExecute(profil);
                    }
                }.execute();

            }

        });
    }





}
