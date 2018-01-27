package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Set;

/**
 * Created by Sélim on 14/01/2018.
 */
public class SearchFragment extends Fragment {

    private View fragmentView;
    private Context context;
    private ImageView imageView;

    public Set<String> serviceTag;
    public Set<String> categories;

    private TextView searchBudget;

    private EditText searchEditText;

    private InputMethodManager imm;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context=getActivity().getApplicationContext();
        fragmentView = inflater.inflate(R.layout.fragment_search,container,false);
        return fragmentView;

    }
}
