package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pengguna;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.bookmark.BookmarkFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard.DashboardFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.disclaimer.DisclaimerFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor.LaporFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.login.LoginActivity;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.tentang.SyncronFragment;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.tentang.TentangFragment;

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

    int FILTER_REQUEST = 111;

    public static String CURRENT_TAG = TAG_CARI;

    private boolean isLogin = false;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.bottomBar)
//    BottomBar bottomBar;

    RelativeLayout lytHeader;
    RelativeLayout lytHeaderLogin;

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
//        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        preferenceManager = new PreferenceManager(this);

        mHandler = new Handler();

        navHeader = navigationView.getHeaderView(0);

        lytHeader = (RelativeLayout) navHeader.findViewById(R.id.lytHeader);
        lytHeaderLogin = (RelativeLayout) navHeader.findViewById(R.id.lytHeaderLogin);


        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            if (preferenceManager.isLogin()) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_CARI;
                loadHomeFragment();
            } else {
                navItemIndex = 0;
                CURRENT_TAG = TAG_CARI;
                loadHomeFragment();
            }
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
            // load toolbar titles from string resources
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_login);
        } else {
            // load toolbar titles from string resources
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        }
    }

    private void setUpNavigationView() {

        if (preferenceManager.isLogin()) {
            lytHeaderLogin.setVisibility(View.VISIBLE);
            lytHeader.setVisibility(View.GONE);

            TextView tvNama = (TextView) lytHeaderLogin.findViewById(R.id.text_nama_profil);
            TextView tvJabatan = (TextView) lytHeaderLogin.findViewById(R.id.text_jabatan_profil);

            Pengguna pengguna = preferenceManager.getPengguna();

            tvNama.setText(pengguna.getNama());
            tvJabatan.setText(pengguna.getNamaHakAkses());

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_login);
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
                            navItemIndex = 6;
                            CURRENT_TAG = TAG_SYNCRON;
                            break;
                        case R.id.nav_logout:
                            onLogout();
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_CARI;
                            break;
                        default:
                            navItemIndex = 0;
                    }

                    //Checking if the item is in checked state or not, if not make it in checked state
                    if (menuItem.isChecked()) {
                        menuItem.setChecked(false);
                    } else {
                        menuItem.setChecked(true);
                    }
//                    menuItem.setChecked(true);

                    loadHomeFragment();

                    return true;
                }
            });
        } else {

            lytHeaderLogin.setVisibility(View.GONE);
            lytHeader.setVisibility(View.VISIBLE);

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                // This method will trigger on item Click of navigation menu
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    //Check to see which item was being clicked and perform appropriate action
                    switch (menuItem.getItemId()) {
                        //Replacing the main content with ContentFragment Which is our Inbox View;
                        case R.id.nav_search:
                            navItemIndex = 0;
                            CURRENT_TAG = TAG_CARI;
                            break;
                        case R.id.nav_bookmark:
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_BOOKMARK;
                            break;
                        case R.id.nav_lapor:
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_LAPOR;
                            break;
                        case R.id.nav_disclaimer:
                            navItemIndex = 3;
                            CURRENT_TAG = TAG_DISCLAIMER;
                            break;
                        case R.id.nav_tentang:
                            navItemIndex = 4;
                            CURRENT_TAG = TAG_TENTANG;
                            break;
                        case R.id.nav_syncronize:
                            navItemIndex = 5;
                            CURRENT_TAG = TAG_SYNCRON;
                            break;
                        case R.id.nav_login:
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            drawer.closeDrawers();
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
//                    menuItem.setChecked(true);

                    loadHomeFragment();

                    return true;
                }
            });
        }


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


    private void loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        loadNavHeader();
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
        if (preferenceManager.isLogin()){
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
                    // diclaimer fragment
                    DisclaimerFragment disclaimerFragment = new DisclaimerFragment();
                    return disclaimerFragment;
                case 5:
                    // tentang fragment
                    TentangFragment tentangFragment = new TentangFragment();
                    return tentangFragment;
                case 6:
                    // syncron fragment
                    SyncronFragment syncronFragment = new SyncronFragment();
                    return syncronFragment;
                default:
                    return new CariFragment();
            }
        }else{
            switch (navItemIndex) {
                case 0:
                    // home
                    CariFragment cariFragment = new CariFragment();
                    return cariFragment;
                case 1:
                    // bookmark fragment
                    BookmarkFragment bookmarkFragment = new BookmarkFragment();
                    return bookmarkFragment;
                case 2:
                    // lapor fragment
                    LaporFragment laporFragment = new LaporFragment();
                    return laporFragment;
                case 3:
                    // diclaimer fragment
                    DisclaimerFragment disclaimerFragment = new DisclaimerFragment();
                    return disclaimerFragment;
                case 4:
                    // tentang fragment
                    TentangFragment tentangFragment = new TentangFragment();
                    return tentangFragment;
                case 5:
                    // syncron fragment
                    SyncronFragment syncronFragment = new SyncronFragment();
                    return syncronFragment;
                default:
                    return new CariFragment();
            }
        }

    }


    private void onLogout() {
        preferenceManager.setLogin(false);
        navItemIndex = 0;
        CURRENT_TAG = TAG_CARI;
        loadHomeFragment();
        setUpNavigationView();


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Anda Berhasil Keluar");
        alertDialogBuilder.setPositiveButton("OKE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
            int index = 0;
            if (preferenceManager.isLogin())
                index = 1;

            if (navItemIndex != index) {
                navItemIndex = index;
                CURRENT_TAG = TAG_CARI;
                loadHomeFragment();
                if (isSearchOpened) {
//                    handleMenuSearch();
                }
                return;
            }else{
                /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Apakah anda ingin keluar dari aplikasi?");
                alertDialogBuilder.setPositiveButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                alertDialogBuilder.setNegativeButton("Keluar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();*/
                finish();

//                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu only when home fragment is selected
        int index = 0;
        if (preferenceManager.isLogin())
            index = 1;

        if (navItemIndex == index) {
//            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            handleMenuSearch();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
