package sr.searcheat;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Sélim on 14/01/2018.
 */
public class UserActivity  extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
    }
}
