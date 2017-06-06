package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.tentang;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseKabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponsePesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseProvinsi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SyncronFragment extends Fragment {


    @BindView(R.id.pesantren)
    Button btnPesantren;

    @BindView(R.id.lembaga)
    Button btnLembaga;

    @BindView(R.id.kabupten)
    Button btnKabupaten;

    @BindView(R.id.provinsi)
    Button btnProvinsi;

    public SyncronFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_syncron, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    @OnClick(R.id.pesantren)
    public void onSyncronPesantren() {

        PreferenceManager pref = new PreferenceManager(getContext());
        String lastUpdate = pref.getLastUpdate();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponsePesantren> call = apiService.getPesantren(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil data pesantren...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponsePesantren>() {
            @Override
            public void onResponse(Call<GetResponsePesantren>call, Response<GetResponsePesantren> response) {
                progressDialog.dismiss();
                if (response != null){
                    List<Pesantren> pesantrens = response.body().getData();
                    Log.d(TAG, "Number of pesantren received: " + pesantrens.size());
                    Toast.makeText(getActivity(), "Number of pesantren received: " + pesantrens.size(), Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.lembaga)
    public void onSyncronLembaga() {

        PreferenceManager pref = new PreferenceManager(getContext());
        String lastUpdate = pref.getLastUpdate();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseLembaga> call = apiService.getLembaga(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil data Lembaga...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponseLembaga>() {
            @Override
            public void onResponse(Call<GetResponseLembaga>call, Response<GetResponseLembaga> response) {
                progressDialog.dismiss();
                if (response != null){
                    List<Lembaga> lembagas = response.body().getData();
                    Log.d(TAG, "Number of pesantren received: " + lembagas.size());
                    Toast.makeText(getActivity(), "Number of pesantren received: " + lembagas.size(), Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.kabupten)
    public void onSyncronKabupaten() {

        KabupatenDbHelper kHelper = new KabupatenDbHelper(getContext());
        List<Kabupaten> lsK = kHelper.findAllProvinsi();

        if (lsK.size() > 0) {
            btnKabupaten.setEnabled(false);
            return;
        }
        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitKabupaten";

        Call<GetResponseKabupaten> call = apiService.getKabupaten(ApiServerURL.TOKEN_PUBLIC);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil data Kabupaten...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponseKabupaten>() {
            @Override
            public void onResponse(Call<GetResponseKabupaten>call, Response<GetResponseKabupaten> response) {
                progressDialog.dismiss();
                if (response != null){
                    List<Kabupaten> kabupatens = response.body().getData();
                    Log.d(TAG, "Number of pesantren received: " + kabupatens.size());
                    Toast.makeText(getActivity(), "Number of pesantren received: " + kabupatens.size(), Toast.LENGTH_LONG).show();

                    //simpan
                    KabupatenDbHelper helper = new KabupatenDbHelper(getActivity());
                    helper.addManyKabupaten(kabupatens);
                }

            }

            @Override
            public void onFailure(Call<GetResponseKabupaten>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.provinsi)
    public void onSyncronProvinsi() {
        final ProvinsiDbHelper pHelper = new ProvinsiDbHelper(getContext());
        List<Provinsi> lsP = pHelper.findAllProvinsi();

        if (lsP.size() > 0) {
            btnProvinsi.setEnabled(false);
            return;
        }

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitProvinsi";

        Call<GetResponseProvinsi> call = apiService.getProvinsi(ApiServerURL.TOKEN_PUBLIC);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil data provinsi...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponseProvinsi>() {
            @Override
            public void onResponse(Call<GetResponseProvinsi>call, Response<GetResponseProvinsi> response) {
                progressDialog.dismiss();
                if (response != null){
                    List<Provinsi> lsProvinsi = response.body().getData();
                    Log.d(TAG, "Number of pesantren received: " + lsProvinsi.size());
                    Toast.makeText(getActivity(), "Number of pesantren received: " + lsProvinsi.size(), Toast.LENGTH_LONG).show();

                    //simpan
//                    ProvinsiDbHelper helper = new ProvinsiDbHelper(getActivity());
                    pHelper.addManyProvinsi(lsProvinsi);
                }

            }

            @Override
            public void onFailure(Call<GetResponseProvinsi>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }

}
