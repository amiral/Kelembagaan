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

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren.PesantrenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkPesantrenFragment extends Fragment {

    ListView lvPesantren;
    TextView tvNodata;

    public BookmarkPesantrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bookmark_pesantren, container, false);

        lvPesantren = (ListView) view.findViewById(R.id.list_pesantren);
        tvNodata = (TextView) view.findViewById(R.id.text_no_data);

        PesantrenDbHelper helper = new PesantrenDbHelper(getContext());
        final List<Pesantren> lsBookmark = helper.getAllBookmarkPesantren();

        if (lsBookmark.size() > 0){
            tvNodata.setVisibility(View.GONE);
            lvPesantren.setVisibility(View.VISIBLE);

            PesantrenBookmarkAdapter adapter = new PesantrenBookmarkAdapter(getContext(),lsBookmark);
            lvPesantren.setAdapter(adapter);
        }else{
            lvPesantren.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        lvPesantren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pesantren pesantren = lsBookmark.get(position);
                Intent intent = new Intent(getActivity(), PesantrenActivity.class);
                intent.putExtra("idPesantren", pesantren.getIdPesantren());
                startActivity(intent);
            }
        });
        return view;
    }

}
