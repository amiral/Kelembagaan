package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.disclaimer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisclaimerFragment extends Fragment {


    public DisclaimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disclaimer, container, false);

//        WebView wv = (WebView) view.findViewById(R.id.textContent);
//        String text;
//        text = "<html><body><p align=\"justify\">";
//        text+= ""+getString(R.string.disclaimer);
//        text+= "</p></body></html>";
//        wv.loadData(text, "text/html", "utf-8");


        return view;
    }


}
