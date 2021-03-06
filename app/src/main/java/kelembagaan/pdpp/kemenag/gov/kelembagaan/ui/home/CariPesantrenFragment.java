package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter.CariPesantrenAdapter;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren.PesantrenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariPesantrenFragment extends Fragment implements CariPesantrenAdapter.ItemClickListener, View.OnClickListener {

    @BindView(R.id.recyclerViewPesantren) RecyclerView recyclerView;

    @BindView(R.id.text_no_data)
    TextView tvNodata;

    @BindView(R.id.bottom_jenjang)
    LinearLayout btnJenjang;

    @BindView(R.id.bottom_wilayah)
    LinearLayout btnWilayah;

    @BindView(R.id.bottom_tipe)
    LinearLayout btnTipe;

//    @BindView(R.id.bottomBar)
//    BottomNavigationView bottomBar;

    SearchView searchView;
    int FILTER_REQUEST = 111;
    int TIPE_FILTER;

    private CariPesantrenAdapter adapterPesantren;
    private List<Pesantren> listPesantren;

    String searchText="";

    List<Integer> lsWilayah ;
    List<Integer> lsTipe ;
    List<Integer> lsJenjang ;

    PreferenceManager pref;
    public CariPesantrenFragment() {
        // Required empty public constructor

    }

    PesantrenDbHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.nav_cari);
        View view = inflater.inflate(R.layout.fragment_cari_pesantren, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        pref = new PreferenceManager(getContext());
        pref.removeSearchValuePesantren();

        lsWilayah = new ArrayList<Integer>();
        lsTipe = new ArrayList<Integer>();
        lsJenjang = new ArrayList<Integer>();

        helper = new PesantrenDbHelper(getContext());
        listPesantren = new ArrayList<Pesantren>();

        getDataPesantren();

        if (listPesantren.size() > 0) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterPesantren);
        }else{
            recyclerView.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        btnWilayah.setOnClickListener(this);
        btnTipe.setOnClickListener(this);
        btnJenjang.setOnClickListener(this);

//        dataSamplePesantren();
//        handleBottomBar();
//

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_cari, menu);
        try {
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            searchView =
                    (SearchView) menu.findItem(R.id.action_search).getActionView();

            String searchHint = "NSPP, Nama, Provinsi, Kabupaten";
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint(searchHint);
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    // do your search
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    // do your search on change or save the last string or...
                    searchText = s ;
//                    if (searchText.isEmpty()){
//                        listPesantren = helper.getAllPesantren();
//                    }else{
//                        listPesantren = helper.getAllSearchPesantren(s);
//                    }

//                    Log.i("Cari", "Query : "+s + " listSize : "+listPesantren.size());
//                    adapterPesantren = new CariPesantrenAdapter(getContext(), listPesantren);
//                    recyclerView.setAdapter(adapterPesantren);
//                    adapterPesantren.setClickListener(CariPesantrenFragment.this);

                    lsWilayah = pref.getFilterKabupatenPesantren();
                    lsTipe = pref.getFilterLembagaPesantren();
                    lsJenjang = pref.getFilterJenjangPesantren();
                    getDataFilter();
                    return false;
                }
            });

            /*// Define the listener
            MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when action item collapses
                    bottomBar.setVisibility(View.GONE);
                    return true;  // Return true to collapse action view
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    bottomBar.setVisibility(View.VISIBLE);
                    handleBottomBar();
                    return true;  // Return true to expand action view
                }
            };*/

            // Get the MenuItem for the action item
            MenuItem actionMenuItem = menu.findItem(R.id.action_search);

            // Assign the listener to that action item
