package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.cuboid.cuboidcirclebutton.CuboidButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;

public class IzinOperationalActivity extends AppCompatActivity {

    @BindView(R.id.circle_1minggu)
    CuboidButton c1Minggu;

    @BindView(R.id.circle_1bulan)
    CuboidButton c1Bulan;

    @BindView(R.id.circle_2bulan)
    CuboidButton c2Bulan;

    @BindView(R.id.list_madrasah)
    ListView lvMadrasah;

    LembagaDbHelper dbHelper ;
    ArrayList<Lembaga> minggu, bulan, bulan2;

    IzinLembagaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_izin_operational);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.izin_operasional);

        ButterKnife.bind(this);

        dbHelper = new LembagaDbHelper(this);
        minggu = dbHelper.getAllWarningLembaga(7);
        bulan = dbHelper.getAllWarningLembaga(30);
        bulan2 = dbHelper.getAllWarningLembaga(60);

        c1Minggu.setText(""+minggu.size());
        c1Bulan.setText(""+bulan.size());
        c2Bulan.setText(""+bulan2.size());

        adapter = new IzinLembagaAdapter(this,minggu);
        lvMadrasah.setAdapter(adapter);
    }

    public void onViewData(View view){
        if (view.getId() == R.id.circle_1minggu){
            adapter = new IzinLembagaAdapter(this,minggu);
            lvMadrasah.setAdapter(adapter);
        }else if (view.getId() == R.id.circle_1bulan){
            adapter = new IzinLembagaAdapter(this,bulan);
            lvMadrasah.setAdapter(adapter);
        }else{
            adapter = new IzinLembagaAdapter(this,bulan2);
            lvMadrasah.setAdapter(adapter);
        }
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
