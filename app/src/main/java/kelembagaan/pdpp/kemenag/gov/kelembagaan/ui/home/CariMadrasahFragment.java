package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter.CariMadrasahAdapter;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah.MadrasahActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariMadrasahFragment extends Fragment implements CariMadrasahAdapter.ItemClickListener{

    @BindView(R.id.recyclerViewMadrasah) RecyclerView recyclerView;

    private CariMadrasahAdapter adapterMadrasah;
    private List<Lembaga> listMadrasah;

    public CariMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cari_madrasah, container, false);
        ButterKnife.bind(this, view);

        listMadrasah = new ArrayList<>();
        adapterMadrasah = new CariMadrasahAdapter(view.getContext(), listMadrasah);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterMadrasah);


        adapterMadrasah.setClickListener(this);
        dataSampleMadrasah();

        return view;
    }

    private void dataSampleMadrasah() {

        Lembaga l1 = new Lembaga();
        l1.setNamaLembaga("Madrasah Surabaya");
        l1.setNpsn("Pesantren 1");
        l1.setNsm("123456789");
        l1.setLokasiLembaga("Surabaya, Jawa Timur");
        listMadrasah.add(l1);

        Lembaga l2 = new Lembaga();
        l2.setNamaLembaga("Madrasah Medan");
        l2.setNpsn("Pesantren 2");
        l2.setNsm("12345678");
        l2.setLokasiLembaga("Medan, Sumut");
        listMadrasah.add(l2);

        Lembaga l3 = new Lembaga();
        l3.setNamaLembaga("Madrasah Padang");
        l3.setNpsn("Pesantren 3");
        l3.setNsm("1234567");
        l3.setLokasiLembaga("Padang, Sumbar");
        listMadrasah.add(l3);

        Lembaga l4 = new Lembaga();
        l4.setNamaLembaga("Madrasah Lampung");
        l4.setNpsn("Pesantren 4");
        l4.setNsm("123456789");
        l4.setLokasiLembaga("Metro, Lampung");
        listMadrasah.add(l4);

        Lembaga l5 = new Lembaga();
        l5.setNamaLembaga("Madrasah Bali");
        l5.setNpsn("Pesantren 5");
        l5.setNsm("123456789");
        l5.setLokasiLembaga("Kuta, Bali");
        listMadrasah.add(l5);

        Lembaga l6 = new Lembaga();
        l6.setNamaLembaga("Madrasah Irian");
        l6.setNpsn("Pesantren 6");
        l6.setNsm("123456789");
        l6.setLokasiLembaga("Jayapura, Papua Barat");
        listMadrasah.add(l6);
    }

    @Override
    public void onClick(View view, int position) {
        Lembaga madrasah = listMadrasah.get(position);

        Intent intent = new Intent(getActivity(), MadrasahActivity.class);
        intent.putExtra("madrasah", Parcels.wrap(madrasah));
        startActivity(intent);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