//            MenuItemCompat.setOnActionExpandListener(actionMenuItem, expandListener);

        }catch(Exception e){e.printStackTrace();
        }
    }

    private  void getDataPesantren(){
        listPesantren = helper.getAllPesantren();
        adapterPesantren = new CariPesantrenAdapter(getContext(), listPesantren);
        adapterPesantren.setClickListener(this);
//        adapterPesantren.notifyDataSetChanged();
    }



    /*public void handleBottomBar(){
        bottomBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.tab_wilayah){
                            TIPE_FILTER = 0;
                        }else if(item.getItemId() == R.id.tab_tipe){
                            TIPE_FILTER = 1;
                        }else{
                            TIPE_FILTER = 2;
                        }

                        Intent intent = new Intent(getContext(), FilterSearchActivity.class);
//                    intent.putExtra(FilterActivity.FILTER_TYPE, FilterType.JenisKelamin);
//                    intent.putExtra(FilterActivity.FILTER_DATA, this.filterData);
                        intent.putExtra("TIPEFILTER", TIPE_FILTER);
                        intent.putExtra("from", 1);
                        startActivityForResult(intent, FILTER_REQUEST);
                        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
                        return true;
                    }
                });

    }*/

    @Override
    public void onClick(View view, int position) {

        Pesantren pesantren = listPesantren.get(position);
        Intent intent = new Intent(getActivity(), PesantrenActivity.class);
//        intent.putExtra("pesantren", Parcels.wrap(pesantren));
        intent.putExtra("idPesantren", pesantren.getIdPesantren());
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (requestCode == FILTER_REQUEST && resultCode == Activity.RESULT_OK) {
            //some code
            PreferenceManager pref = new PreferenceManager(getContext());
            lsWilayah = pref.getFilterKabupatenPesantren();
            lsTipe = pref.getFilterLembagaPesantren();
            lsJenjang = pref.getFilterJenjangPesantren();

            if (lsWilayah.size() > 0 ){
//                MenuItem item = bottomBar.getMenu().getItem(0);
//                item.setTitle(changetTextColorYellow(item.getTitle().toString()));
                btnWilayah.setSelected(true);
            }else{
//                MenuItem item = bottomBar.getMenu().getItem(0);
//                item.setTitle(changetTextColorWhite(item.getTitle().toString()));
                btnWilayah.setSelected(false);
            }

            if (lsTipe.size() > 0 ){
//                MenuItem item = bottomBar.getMenu().getItem(1);
//                item.setTitle(changetTextColorYellow(item.getTitle().toString()));
                btnTipe.setSelected(true);
            }else{
//                MenuItem item = bottomBar.getMenu().getItem(1);
//                item.setTitle(changetTextColorWhite(item.getTitle().toString()));
                btnTipe.setSelected(false);
            }

            if (lsJenjang.size() > 0 ){
//                MenuItem item = bottomBar.getMenu().getItem(2);
//                item.setTitle(changetTextColorYellow(item.getTitle().toString()));
                btnJenjang.setSelected(true);
            }else{
//                MenuItem item = bottomBar.getMenu().getItem(2);
//                item.setTitle(changetTextColorWhite(item.getTitle().toString()));
                btnJenjang.setSelected(false);
            }

           getDataFilter();

        }
    }

    public void getDataFilter(){
        listPesantren = helper.getAllSearchPesantrenFilter(searchText,lsWilayah,lsTipe, lsJenjang);
        adapterPesantren = new CariPesantrenAdapter(getContext(), listPesantren);
        recyclerView.setAdapter(adapterPesantren);
        adapterPesantren.setClickListener(CariPesantrenFragment.this);
    }

    public SpannableString changetTextColorYellow(String text){
        SpannableString s = new SpannableString(text);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow)), 0, s.length(), 0);

        return s;
    }
    public SpannableString changetTextColorWhite(String text){
        SpannableString s = new SpannableString(text);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, s.length(), 0);

        return s;
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.bottom_wilayah){
            TIPE_FILTER = 0;
        }else if(v.getId() == R.id.bottom_tipe){
            TIPE_FILTER = 1;
        }else{
            TIPE_FILTER = 2;
        }

        Intent intent = new Intent(getContext(), FilterSearchActivity.class);
//                    intent.putExtra(FilterActivity.FILTER_TYPE, FilterType.JenisKelamin);
//                    intent.putExtra(FilterActivity.FILTER_DATA, this.filterData);
        intent.putExtra("TIPEFILTER", TIPE_FILTER);
        intent.putExtra("from", 1);
        startActivityForResult(intent, FILTER_REQUEST);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
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
