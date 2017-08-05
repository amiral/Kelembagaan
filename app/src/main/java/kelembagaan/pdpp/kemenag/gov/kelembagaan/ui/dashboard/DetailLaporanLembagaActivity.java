package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LaporanLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;

public class DetailLaporanLembagaActivity extends AppCompatActivity {


    @BindView(R.id.text_nama_madrasah)
    TextView tvNama;

    @BindView(R.id.list_laporan_madrasah)
    ListView lvLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_lembaga);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        String nama = getIntent().getStringExtra("nama");
        int idLembaga = getIntent().getIntExtra("idLembaga", 0);
        int tipe = getIntent().getIntExtra("tipe", 0);  //0 tidak akurat, 1 duplikat

        tvNama.setText(nama);

        List<Laporan> lsLaporan;

        if (tipe == 0)
         lsLaporan = new LaporanLembagaDbHelper(this).getTidakAkuratLaporanLembaga(idLembaga);
        else
            lsLaporan = new LaporanLembagaDbHelper(this).getDuplikatLaporanLembaga(idLembaga);

        LaporanMissingAdapter adapter = new LaporanMissingAdapter(this, lsLaporan);
        lvLaporan.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            if (parentIntent == null) {
                finish();
                return true;
            } else {
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
