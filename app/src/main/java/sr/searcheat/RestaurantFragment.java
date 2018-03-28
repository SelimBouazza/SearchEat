package sr.searcheat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Sélim on 14/01/2018.
 */
public class RestaurantFragment extends Fragment { //Page détails du restaurant

    private View fragmentView;
    private Context context;
    private TextView restaurantAddresse;
    private TextView restaurantName;
    private TextView restaurantPhone;
    private Restaurant restaurant;
    private ImageView imageGps;
    private GridLayout platsGrid;
    private CheckBox favorites;
    private Profil profil=null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        fragmentView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        restaurantAddresse = (TextView)fragmentView.findViewById(R.id.restaurantAdresse);
        restaurantPhone=(TextView)fragmentView.findViewById(R.id.restaurantPhone);
        restaurantName=(TextView)fragmentView.findViewById(R.id.restaurantName);
        imageGps = (ImageView) fragmentView.findViewById(R.id.imageGps);
        platsGrid = (GridLayout)fragmentView.findViewById(R.id.platsGrid);
        favorites = (CheckBox)fragmentView.findViewById(R.id.fav);

        SharedPreferences sharedPref = context.getSharedPreferences(Global.SP_FAV, Context.MODE_PRIVATE);
        Set<String> restoIds = sharedPref.getStringSet(Global.SP_FAV, new HashSet<String>());

        this.prepareView();
        Bundle bundle = this.getArguments();
        if (bundle != null) {                   //Récupération du restaurant en question
            long id = bundle.getLong("id", 0);

            if (id != 0) {

                this.loadRestaurantFromDataBase(id);

            }
        }

        if(restoIds.contains(String.valueOf(restaurant.getIdRestaurant())))
        {

            favorites.setChecked(true);
        }

        try {
            profil = GestionProfil.getInstance().getProfilFromSharedPref(context);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        if (profil == null ) {
           favorites.setEnabled(false);
        }
        favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                 @Override
                                                 public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //Gestion des favoris
                                                     if (favorites.isChecked()) {
                                                         try {
                                                             GestionRestaurant.getInstance().addRestoFav(context,restaurant);
                                                         } catch (java.lang.InstantiationException e) {
                                                             e.printStackTrace();
                                                         }
                                                     } else {
                                                         try {
                                                             GestionRestaurant.getInstance().removeRestoFromFav(context,restaurant);
                                                         } catch (java.lang.InstantiationException e) {
                                                             e.printStackTrace();
                                                         }
                                                     }
                                                 }
                                             }
        );



        return fragmentView;
    }
    private void prepareView() {
        restaurantPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Appeller le restaurant")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Tools.callNumber(restaurant.getPhoneNumber(), context);
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });


        imageGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showMap(Uri.parse("google.navigation:q=" + restaurant.getLatitude() + "," + restaurant.getLongitude()), context);
            }
        });


    }




    private void loadRestaurantFromDataBase(long id) {
      Realm realm = Realm.getInstance(context);

        restaurant = realm.where(Restaurant.class)
                .equalTo("idRestaurant", id)
                .findFirst();


        this.setData();
    }



    private void setData() { //Ajout de toute les informations necessaire dans le fragment
        getActivity().setTitle(restaurant.getNomRestaurant());
        restaurantName.setText(restaurant.getNomRestaurant());
        restaurantName.setSelected(true);
        restaurantAddresse.setText(restaurant.getAdrRestaurant());
        restaurantPhone.setText(restaurant.getPhoneRestaurant());
        platsGrid.setOrientation(GridLayout.VERTICAL);
        for(final Plat plat : restaurant.getPlats())
        {
            final TextView textPlat = new TextView(context);
            textPlat.setText(plat.getNomPlat());
            textPlat.setTextSize(28);

            textPlat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String s = "";
                    int i =0;
                    for (Ingredient ingredient : plat.getIngredients()) {
                        i++;
                        s += ingredient.getNom();
                        if(i<plat.getIngredients().size())
                            s+=" - ";

                    }
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });

                platsGrid.addView(textPlat);
        }


    }


}
