package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.bookmark.BookmarkFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard.DashboardFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor.LaporFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.login.LoginActivity;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.tentang.TentangFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    //    index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_DASHBOARD = "dashboard";
    private static final String TAG_CARI = "cari";
    private static final String TAG_LAPOR = "lapor";
    private static final String TAG_DISCLAIMER = "disclaimer";
    private static final String TAG_BOOKMARK = "bookmark";
    private static final String TAG_TENTANG = "tentang";
    private static final String TAG_SYNCRON = "syncron";

    public static String CURRENT_TAG = TAG_CARI;

    private boolean isLogin = false;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    EditText etSearch;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;

    private View navHeader;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        preferenceManager = new PreferenceManager(this);

        mHandler = new Handler();


        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        navHeader = navigationView.getHeaderView(0);



        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_CARI;
            loadHomeFragment();
        }


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


    }

    //untuk loading navHeader
    private void loadNavHeader() {
        // Cek apakah login atau tidak
        if (preferenceManager.isLogin()) {

        } else {

        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.nav_search:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CARI;
                        break;
                    case R.id.nav_bookmark:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_BOOKMARK;
                        break;
                    case R.id.nav_lapor:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LAPOR;
                        break;
                    case R.id.nav_disclaimer:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_DISCLAIMER;
                        break;
                    case R.id.nav_tentang:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_TENTANG;
                        break;
                    case R.id.nav_syncronize:
//                        onSyncron();
//                        onSyncronLembaga();
//                        onSyncronKabupaten();
                        onSyncronProvinsi();
                        return true;

                    case R.id.nav_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        onLogout();
                        drawer.refreshDrawableState();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }




    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void onSyncron() {

        PreferenceManager pref = new PreferenceManager(this);
        String lastUpdate = pref.getLastUpdate();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponsePesantren> call = apiService.getPesantren(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("mengambil data...");
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
                    Toast.makeText(MainActivity.this, "Number of pesantren received: " + pesantrens.size(), Toast.LENGTH_LONG).show();
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

    private void onSyncronLembaga() {

        PreferenceManager pref = new PreferenceManager(this);
        String lastUpdate = pref.getLastUpdate();

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "Retrofit";

        Call<GetResponseLembaga> call = apiService.getLembaga(ApiServerURL.TOKEN_PUBLIC,lastUpdate);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("mengambil data...");
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
                    Toast.makeText(MainActivity.this, "Number of pesantren received: " + lembagas.size(), Toast.LENGTH_LONG).show();
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

    private void onSyncronKabupaten() {

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitKabupaten";

        Call<GetResponseKabupaten> call = apiService.getKabupaten(ApiServerURL.TOKEN_PUBLIC);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("mengambil data kabupaten...");
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
                    Toast.makeText(MainActivity.this, "Number of pesantren received: " + kabupatens.size(), Toast.LENGTH_LONG).show();

                    //simpan
                    KabupatenDbHelper helper = new KabupatenDbHelper(MainActivity.this);
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

    private void onSyncronProvinsi() {

        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitProvinsi";

        Call<GetResponseProvinsi> call = apiService.getProvinsi(ApiServerURL.TOKEN_PUBLIC);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("mengambil data kabupaten...");
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
                    Toast.makeText(MainActivity.this, "Number of pesantren received: " + lsProvinsi.size(), Toast.LENGTH_LONG).show();

                    //simpan
                    ProvinsiDbHelper helper = new ProvinsiDbHelper(MainActivity.this);
                    helper.addManyProvinsi(lsProvinsi);
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

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 1:
                // home
                CariFragment cariFragment = new CariFragment();
                return cariFragment;
            case 0:
                // dahsboard
                DashboardFragment dashboardFragment = new DashboardFragment();
                return dashboardFragment;
            case 2:
                // bookmark fragment
                BookmarkFragment bookmarkFragment = new BookmarkFragment();
                return bookmarkFragment;
            case 3:
                // lapor fragment
                LaporFragment laporFragment = new LaporFragment();
                return laporFragment;

            case 4:
                // tentang fragment
                TentangFragment tentangFragment = new TentangFragment();
                return tentangFragment;
            default:
                return new CariFragment();
        }
    }


    private void onLogout() {
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 1) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_CARI;
                loadHomeFragment();
                if(isSearchOpened) {
                    handleMenuSearch();
                }
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu only when home fragment is selected
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            handleMenuSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(R.drawable.ic_action_search);

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            etSearch = (EditText)action.getCustomView().findViewById(R.id.edit_search); //the text editor

            //this is a listener to do a search when the user clicks on search button
            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            etSearch.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(R.drawable.ic_action_close);

            isSearchOpened = true;
        }
    }

    private void doSearch(){

    }



}
