package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetaLokasiMadrasahFragment extends Fragment {


    public PetaLokasiMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peta_lokasi_madrasah, container, false);
    }

}