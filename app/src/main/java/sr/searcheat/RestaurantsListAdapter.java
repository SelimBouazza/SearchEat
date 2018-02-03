package sr.searcheat;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;



/**
 * Created by Sélim on 02/02/2018.
 */
public class RestaurantsListAdapter extends ArrayAdapter<Restaurant> {

    private List<Restaurant> items;
    private int resource;
    private Context context;
    private Location currentLocation;


    private boolean showDistance = true;

    public RestaurantsListAdapter(Context context, int resource, List<Restaurant> items, Location currentLocation) {
        super(context, resource, items);
        this.items = items;
        this.resource = resource;
        this.context = context;
        this.currentLocation=currentLocation;
    }

    private static class ViewHolder {
        public TextView RestaurantTitle;
        public TextView RestaurantCity;
        public TextView RestaurantDistance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(resource, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.RestaurantTitle = (TextView) rowView.findViewById(R.id.restoTitle);
            viewHolder.RestaurantCity = (TextView) rowView.findViewById(R.id.restoCity);
            viewHolder.RestaurantDistance = (TextView) rowView.findViewById(R.id.restoDistance);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Restaurant item = items.get(position);

        int color = ((position % 2) != 0) ? android.R.color.white : android.R.color.black;

        if ((position % 2) != 0) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }

        ((TextView) rowView.findViewById(R.id.restoPreTitle)).setTextColor(context.getResources().getColor(color));
        holder.RestaurantTitle.setTextColor(context.getResources().getColor(color));
        holder.RestaurantTitle.setTextColor(context.getResources().getColor(color));
        holder.RestaurantCity.setTextColor(context.getResources().getColor(color));
        holder.RestaurantDistance.setTextColor(context.getResources().getColor(color));

        holder.RestaurantTitle.setText(item.getNomRestaurant());
        holder.RestaurantTitle.setSelected(true);

       if (showDistance) {


            holder.RestaurantDistance.setVisibility(View.VISIBLE);
            holder.RestaurantDistance.setText(String.valueOf(Math.floor(GeoTools.distanceTo(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    item.getLatitude(), item.getLongitude())) + " km"));
        }else{
            holder.RestaurantDistance.setVisibility(View.GONE);
        }

        holder.RestaurantCity.setText(item.getAdrRestaurant());


        return rowView;
    }



    public void setShowDistance(boolean showDistance) {
        this.showDistance = showDistance;
    }
}
