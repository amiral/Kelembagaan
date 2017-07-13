package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

public class LaporFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.button_lapor_pesantren)
    Button btnPesantren;

    @BindView(R.id.button_lapor_madrasah)
    Button btnMadrasah;

    @BindView(R.id.spinner_provinsi)
    Spinner spnProvinsi;

    @BindView(R.id.spinner_kabupaten)
    Spinner spnKabupaten;

    @BindView(R.id.edit_lapor)
    EditText etLapor;

    @BindView(R.id.text_hiden_lapor)
    TextView tvHidden;

    int tipeLaporan;

    ArrayList<Kabupaten> lsKabupaten;

    int idProvinsi = 0;
    int idKabupaten;

    public LaporFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lapor, container, false);

        ButterKnife.bind(this, view);

        onProvinsi();

        final ArrayList<Provinsi> lsProvinsi = new ProvinsiDbHelper(getContext()).findAllProvinsi();
        ArrayList<String> name = new ArrayList();
        Iterator it = lsProvinsi.iterator();
        while (it.hasNext()) {
            name.add(((Provinsi) it.next()).getNamaProvinsi());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, name.toArray(new String[name.size()]));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProvinsi.setAdapter(spinnerAdapter);
        spnProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lsProvinsi.size() > 0) {
                    idProvinsi = lsProvinsi.get(position).getIdProvinsi();
                    lsKabupaten = new KabupatenDbHelper(getContext()).findAllProvinsiKabupaten(idProvinsi);
                    ArrayList<String> name = new ArrayList();
                    Iterator it = lsKabupaten.iterator();
                    while (it.hasNext()) {
                        name.add(((Kabupaten) it.next()).getNamaKabupaten());
                    }
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, name.toArray(new String[name.size()]));
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnKabupaten.setAdapter(spinnerAdapter);
                    spnKabupaten.setOnItemSelectedListener(LaporFragment.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (lsProvinsi.size() > 0) {
            lsKabupaten = new KabupatenDbHelper(getContext()).findAllProvinsiKabupaten(lsProvinsi.get(0).getIdProvinsi());
            ArrayList<String> namaKabupaten = new ArrayList();
            Iterator itK = lsKabupaten.iterator();
            while (itK.hasNext()) {
                namaKabupaten.add(((Kabupaten) itK.next()).getNamaKabupaten());
            }
            ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, namaKabupaten.toArray(new String[namaKabupaten.size()]));
            spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnKabupaten.setAdapter(spnAdapter);
            spnKabupaten.setOnItemSelectedListener(LaporFragment.this);
        }

        etLapor.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    tvHidden.setVisibility(View.VISIBLE);
                } else {
                    tvHidden.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }


    @OnClick(R.id.button_lapor_pesantren)
    public void onProvinsi() {
        tipeLaporan = 0;
        btnPesantren.setSelected(true);
        btnMadrasah.setSelected(false);
        tvHidden.setText(R.string.hideen_pesantren);
    }

    @OnClick(R.id.button_lapor_madrasah)
    public void onMadrasah() {
        tipeLaporan = 1;
        btnPesantren.setSelected(false);
        btnMadrasah.setSelected(true);
        tvHidden.setText(R.string.hidden_madrasah);
    }

    @OnClick(R.id.button_batal_lapor)
    public void onLaporBatal() {

    }

    @OnClick(R.id.button_kirim_lapor)
    public void onLaporKirim() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        idKabupaten = lsKabupaten.get(position).getIdKabupaten();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        idKabupaten = lsKabupaten.get(0).getIdKabupaten();
    }
}
