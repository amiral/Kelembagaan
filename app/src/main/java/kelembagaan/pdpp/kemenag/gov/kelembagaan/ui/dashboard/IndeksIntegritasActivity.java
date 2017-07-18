package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.content.Context;
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
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LaporanLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;

public class IndeksIntegritasActivity extends AppCompatActivity {

    @BindView(R.id.text_number_belum_ada)
    TextView tvBelumAda;

    @BindView(R.id.text_number_tidak_akurat)
    TextView tvTidakAkurat;

    @BindView(R.id.text_number_duplikat)
    TextView tvDuplikat;

    @BindView(R.id.list_laporan)
    ListView lvLaporan;

    LaporanLembagaDbHelper helper;

    List<Laporan> lsMissing, lsInvalid, lsDuplicate;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.indeks_integritas);
        setContentView(R.layout.activity_indeks_integritas);

        mContext = this;
        ButterKnife.bind(this);

        helper = new LaporanLembagaDbHelper(this);

        lsMissing = helper.getMissingLaporan();
        lsInvalid = helper.getTidakAkuratLaporan();
        lsDuplicate = helper.getDuplikatLaporan();

        tvBelumAda.setText(""+lsMissing.size());
        tvTidakAkurat.setText(""+lsInvalid.size());
        tvDuplikat.setText(""+lsDuplicate.size());


        onMissing();
    }

    @OnClick(R.id.text_number_belum_ada)
    public void onMissing(){
        tvBelumAda.setTextColor(getResources().getColor(R.color.red_light));
        tvTidakAkurat.setTextColor(getResources().getColor(R.color.primary));
        tvDuplikat.setTextColor(getResources().getColor(R.color.primary));

        LaporanMissingAdapter adapter = new LaporanMissingAdapter(mContext, lsMissing);
        lvLaporan.setAdapter(adapter);
    }

    @OnClick(R.id.text_number_tidak_akurat)
    public void onTidakAkurat(){
        tvBelumAda.setTextColor(getResources().getColor(R.color.primary));
        tvTidakAkurat.setTextColor(getResources().getColor(R.color.red_light));
        tvDuplikat.setTextColor(getResources().getColor(R.color.primary));

        LaporanLembagaAdapter adapter = new LaporanLembagaAdapter(mContext, lsInvalid);
        lvLaporan.setAdapter(adapter);
    }

    @OnClick(R.id.text_number_duplikat)
    public void onDuplikat(){
        tvBelumAda.setTextColor(getResources().getColor(R.color.primary));
        tvTidakAkurat.setTextColor(getResources().getColor(R.color.primary));
        tvDuplikat.setTextColor(getResources().getColor(R.color.red_light));

        LaporanLembagaAdapter adapter = new LaporanLembagaAdapter(mContext, lsDuplicate);
        lvLaporan.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                if(parentIntent == null) {
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
