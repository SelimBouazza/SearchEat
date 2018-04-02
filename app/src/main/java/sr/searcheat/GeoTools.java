package sr.searcheat;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sélim on 02/02/2018.
 */
class GeoTools {

    private static final int EARTH_RADIUS = 6371;

    private double latitude;
    private double longitude;
    private int radiusInKm;

    public GeoTools(double latitude, double longitude, int radiusInKm) {
        this.latitude = latitude;
        this.longitude = longitude;

        if(radiusInKm < 0)
            radiusInKm = 0;

        this.radiusInKm = radiusInKm;
    }

    private double getDeltaEarth() {
        return 180 / (Math.PI * EARTH_RADIUS);
    }

    public double getEcartLongitude() {
        return Math.abs(this.getEcartLatitude() / Math.cos(latitude));
    }

    public double getEcartLatitude() {
        return radiusInKm * getDeltaEarth();
    }

    public double getMaxLatitude() {
        return latitude + getEcartLatitude();
    }

    public double getMinLatitude() {
        return latitude - getEcartLatitude();
    }

    public double getMaxLongitude() {
        return longitude + getEcartLongitude();
    }

    public double getMinLongitude() {
        return longitude - getEcartLongitude();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRadiusInKm() {
        return radiusInKm;
    }

    public void setRadiusInKm(int radiusInKm) {
        if(radiusInKm < 0)
            radiusInKm = 0;
        this.radiusInKm = radiusInKm;
    }

    public boolean pointIsInRadius(double latitude, double longitude) {
        if (latitude >= this.getMinLatitude() && latitude <= this.getMaxLatitude()) {

            if (longitude >= this.getMinLongitude() && longitude <= this.getMaxLongitude()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static double distanceTo(double latitude1, double longitude1, double latitude2, double longitude2) {

        Location location1 = new Location("GPS");
        location1.setLatitude(latitude1);
        location1.setLongitude(longitude1);

        Location location2 = new Location("GPS");
        location2.setLatitude(latitude2);
        location2.setLongitude(longitude2);

        Log.i("lati1", String.valueOf(latitude1));
        Log.i("longitude1",String.valueOf(longitude1));

        Log.i("lati2",String.valueOf(latitude2));
        Log.i("long2",String.valueOf(longitude2));

        float distance = location1.distanceTo(location2);
        distance = Math.abs(distance);

        return distance;
    }

    public static class Position {
        public double latitude, longitude;
    }

    public static Position getLocationPosition(Context context, String address) throws IOException, JSONException {
        Geocoder geocoder = new Geocoder(context);


        List<Address>addresses = geocoder.getFromLocationName(address, 1);

        Position position = null;
        if(addresses.size() > 0) {
            position = new Position();

            position.latitude= addresses.get(0).getLatitude();
            position.longitude= addresses.get(0).getLongitude();

        }

        return position;
    }
}