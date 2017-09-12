package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseKoreksi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//    public void onResume(){
//        super.onResume();
//        // Set title bar
//        ((MainActivity) getActivity())
//                .setActionBarTitle(getString(R.string.nav_lapor));
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.nav_lapor);
        View view = inflater.inflate(R.layout.fragment_lapor, container, false);

        ButterKnife.bind(this, view);

//        ((MainActivity) getActivity())
//                .setActionBarTitle(getString(R.string.nav_lapor));

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
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_kirim_lapor)
    public void onLaporKirim() {
        String pesan = etLapor.getText().toString();

        if (pesan.isEmpty()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            int msg = tipeLaporan == 0 ? R.string.hideen_pesantren : R.string.hidden_madrasah;
            alertDialogBuilder.setMessage(getString(msg));
            alertDialogBuilder.setPositiveButton("OKE",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            submitProcess(pesan);
        }
    }

    public void submitProcess(String pesan){


        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitForgot";

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses Laporan");
        progressDialog.setTitle("Memproses...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        int idPengguna = 0;
        int idHakAkses = 0 ;
        PreferenceManager pref = new PreferenceManager(getActivity());

        if (pref.isLogin()){
            idPengguna = pref.getPengguna().getIdPengguna();
            idHakAkses = pref.getPengguna().getHakAksesId();
        }

        if (tipeLaporan == 0){
            Call<PostResponseKoreksi> call = apiService
                    .postLaporPesantren(ApiServerURL.TOKEN_PUBLIC,idPengguna,
                            idHakAkses, idKabupaten, pesan);
            call.enqueue(new Callback<PostResponseKoreksi>() {
                @Override
                public void onResponse(Call<PostResponseKoreksi>call, Response<PostResponseKoreksi> response) {
                    progressDialog.dismiss();
                    if (response != null && response.body() != null){
                        if (response.body().getError()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            etLapor.setText("");
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Gangguan Server!");
                        alertDialogBuilder.setPositiveButton("OKE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }

                @Override
                public void onFailure(Call<PostResponseKoreksi>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                }
            });
        }else{
            Call<PostResponseKoreksi> call = apiService
                    .postLaporLembaga(ApiServerURL.TOKEN_PUBLIC,idPengguna,
                            idHakAkses, idKabupaten, pesan);
            call.enqueue(new Callback<PostResponseKoreksi>() {
                @Override
                public void onResponse(Call<PostResponseKoreksi>call, Response<PostResponseKoreksi> response) {
                    progressDialog.dismiss();
                    if (response != null && response.body() != null ){
                        if (response.body().getError()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else{
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            etLapor.setText("");
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Gangguan Server!");
                        alertDialogBuilder.setPositiveButton("OKE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }

                @Override
                public void onFailure(Call<PostResponseKoreksi> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                }
            });
        }

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
