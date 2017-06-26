package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
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

    public static String CURRENT_TAG = TAG_CARI;

    private boolean isLogin = false;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

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
        // load toolbar titles from string resources
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_login);
        } else {
        // load toolbar titles from string resources
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        }
    }

    private void setUpNavigationView() {

        if (preferenceManager.isLogin()) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_login);
        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
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
    }


    private void onLogout() {
        preferenceManager.setLogin(false);
        preferenceManager.clearPengguna();
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
            bottomBar.setVisibility(View.GONE);
            isFirstBar = true;
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
            bottomBar.setVisibility(View.VISIBLE);

            handleBottomBar();
        }

    }

    private void doSearch(){

    }

    boolean isFirstBar = true;
    boolean mIsLargeLayout;
    private void handleBottomBar(){

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (isFirstBar){
                    isFirstBar = false;
                }else {
                    if (tabId == R.id.tab_wilayah) {
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        Toast.makeText(MainActivity.this, "Tab Wilayah", Toast.LENGTH_SHORT).show();
                        showDialogWilayah();
                    } else if (tabId == R.id.tab_tipe) {
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        Toast.makeText(MainActivity.this, "Tab Tipe", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Tab Jenjang", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void showDialogWilayah() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogWilayahFragment newFragment = new DialogWilayahFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
//
//        if (mIsLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//            newFragment.show(fragmentManager, "dialog");
//        } else {
//            // The device is smaller, so show the fragment fullscreen
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            // For a little polish, specify a transition animation
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            // To make it fullscreen, use the 'content' root view as the container
//            // for the fragment, which is always the root view for the activity
//            transaction.add(android.R.id.content, newFragment)
//                    .addToBackStack(null).commit();
//        }
    }

}
