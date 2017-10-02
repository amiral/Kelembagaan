package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sccomponents.gauges.ScArcGauge;
import com.sccomponents.gauges.ScCopier;
import com.sccomponents.gauges.ScFeature;
import com.sccomponents.gauges.ScGauge;
import com.sccomponents.gauges.ScNotches;
import com.sccomponents.gauges.ScPointer;
import com.sccomponents.gauges.ScWriter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LaporanLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLaporanLembaga;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Act an instance of this fragment.
 */
public class DashboardFragment extends Fragment {


    @BindView(R.id.text_madrasah_expired)
    TextView tvExpired;

    @BindView(R.id.text_percetage)
    TextView tvPercentage;
    @BindView(R.id.text_percetage_status)
    TextView tvStatus;
    PreferenceManager prefManager;
    View view;

    ScArcGauge gauge;
    ImageView indicator;
    public DashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.nav_dashboard);
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this, view);

        gauge = (ScArcGauge) view.findViewById(R.id.gauge);
        indicator = (ImageView) view.findViewById(R.id.indicator);
        prefManager = new PreferenceManager(getContext());
        onSyncronGeLaporanLembaga();
        return view;
    }

    public void initView(){

        setGauge(50);

        //izin operasional
        LembagaDbHelper helper = new LembagaDbHelper(getActivity());
        tvExpired.setText(""+helper.getAllWarningLembaga(60).size());
    }

    public void onSyncronGeLaporanLembaga() {

        boolean isLogin = prefManager.isLogin();
        if (isLogin){
            String lastUpdate = prefManager.getLastUpdateLaporanLembaga();
            String token = prefManager.getPengguna().getApiToken();

            ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

            final String TAG = "Retrofit";

            Call<GetResponseLaporanLembaga> call = apiService.getLaporanLembaga(token,lastUpdate);

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Mengambil data Laporan...");
            progressDialog.setTitle("Sinkronisasi Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDialog.show();
            call.enqueue(new Callback<GetResponseLaporanLembaga>() {
                @Override
                public void onResponse(Call<GetResponseLaporanLembaga>call, Response<GetResponseLaporanLembaga> response) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (response != null && response.body().getData() != null){
                        List<Laporan> laporans = response.body().getData();
                        String lastSync = response.body().getTanggal().getDate();
                        prefManager.setLastUpdateLaporanLembaga(lastSync);

                        if (laporans.size()> 0){
                            LaporanLembagaDbHelper helper = new LaporanLembagaDbHelper(getContext());
                            helper.addManyLaporan(laporans);

                        }else{
                            Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
                        }
                        initView();
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage(response.body().toString());
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
                public void onFailure(Call<GetResponseLaporanLembaga>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                }
            });
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("Silahkan Login!");
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

    private void setGauge(int value){

        assert gauge != null;
        assert indicator != null;



        tvPercentage.setText(""+value+" %");

        String status = getStatus(value);
        tvStatus.setText(status);

        // Set the center pivot for a right rotation
        indicator.setPivotX(39);
        indicator.setPivotY(88);

        // Set the values.
        gauge.setHighValue(value);

        // Set the filter of the base
        ScFeature base = gauge.findFeature(ScGauge.BASE_IDENTIFIER);
        BlurMaskFilter filter = new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER);
        base.getPainter().setMaskFilter(filter);

       /* // Writer
        String[] tokens = new String[10];
        for (int index = 0; index < 10; index++) {
            tokens[index] = Integer.toString((index + 1) * 10);
        }
        gauge.setTextTokens(tokens);

        ScWriter writer = (ScWriter) gauge.findFeature(ScGauge.WRITER_IDENTIFIER);
        writer.setTokenOffset(0.0f, -40.0f);*/

        // Each time I will change the value I must write it inside the counter text.
        gauge.setOnEventListener(new ScGauge.OnEventListener() {
            @Override
            public void onValueChange(float lowValue, float highValue) {
                // Convert the percentage value in an angle
                float angle = gauge.percentageToAngle(highValue);
                indicator.setRotation(angle);
            }
        });

        gauge.setOnDrawListener(new ScGauge.OnDrawListener() {
            @Override
            public void onBeforeDrawCopy(ScCopier.CopyInfo info) {
                // Do nothing
            }

            @Override
            public void onBeforeDrawNotch(ScNotches.NotchInfo info) {
                // Hide the first and the last
                info.visible = info.index > 0 && info.index < info.source.getCount();
            }

            @Override
            public void onBeforeDrawPointer(ScPointer.PointerInfo info) {
                // Do nothing
            }

            @Override
            public void onBeforeDrawToken(ScWriter.TokenInfo info) {
                // Highlight
                int sector = (int) (gauge.getHighValue() / 10);
                info.color = sector == info.index ? Color.BLACK : Color.parseColor("#cccccc");
            }
        });


    }

    /*merah = benarnya: 1-24% buruk
        orange = benarnya:25-49% cukup buruk
                kuning = benarnya 50-74% cukup
        hijau = benarnya 75-99% cukup baik
                biru = benarnya 100% sempurna
*/
    private String getStatus(int value){
        String status;

        if (value == 0){
            status = "Sangat Buruk";
        }else if (value >= 1 && value < 25){
            status = "Buruk";
        }else if (value >= 25 && value < 50){
            status = "Cukup Buruk";
        }else if (value >= 50 && value < 75){
            status = "Cukup";
        }else if (value >= 75 && value < 100){
            status = "Cukup Baik";
        }else{
            status = "Sempurna";
        }


        return status;
    }

    @OnClick(R.id.text_index_selengkapnya)
    public void onIndex(View view){
        startActivity(new Intent(getActivity(), IndeksIntegritasActivity.class));
    }

    @OnClick(R.id.text_izin_selengkapnya)
    public void onIzin(View view){
        startActivity(new Intent(getActivity(), IzinOperationalActivity.class));
    }
}
