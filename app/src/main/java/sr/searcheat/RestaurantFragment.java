package sr.searcheat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sélim on 14/01/2018.
 */
public class RestaurantFragment extends Fragment {

    private View fragmentView;
    private Context context;
    private TextView restaurantAddresse;
    private TextView restaurantPhone;
    private TextView restaurantName;
    private Restaurant restaurant;
    private float heightPaddingTop = 0;
    private ImageView imageGps;
    private View swipePager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        fragmentView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        restaurantAddresse = (TextView)fragmentView.findViewById(R.id.restaurantAdresse);
        restaurantPhone=(TextView)fragmentView.findViewById(R.id.restaurantPhone);
        restaurantName=(TextView)fragmentView.findViewById(R.id.restaurantName);
        imageGps = (ImageView) fragmentView.findViewById(R.id.imageGps);
        swipePager = fragmentView.findViewById(R.id.swipePager);


        this.prepareView();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            long id = bundle.getLong("id", 0);

            if (id != 0) {

                this.loadRestaurantFromDataBase(id);

            }
        }


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

        this.prepareScrollView();
    }


    private void prepareMap() {

        if (Tools.checkIfMapsIsOk(getActivity())) {

            new AsyncTask<Void, Void, GoogleMap>() {

                protected GoogleMap doInBackground(Void... params) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    MapFragment fragment = new MapFragment();
                    fragmentTransaction.add(R.id.mapView, fragment);
                    fragmentTransaction.commit();

                    return null;
                }

                protected void onPostExecute(GoogleMap map) {
                    MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
                    map = mapFragment.getMap();
                    map.getUiSettings().setAllGesturesEnabled(false);
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                    map.moveCamera(center);
                    map.animateCamera(zoom);

                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(restaurant.getLatitude(), restaurant.getLongitude()));

                    map.addMarker(marker);
                }

            }.execute();
        } else {
            fragmentView.findViewById(R.id.mapView).setVisibility(View.GONE);
        }
    }

    private void loadRestaurantFromDataBase(long id) {
    /*    Realm realm = Realm.getInstance(context);

        restaurant = realm.where(Restaurant.class)
                .equalTo("id", id)
                .findFirst();*/

     restaurant = new Restaurant(1, "McDonald", "14 rue Edgar Faure 21000 Dijon");
        restaurant.setPhoneNumber("0666666666");
        restaurant.setLatitude(48.862725);
        restaurant.setLongitude(2.287592);

        this.setData();
    }


    private void prepareScrollView() {
        // Get screen height (whithout status bar)
        Rect windowRect = new Rect();
        fragmentView.getWindowVisibleDisplayFrame(windowRect);

        float totalHeight = (windowRect.bottom - windowRect.top);

        View bottomBloc = fragmentView.findViewById(R.id.bottomBloc);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        bottomBloc.measure(display.getWidth(), display.getHeight());

        heightPaddingTop = (totalHeight - bottomBloc.getMeasuredHeight());

        swipePager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) heightPaddingTop));
    }

    private void setData() {
        getActivity().setTitle(restaurant.getNomRestaurant());
        restaurantName.setText(restaurant.getNomRestaurant());
        restaurantName.setSelected(true);
        restaurantAddresse.setText(restaurant.getAdrRestaurant());

        this.prepareMap();
    }


}
