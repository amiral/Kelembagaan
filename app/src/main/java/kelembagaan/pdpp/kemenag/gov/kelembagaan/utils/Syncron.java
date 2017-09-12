package kelembagaan.pdpp.kemenag.gov.kelembagaan.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseProvinsi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amiral on 8/23/17.
 */

public class Syncron {

    Context mContext;

    public Syncron(Context mContext) {
        this.mContext = mContext;
    }

    public void startFromProvinsi(){
        onSyncronProvinsi();
    }

    public void startFromLembaga(){

    }

    public void onSyncronProvinsi() {
        final ProvinsiDbHelper pHelper = new ProvinsiDbHelper(mContext);
        List<Provinsi> lsP = pHelper.findAllProvinsi();



        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitProvinsi";

        Call<GetResponseProvinsi> call = apiService.getProvinsi(ApiServerURL.TOKEN_PUBLIC);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(mContext);
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
//                    Toast.makeText(, "Number of pesantren received: " + lsProvinsi.size(), Toast.LENGTH_LONG).show();

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
