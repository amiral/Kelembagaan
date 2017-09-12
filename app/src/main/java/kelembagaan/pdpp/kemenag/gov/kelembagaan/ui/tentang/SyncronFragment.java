package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.tentang;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LaporanLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatisikLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatistikPesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikPesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLaporanLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponsePesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikPesantren;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyncronFragment extends Fragment {




    @BindView(R.id.lembaga)
    Button btnLembaga;

    /*@BindView(R.id.kabupten)
    Button btnKabupaten;

    @BindView(R.id.provinsi)
    Button btnProvinsi;

     @BindView(R.id.pesantren)
    Button btnPesantren;

    @BindView(R.id.tvLaporLembaga)
            TextView tvLaporanLembaga;

    @BindView(R.id.tvPesantren)
    TextView tvPesantren;*/

    @BindView(R.id.tvLembaga)
    TextView tvLembaga;




    PreferenceManager prefManager;
    ProgressDialog progressDialog;

    Context mContext;
    public SyncronFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_syncron, container, false);
        ButterKnife.bind(this,view);

        prefManager = new PreferenceManager(getContext());
        mContext = getContext();

//        tvPesantren.setText("Last update pesantren : "+pref.getLastUpdatePesantren());
        tvLembaga.setText("Last update lembaga : "+ prefManager.getLastUpdateLembaga());
        return view;
    }


    @OnClick(R.id.lembaga)
    public void onSyncronLembaga() {

        String lastUpdate = prefManager.getLastUpdateLembaga();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseLembaga> call = apiService.getLembaga(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data Lembaga...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponseLembaga>() {
            @Override
            public void onResponse(Call<GetResponseLembaga>call, Response<GetResponseLembaga> response) {
//                progressDialog.dismiss();
                if (response != null){
                    List<Lembaga> lembagas = response.body().getData();
                    String lastSync = response.body().getTanggal().getDate();
                    prefManager.setLastUpdateLembaga(lastSync);

                    if (lembagas.size()> 0){
                        LembagaDbHelper helper = new LembagaDbHelper(mContext);
                        helper.addManyLembaga(lembagas);
                        Log.d(TAG, "Number of pesantren received: " + lembagas.size());
//                        Toast.makeText(mContext, "Jumlah Lembaga: " + lembagas.size(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "Tidak ada pembaruan data", Toast.LENGTH_SHORT).show();
                    }

                    onSyncronPesantren();
                }

            }

            @Override
            public void onFailure(Call<GetResponseLembaga>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }

    public void onSyncronPesantren() {

        String lastUpdate = prefManager.getLastUpdatePesantren();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponsePesantren> call = apiService.getPesantren(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data pesantren...");
//        progressDialog.setTitle("Sinkronisasi Data");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDialog.show();
        call.enqueue(new Callback<GetResponsePesantren>() {
            @Override
            public void onResponse(Call<GetResponsePesantren>call, Response<GetResponsePesantren> response) {
//                progressDialog.dismiss();
                if (response != null){

                    List<Pesantren> pesantrens = response.body().getData();
                    String lastSync = response.body().getTanggal().getDate();
                    prefManager.setLastUpdatePesantren(lastSync);
                    if (pesantrens.size()> 0){
                        PesantrenDbHelper helper = new PesantrenDbHelper(mContext);
                        helper.addManyPesantren(pesantrens);
                        Log.d(TAG, "Number of pesantren received: " + pesantrens.size());
//                        Toast.makeText(mContext, "Jumlah Pesantren: " + pesantrens.size(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "Tidak ada pembaruan data", Toast.LENGTH_SHORT).show();
                    }
                    onSyncronGetStatistikLembaga();
                }

            }

            @Override
            public void onFailure(Call<GetResponsePesantren>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }

    public void onSyncronGetStatistikLembaga() {

        String lastUpdate = prefManager.getLastUpdateStatistikLembaga();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseStatistikLembaga> call = apiService.getStatistikLembaga(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data Statistik Lembaga...");
//        progressDialog.setTitle("Sinkronisasi Data");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDialog.show();
        call.enqueue(new Callback<GetResponseStatistikLembaga>() {
            @Override
            public void onResponse(Call<GetResponseStatistikLembaga>call, Response<GetResponseStatistikLembaga> response) {
//                progressDialog.dismiss();
                if (response != null && response.body().getData() != null){
                    List<StatistikLembaga> laporans = response.body().getData();
                    String lastSync = response.body().getTanggal().getDate();
                    prefManager.setLastUpdateStatistikLembaga(lastSync);

                    if (laporans.size()> 0){
                        StatisikLembagaDbHelper helper = new StatisikLembagaDbHelper(mContext);
                        helper.addManyStatistikLembaga(laporans);
//                        Toast.makeText(getActivity(), "Number of statistik received: " + laporans.size(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
                    }

                    onSyncronGetStistikPesantren();
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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
            public void onFailure(Call<GetResponseStatistikLembaga>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }

    public void onSyncronGetStistikPesantren() {

        String lastUpdate = prefManager.getLastUpdateStatistikPesantren();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseStatistikPesantren> call = apiService.getStatistikPesantren(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data Statistik Pesantren...");
//        progressDialog.setTitle("Sinkronisasi Data");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDialog.show();
        call.enqueue(new Callback<GetResponseStatistikPesantren>() {
            @Override
            public void onResponse(Call<GetResponseStatistikPesantren>call, Response<GetResponseStatistikPesantren> response) {
                progressDialog.dismiss();
                if (response != null && response.body().getData() != null){
                    List<StatistikPesantren> statistikPesantren = response.body().getData();
                    String lastSync = response.body().getTanggal().getDate();
                    prefManager.setLastUpdateLaporanLembaga(lastSync);

                    if (statistikPesantren.size()> 0){
                        StatistikPesantrenDbHelper helper = new StatistikPesantrenDbHelper(mContext);
                        helper.addManyStatistikPesantren(statistikPesantren);
//                        Toast.makeText(getActivity(), "Number of statistik pesantren received: " + statistikPesantren.size(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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
            public void onFailure(Call<GetResponseStatistikPesantren>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }



//    @OnClick(R.id.button_getlapor_lembaga)
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
                    progressDialog.dismiss();
                    if (response != null && response.body().getData() != null){
                        List<Laporan> laporans = response.body().getData();
                        String lastSync = response.body().getTanggal().getDate();
                        prefManager.setLastUpdateLaporanLembaga(lastSync);

                        if (laporans.size()> 0){
                            LaporanLembagaDbHelper helper = new LaporanLembagaDbHelper(getContext());
                            helper.addManyLaporan(laporans);
                            Toast.makeText(getActivity(), "Number of laporan received: " + laporans.size(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
                        }

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


    public void exportDatabase() {

        // init realm
//        Realm realm = Realm.getInstance(getActivity());
//
//        File exportRealmFile = null;
//        try {
//            // get or create an "export.realm" file
//            exportRealmFile = new File(getActivity().getExternalCacheDir(), "export.realm");
//
//            // if "export.realm" already exists, delete
//            exportRealmFile.delete();
//
//            // copy current realm to "export.realm"
//            realm.writeCopyTo(exportRealmFile);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        realm.close();
//
//        // init email intent and add export.realm as attachment
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("plain/text");
//        intent.putExtra(Intent.EXTRA_EMAIL, "YOUR MAIL");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
//        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
//        Uri u = Uri.fromFile(exportRealmFile);
//        intent.putExtra(Intent.EXTRA_STREAM, u);
//
//        // start email intent
//        startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }

}
