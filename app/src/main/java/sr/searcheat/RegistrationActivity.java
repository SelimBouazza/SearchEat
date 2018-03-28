package sr.searcheat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sélim on 14/01/2018.
 */
public class RegistrationActivity extends Activity { //Page de l'inscription

    private EditText textName;
    private EditText textSurname;
    private EditText textLogin;
    private EditText textPwd;
    private EditText textConfirmPwd;
    private EditText textMail;
    private int error=0;
    private Profil profil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activy_registration);

        textName = (EditText) findViewById(R.id.textName);
        textSurname = (EditText) findViewById(R.id.textSurname);
        textLogin = (EditText) findViewById(R.id.textLogin);
        textPwd = (EditText) findViewById(R.id.textPwd);
        textConfirmPwd = (EditText) findViewById(R.id.textConfirmPwd);
        textMail = (EditText) findViewById(R.id.textMail);

        final Button buttonValid = (Button) findViewById(R.id.buttonValid);
        buttonValid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                profil = createProfilFromEditTexts();

               verificationOfProfilForPostOrPut(profil);

                    if (!textPwd.getText().toString().equals(textConfirmPwd.getText().toString())|| textPwd.getText().toString()=="" || error==0) {
                        Toast.makeText(getApplicationContext(), R.string.errorPwdConfirm, Toast.LENGTH_SHORT).show();
                    }
                    else{

                        postNewProfil(profil);

                    }
                }


        });
    }

    public  void verificationOfProfilForPostOrPut(Profil profil) { //Ici on vérifie que les données sont bien au normes

        if (profil.getLogin().equals("") ||  // if Form OK
                profil.getMdp().equals("") ||
                profil.getFirstName().equals("") ||
                profil.getSurname().equals("") ||
                profil.getEmail().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.errorForm, Toast.LENGTH_SHORT).show();
        } else {
            Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
            Matcher m = p.matcher(profil.getMdp());

            if (!m.find()) {
                Toast.makeText(getApplicationContext(), R.string.errorMdp, Toast.LENGTH_SHORT).show();
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(profil.getEmail()).matches()) {
                    Toast.makeText(getApplicationContext(), R.string.errormMail, Toast.LENGTH_SHORT).show();
                }else
                {
                    error=1;
                }
            }
        }

    }


    private Profil createProfilFromEditTexts() {

        profil= new Profil();
        profil.setFirstName(textName.getText().toString());
        profil.setSurname(textSurname.getText().toString());
        profil.setLogin(textLogin.getText().toString());
        profil.setMdp(textPwd.getText().toString());
        profil.setEmail(textMail.getText().toString());
        return profil;
    }

    private void postNewProfil(final Profil profil) { //On envoie le profil dans la BDD
        new AsyncTask<Profil, Void, Profil>() {



            protected Profil doInBackground(Profil... params) {


                try {
                    GestionProfil.getInstance().postProfil(profil);
                    return profil;
                } catch (Exception e) {

                }

                return null;
            }

            protected void onPostExecute(Profil profil) {


                    if(profil != null) {

                        Toast.makeText(getApplicationContext(),"Inscription Reussi",Toast.LENGTH_SHORT).show();
                        RegistrationActivity.this.finish();
                    }

            }

        }.execute(profil);
    }
}

