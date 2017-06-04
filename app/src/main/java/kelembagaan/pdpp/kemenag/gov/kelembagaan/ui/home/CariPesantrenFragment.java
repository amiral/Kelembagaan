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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter.CariPesantrenAdapter;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren.PesantrenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariPesantrenFragment extends Fragment implements CariPesantrenAdapter.ItemClickListener {

    @BindView(R.id.recyclerViewPesantren) RecyclerView recyclerView;

    private CariPesantrenAdapter adapterPesantren;
    private List<Pesantren> listPesantren;

    public CariPesantrenFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cari_pesantren, container, false);
        ButterKnife.bind(this, view);

        listPesantren = new ArrayList<>();
        adapterPesantren = new CariPesantrenAdapter(view.getContext(), listPesantren);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterPesantren);

        adapterPesantren.setClickListener(this);
        dataSamplePesantren();



        return view;
    }

    private void dataSamplePesantren(){

//        holder.tvNamaPesantren.setText(pesantren.getNamaPesantren());
//        holder.tvPimpinanPesantren.setText(pesantren.getPimpinan());
//        holder.tvLokasiPesantren.setText(pesantren.getLokasiPesantren());
//        holder.tvNspp.setText(pesantren.getNspp());

        Pesantren p1 = new Pesantren();
        p1.setNamaPesantren("Pesantren Kudus");
        p1.setPimpinan("K.H Kudus Wangi");
        p1.setLokasiPesantren("Kudus, Jawa Timur");
        p1.setNspp("12345678");
        listPesantren.add(p1);

        Pesantren p2 = new Pesantren();
        p2.setNamaPesantren("Pesantren Tebu Ireng");
        p2.setPimpinan("K.H Tebu Ireng Wangi");
        p2.setLokasiPesantren("Tebu Ireng, Jawa Tengah");
        p2.setNspp("1234567");
        listPesantren.add(p2);

        Pesantren p3 = new Pesantren();
        p3.setNamaPesantren("Pesantren Cieubeureum");
        p3.setPimpinan("K.H Cieubeureum Wangi");
        p3.setLokasiPesantren("Cieubeureum, Jawa Barat");
        p3.setNspp("123456");
        listPesantren.add(p3);

        Pesantren p4 = new Pesantren();
        p4.setNamaPesantren("Pesantren Kunciran");
        p4.setPimpinan("K.H Kunciran Wangi");
        p4.setLokasiPesantren("Kunciran, Jakarta");
        p4.setNspp("12345");
        listPesantren.add(p4);

        Pesantren p5 = new Pesantren();
        p5.setNamaPesantren("Pesantren Badui");
        p5.setPimpinan("K.H Badui Wangi");
        p5.setLokasiPesantren("Badui, Banten");
        p5.setNspp("1234");
        listPesantren.add(p5);

        adapterPesantren.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {

        Pesantren pesantren = listPesantren.get(position);

        Intent intent = new Intent(getActivity(), PesantrenActivity.class);
        intent.putExtra("pesantren", Parcels.wrap(pesantren));
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
