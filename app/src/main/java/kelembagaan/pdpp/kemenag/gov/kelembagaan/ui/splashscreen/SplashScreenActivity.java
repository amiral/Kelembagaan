package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.splashscreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatisikLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatistikPesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikPesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseKabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponsePesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseProvinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikPesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreenActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PreferenceManager prefManager;

    Context mContext;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_splash_screen);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.fragment_splash_slide1,
                R.layout.fragment_splash_slide2,
                R.layout.fragment_splash_slide3};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                launchHomeScreen();
                onSyncronProvinsi();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
//                    launchHomeScreen();
                    onSyncronProvinsi();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    public void onSyncronProvinsi() {
        final ProvinsiDbHelper pHelper = new ProvinsiDbHelper(mContext);
        List<Provinsi> lsP = pHelper.findAllProvinsi();



        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitProvinsi";

        Call<GetResponseProvinsi> call = apiService.getProvinsi(ApiServerURL.TOKEN_PUBLIC);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data provinsi...");
        progressDialog.setTitle("Sinkronisasi Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<GetResponseProvinsi>() {
            @Override
            public void onResponse(Call<GetResponseProvinsi>call, Response<GetResponseProvinsi> response) {
//                progressDialog.dismiss();
                if (response != null){
                    List<Provinsi> lsProvinsi = response.body().getData();
                    Log.d(TAG, "Number of pesantren received: " + lsProvinsi.size());
//                    Toast.makeText(, "Number of pesantren received: " + lsProvinsi.size(), Toast.LENGTH_LONG).show();

                    //simpan
//                    ProvinsiDbHelper helper = new ProvinsiDbHelper(getActivity());
                    pHelper.addManyProvinsi(lsProvinsi);
                    onSyncronKabupaten();
                }

            }

            @Override
            public void onFailure(Call<GetResponseProvinsi>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
                launchHomeScreen();
            }
        });
    }

    public void onSyncronKabupaten() {

        KabupatenDbHelper kHelper = new KabupatenDbHelper(mContext);
        List<Kabupaten> lsK = kHelper.findAllKabupaten();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitKabupaten";

        Call<GetResponseKabupaten> call = apiService.getKabupaten(ApiServerURL.TOKEN_PUBLIC);

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data Kabupaten...");
//        progressDialog.setTitle("Sinkronisasi Data");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDialog.show();
        call.enqueue(new Callback<GetResponseKabupaten>() {
            @Override
            public void onResponse(Call<GetResponseKabupaten>call, Response<GetResponseKabupaten> response) {
//                progressDialog.dismiss();
                if (response != null){
                    List<Kabupaten> kabupatens = response.body().getData();
                    //simpan
                    KabupatenDbHelper helper = new KabupatenDbHelper(mContext);
                    helper.addManyKabupaten(kabupatens);
                    onSyncronLembaga();
                }

            }

            @Override
            public void onFailure(Call<GetResponseKabupaten>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
                launchHomeScreen();
            }
        });
    }

    public void onSyncronLembaga() {

        String lastUpdate = prefManager.getLastUpdateLembaga();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseLembaga> call = apiService.getLembaga(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Mengambil data Lembaga...");
//        progressDialog.setTitle("Sinkronisasi Data");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDialog.show();
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
//                        Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
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
//                        Toast.makeText(getActivity(), "Tidak ada pembaruan data", Toast.LENGTH_LONG).show();
                    }

                    launchHomeScreen();
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
}
