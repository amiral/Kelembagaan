package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.utils.GlobalData;

public class FilterSearchActivity extends AppCompatActivity {

    @BindView(R.id.text_filter_title)
    TextView title;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.lytSpinner)
    FrameLayout lytSpinner;

    String[] judul = {"FILTER WILAYAH", "FILTER TIPE LEMBAGA","FILTER JENJANG"};

    int TIPE_FILTER;
    int from; // 0: Lembaga , 1 : Pesantren
    PreferenceManager pref;
    ArrayList<Integer> idFilterKabupaten;
    ArrayList<Integer> idFilterTipe;
    ArrayList<Integer> idFilterJenjang;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        ButterKnife.bind(this);
        mContext = this;

        pref = new PreferenceManager(this);
        TIPE_FILTER = getIntent().getIntExtra("TIPEFILTER", 0);
        from = getIntent().getIntExtra("from", 0);
        title.setText(judul[TIPE_FILTER]);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if (TIPE_FILTER == 1){
            initViewFilterTipe(false);
        }else if (TIPE_FILTER == 2){
            initViewFilterJenjang(false);
        }else{
            initViewFilterWilayah(false);
        }
    }

    public void initViewFilterWilayah(boolean isReset){
        lytSpinner.setVisibility(View.VISIBLE);


        if (from == 1)
            idFilterKabupaten = isReset ? new ArrayList<Integer>() : pref.getFilterKabupatenPesantren();
        else
            idFilterKabupaten = isReset ? new ArrayList<Integer>() : pref.getFilterKabupaten();

        final ArrayList<Provinsi> lsProvinsi = new ProvinsiDbHelper(mContext).findAllProvinsi();
        ArrayList<String> name = new ArrayList();
        Iterator it = lsProvinsi.iterator();
        while (it.hasNext()) {
            name.add(((Provinsi) it.next()).getNamaProvinsi());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, name.toArray(new String[name.size()]));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lsProvinsi.size() > 0){
                    ArrayList<Kabupaten> lsKabupaten = new KabupatenDbHelper(mContext).findAllProvinsiKabupaten(lsProvinsi.get(position).getIdProvinsi());
                    FilterWilayahAdapter adapter = new FilterWilayahAdapter(lsKabupaten);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (lsProvinsi.size() > 0){
            ArrayList<Kabupaten> lsKabupaten = new KabupatenDbHelper(mContext).findAllProvinsiKabupaten(lsProvinsi.get(0).getIdProvinsi());
            FilterWilayahAdapter adapter = new FilterWilayahAdapter(lsKabupaten);
            recyclerView.setAdapter(adapter);
        }


    }

    public void initViewFilterTipe(boolean isReset){

        if (from == 1)
            idFilterTipe = isReset ? new ArrayList<Integer>() : pref.getFilterLembagaPesantren();
        else
            idFilterTipe = isReset ? new ArrayList<Integer>() : pref.getFilterLembaga();
        recyclerView.setAdapter(new FilterTipeAdapter());
    }

    public void initViewFilterJenjang(boolean isReset){
        if(from == 1)
            idFilterJenjang =isReset ? new ArrayList<Integer>() :  pref.getFilterJenjangPesantren();
        else
            idFilterJenjang =isReset ? new ArrayList<Integer>() :  pref.getFilterJenjang();

        recyclerView.setAdapter(new FilterJenjangAdapter());
    }


    public void onClose(View v){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void onReset(View v){
        if (TIPE_FILTER == 1){
//            idFilterTipe = new ArrayList<Integer>();
//            pref.setFilterLembaga(idFilterTipe);
            initViewFilterTipe(true);
        }else if (TIPE_FILTER == 2){
//            idFilterJenjang = new ArrayList<Integer>();
//            pref.setFilterJenjang(idFilterJenjang);
            initViewFilterJenjang(true);
        }else{
//            idFilterKabupaten = new ArrayList<Integer>();
//            pref.setFilterKabupaten(idFilterKabupaten);
            initViewFilterWilayah(true);
        }
    }

    public void onTerapkan(View v){
        if (TIPE_FILTER == 1){
            if (from == 1)
                pref.setFilterLembagaPesantren(idFilterTipe);
            else
                pref.setFilterLembaga(idFilterTipe);
        }else if (TIPE_FILTER == 2){
            if (from == 1)
                pref.setFilterJenjangPesantren(idFilterJenjang);
            else
                pref.setFilterJenjang(idFilterJenjang);

        }else{
            if (from == 1)
                pref.setFilterKabupatenPesantren(idFilterKabupaten);
            else
                pref.setFilterKabupaten(idFilterKabupaten);
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    private class FilterWilayahAdapter extends RecyclerView.Adapter<FilterViewHolder> {
        private List<Kabupaten> kabupatens;

        FilterWilayahAdapter(List<Kabupaten> kabupatens) {
            this.kabupatens = kabupatens;
        }

        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false));
        }


        public void onBindViewHolder(final FilterViewHolder holder, int position) {
            try {
                final Kabupaten kabupaten = (Kabupaten) this.kabupatens.get(position);
                holder.checkBox.setText(kabupaten.getNamaKabupaten());
                holder.checkBox.setOnCheckedChangeListener(null);
                boolean isSelected = false;
                if (idFilterKabupaten.size() > 0){
                    isSelected = idFilterKabupaten.contains(kabupaten.getIdKabupaten());
                }
                holder.checkBox.setChecked(isSelected);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            idFilterKabupaten.add(kabupaten.getIdKabupaten());
                        }else{
                            idFilterKabupaten.remove(new Integer(kabupaten.getIdKabupaten()));
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public int getItemCount() {
            return this.kabupatens != null ? this.kabupatens.size() : 0;
        }
    }

    private class FilterTipeAdapter extends RecyclerView.Adapter<FilterViewHolder> {

        String [] data;
        FilterTipeAdapter() {
            data = GlobalData.NAMATIPELEMBAGA;
        }

        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false));
        }


        public void onBindViewHolder(final FilterViewHolder holder, final int position) {
            try {
                String tipe = data[position];
                holder.checkBox.setText(tipe);
                holder.checkBox.setOnCheckedChangeListener(null);
                boolean isSelected = false;
                if (idFilterTipe.size() > 0){
                    isSelected = idFilterTipe.contains(position+1);
                }
                holder.checkBox.setChecked(isSelected);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            idFilterTipe.add(position+1);
                        }else{
                            idFilterTipe.remove(new Integer(position+1));
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public int getItemCount() {
            return data.length ;
        }
    }

    private class FilterJenjangAdapter extends RecyclerView.Adapter<FilterViewHolder> {

        String [] data;
        FilterJenjangAdapter() {
            data = GlobalData.NAMA_JENJANG;
        }

        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false));
        }


        public void onBindViewHolder(final FilterViewHolder holder, final int position) {
            try {
                String jenjang = data[position];
                holder.checkBox.setText(jenjang);
                holder.checkBox.setOnCheckedChangeListener(null);
                boolean isSelected = false;
                if (idFilterJenjang.size() > 0){
                    isSelected = idFilterJenjang.contains(position);
                }
                holder.checkBox.setChecked(isSelected);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            idFilterJenjang.add(position);
                        }else{
                            idFilterJenjang.remove(new Integer(position));
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public int getItemCount() {
            return data.length ;
        }
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public FilterViewHolder(View itemView) {
            super(itemView);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
