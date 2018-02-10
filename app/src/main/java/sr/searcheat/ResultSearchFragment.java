package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sélim on 04/02/2018.
 */
public class ResultSearchFragment extends Fragment {


    private View fragmentView;
    private ListView listView;
    private List<Restaurant> restaurants = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;
    private Context context;
    private List<Restaurant> restoList ;



    int radius = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        radius = context.getSharedPreferences(Global.SP_GROUP_USER, Context.MODE_PRIVATE).getInt(Global.SP_USER_RADIUS, Global.DEFAULT_RADIUS);

        fragmentView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        listView = (ListView) fragmentView.findViewById(R.id.listResto);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setEnabled(false);


        return fragmentView;
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Resultats de la recherche");
        ((MainActivity)getActivity()).setVisibilityMenu(false, false,false);

        if(restoList.isEmpty())
        {
            Toast.makeText(context,"Aucun restaurant trouvée", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
