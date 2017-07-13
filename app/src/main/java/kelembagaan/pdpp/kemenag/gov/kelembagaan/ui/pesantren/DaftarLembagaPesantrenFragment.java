package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah.MadrasahActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarLembagaPesantrenFragment extends Fragment {

    View view;
    Pesantren pesantren;

    ListView lvMadrasah;
    TextView tvNodata;
    public DaftarLembagaPesantrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pesantren = Parcels.unwrap(getArguments().getParcelable("pesantren"));
         view = inflater.inflate(R.layout.fragment_daftar_lembaga_pesantren, container, false);

        lvMadrasah = (ListView) view.findViewById(R.id.list_madrasah);
        tvNodata = (TextView) view.findViewById(R.id.text_no_data);

        LembagaDbHelper helper = new LembagaDbHelper(getContext());
        final List<Lembaga> lsLembaga = helper.getAllLembagaPesantren(pesantren.getNspp());

        if (lsLembaga.size() > 0){
            tvNodata.setVisibility(View.GONE);
            lvMadrasah.setVisibility(View.VISIBLE);

            PesantrenLembagaAdapter adapter = new PesantrenLembagaAdapter(getContext(),lsLembaga);
            lvMadrasah.setAdapter(adapter);
        }else{
            lvMadrasah.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        lvMadrasah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lembaga madrasah = lsLembaga.get(position);
                Intent intent = new Intent(getActivity(), MadrasahActivity.class);
                intent.putExtra("madrasah", Parcels.wrap(madrasah));
                startActivity(intent);
            }
        });
        return view;
    }

}
