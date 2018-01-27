package sr.searcheat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Sélim on 14/01/2018.
 */
public class LogInActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void linkToRegistration(View v) {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }

}
