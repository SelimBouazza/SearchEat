package sr.searcheat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Sélim on 26/01/2018.
 */
public class DrawerAdapter extends ArrayAdapter<String> {

    private List<String> items;

    private int resource;

    public DrawerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DrawerHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new DrawerHolder();
        //    holder.itemImg = (ImageView) row.findViewById(R.id.item_img_drawer);

            row.setTag(holder);
        } else {
            holder = (DrawerHolder) row.getTag();
        }



        return row;
    }

    static class DrawerHolder {
        ImageView itemImg;
    }


}