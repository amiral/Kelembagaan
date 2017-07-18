package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseKoreksi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoreksiActivity extends AppCompatActivity {

    @BindView(R.id.button_tidak_akurat)
    Button btnTidakAkurat;

    @BindView(R.id.button_duplikat)
    Button btnDuplikat;

    @BindView(R.id.edit_koreksi)
    EditText etKoreksi;

    @BindView(R.id.text_hiden_koreksi)
    TextView tvHidden;

    //untuk cek jeniskoreksi 0:Tidak akurat, 1 : duplikat
    int jenisKoreksi;

    //untuk cek koreksi pesantren atau lembaga (0 / 1)
    int tipeKoreksi;
    String nama;
    int id, idKabupaten, idProvinsi;

    Pesantren pesantren;
    Lembaga lembaga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koreksi);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tipeKoreksi = getIntent().getIntExtra("tipe", 0);
        nama = getIntent().getStringExtra("nama");
        id = getIntent().getIntExtra("id", 0);
        idKabupaten = getIntent().getIntExtra("idKabupaten", 0);


        String title = tipeKoreksi == 0 ? "Koreksi Pesantren" : "Koreksi Madrasah";
        getSupportActionBar().setTitle(title);
        tvHidden.setText(getString(R.string.koreksi_hiden, nama));

        etKoreksi.addTextChangedListener(new TextWatcher() {
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

        onTidakAkurat();
    }

    @OnClick(R.id.button_tidak_akurat)
    public void onTidakAkurat(){
        jenisKoreksi = 0;
        btnTidakAkurat.setSelected(true);
        btnDuplikat.setSelected(false);
    }

    @OnClick(R.id.button_duplikat)
    public void onDuplikat(){
        jenisKoreksi = 1;
        btnTidakAkurat.setSelected(false);
        btnDuplikat.setSelected(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public void onBatal(View v){
       finish();
    }

    @OnClick(R.id.button_kirim)
    public void onKirim(){
        String pesan = etKoreksi.getText().toString();

        if (pesan.isEmpty()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.koreksi_hiden, nama));
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses Koreksi");
        progressDialog.setTitle("Memproses...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        int idPengguna = 0;
        PreferenceManager pref = new PreferenceManager(KoreksiActivity.this);

        if (pref.isLogin()){
            idPengguna = pref.getPengguna().getIdPengguna();
        }

        if (tipeKoreksi == 0){
            Call<PostResponseKoreksi> call = apiService
                    .postKoreksiPesantren(ApiServerURL.TOKEN_PUBLIC,idPengguna,
                            id, idKabupaten, jenisKoreksi, pesan);
            call.enqueue(new Callback<PostResponseKoreksi>() {
                @Override
                public void onResponse(Call<PostResponseKoreksi>call, Response<PostResponseKoreksi> response) {
                    progressDialog.dismiss();
                    if (response != null && response.body() != null){
                        if (response.body().getError()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
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
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            finish();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
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
                    .postKoreksiLembaga(ApiServerURL.TOKEN_PUBLIC,idPengguna,
                            id, idKabupaten, jenisKoreksi, pesan);
            call.enqueue(new Callback<PostResponseKoreksi>() {
                @Override
                public void onResponse(Call<PostResponseKoreksi>call, Response<PostResponseKoreksi> response) {
                    progressDialog.dismiss();
                    if (response != null && response.body() != null ){
                        if (response.body().getError()){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
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
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
                            alertDialogBuilder.setMessage(response.body().getPesan());
                            alertDialogBuilder.setPositiveButton("OKE",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            finish();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KoreksiActivity.this);
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
        }

    }
}
