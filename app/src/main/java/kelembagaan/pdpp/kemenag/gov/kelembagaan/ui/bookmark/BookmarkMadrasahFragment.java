package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.bookmark;


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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah.MadrasahActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkMadrasahFragment extends Fragment {

    ListView lvMadrasah;
    TextView tvNodata;

    public BookmarkMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bookmark_madrasah, container, false);

        lvMadrasah = (ListView) view.findViewById(R.id.list_madrasah);
        tvNodata = (TextView) view.findViewById(R.id.text_no_data);

        LembagaDbHelper helper = new LembagaDbHelper(getContext());
        final List<Lembaga> lsBookmark = helper.getAllBookmarkLembaga();

        if (lsBookmark.size() > 0){
            tvNodata.setVisibility(View.GONE);
            lvMadrasah.setVisibility(View.VISIBLE);

            MadrasahBookmarkAdapter adapter = new MadrasahBookmarkAdapter(getContext(),lsBookmark);
            lvMadrasah.setAdapter(adapter);
        }else{
            lvMadrasah.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        lvMadrasah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lembaga madrasah = lsBookmark.get(position);
                Intent intent = new Intent(getActivity(), MadrasahActivity.class);
                intent.putExtra("madrasah", Parcels.wrap(madrasah));
                startActivity(intent);
            }
        });

        return view;
    }

}
