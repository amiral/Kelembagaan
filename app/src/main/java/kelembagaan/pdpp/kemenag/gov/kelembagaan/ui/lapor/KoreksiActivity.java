package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koreksi);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tipeKoreksi = getIntent().getIntExtra("tipe", 0);
        nama = getIntent().getStringExtra("nama");

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

    }
}
