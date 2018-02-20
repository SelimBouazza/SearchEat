package sr.searcheat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Set;


public class MainActivity extends ActionBarActivity{

    private String[] menuItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private android.view.Menu menu;

    private Boolean isInternetConnected = false;

    private Profil profil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isInternetConnected = Tools.isInternetConnected(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.initDrawerLayout();

        String action = getIntent().getStringExtra("action");

        if (action != null) {
            switch (action) {
                case "search":

                    putFragment(new SearchFragment());
                    break;
                case "geo":
                    putFragment(new RestaurantGeoFragment());
                    break;
                case "fav":
                    putFragment(new FavorisFragment());
                    break;
                default:
                    putFragment(new FavorisFragment());
                    break;
            }
        } else {
            putFragment(new FavorisFragment());
        }
    }

    @Override
    protected void onResume() {
        isInternetConnected = Tools.isInternetConnected(this);


        super.onResume();
    }

    private void initDrawerLayout() {
        menuItems = getResources().getStringArray(R.array.drawerMenu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

      //  drawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_item, Arrays.asList(menuItems)));

    //    drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    public void showSearchResult(@NonNull ResultSearchFragment fragment, @NonNull String search) {


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

        fragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public void showRestaurantFragment(@NonNull RestaurantFragment fragment, @NonNull long id) {

        if (id != 0 && id > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            fragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

        fragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void putFragment(@NonNull Fragment fragment) {

        if (fragment instanceof RestaurantGeoFragment && checkGPSStatus() || !(fragment instanceof RestaurantGeoFragment)) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());

            fragmentTransaction.commit();

            setTitleBarNameFromFragmentClass(fragment.getClass());
        }
    }
    private void setTitleBarNameFromFragmentClass(Class fragmentClass) {
        if (fragmentClass == RestaurantGeoFragment.class) {
            this.setTitle("Restaurant around");
        } else if (fragmentClass == FavorisFragment.class) {
            this.setTitle("Favoris");
        } else if (fragmentClass == SearchFragment.class) {
            this.setTitle("Search");
        }
    }

    public boolean checkGPSStatus() {
        LocationManager locationManager = null;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gpsEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            networkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("GPS disabled");
            dialog.setMessage("Activer Gps?");
            dialog.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
            dialog.setNegativeButton(android.R.string.no,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = dialog.create();
            alert.show();
            return false;
        } else {
            return true;
        }
    }

    public void setVisibilityMenu(final Boolean search, final Boolean resto, final Boolean share) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                if (MainActivity.this.menu == null) {
                    while (MainActivity.this.menu == null) {
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                MainActivity.this.menu.getItem(0).setVisible(search); // Search
                MainActivity.this.menu.getItem(1).setVisible(resto); // Favorites
                MainActivity.this.menu.getItem(2).setVisible(share); // Share

                super.onPostExecute(aVoid);
            }
        }.execute();
    }

}
