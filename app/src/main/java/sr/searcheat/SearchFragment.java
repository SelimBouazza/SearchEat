package sr.searcheat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by Sélim on 14/01/2018.
 */
public class SearchFragment extends Fragment {

    private View fragmentView;
    private Context context;
    private ImageView imageView;




    private EditText searchEditText;

    private InputMethodManager imm;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context=getActivity().getApplicationContext();
        fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = (EditText) fragmentView.findViewById(R.id.searchEditText);
        imageView = (ImageView) fragmentView.findViewById(R.id.imageSearchTop);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    openSearchResultsPage();
                    return true;
                }
                return false;
            }
        });
        imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchResultsPage();
            }
        });

        return fragmentView;

    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).setVisibilityMenu(false, false, false);
        super.onResume();
    }

    private void openSearchResultsPage() {
        final String searchText = searchEditText.getText().toString();
        if (searchText.equals("")) {
            Toast.makeText(context,"Champ vide", Toast.LENGTH_SHORT).show();
        } else {

            ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
            ((MainActivity) getActivity()).showSearchResult(resultSearchFragment, searchText);
            Toast.makeText(context,"Veuillez patientez", Toast.LENGTH_SHORT).show();
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(),0 );

        }
    }
}
