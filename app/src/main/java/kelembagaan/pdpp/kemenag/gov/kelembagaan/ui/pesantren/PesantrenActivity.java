package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;

public class PesantrenActivity extends AppCompatActivity {
    private static final String TAG = PesantrenActivity.class.getSimpleName();

    Pesantren pesantren;

    @BindView(R.id.tabs_pesantren)
    TabLayout tabLayout;

    @BindView(R.id.text_nama_pesantren)
    TextView tvNamaPesantren;

    @BindView(R.id.toolbar_pesantren)
    Toolbar toolbar;

    @BindView(R.id.viewpager_pesantren)
    ViewPager viewPager;

    Menu menu;
    private ShareActionProvider mShareActionProvider;

    boolean isBookmark = false;

    private int[] tabIcons = {
            R.drawable.ic_tab_profil_pesantren,
            R.drawable.ic_tab_lembaga_pesantren,
            R.drawable.ic_tab_statistik_pesantren,
            R.drawable.ic_tab_lokasi_pesantren
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesantren);
        ButterKnife.bind(this);

        pesantren = Parcels.unwrap(getIntent().getParcelableExtra("pesantren"));

//        Toast.makeText(this,"Pesantren : "+pesantren.getNamaPesantren(),Toast.LENGTH_LONG).show();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Profil Pesantren");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_blur);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.primary);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_dark);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.primary)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.primary_dark)
            );
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        // TODO: 31/03/17
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tvNamaPesantren.setText(pesantren.getNamaPesantren());

       /* // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_pesantren);
//        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        setupViewPager(viewPager);
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);*/
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProfilPesantrenFragment());
        adapter.addFragment(new DaftarLembagaPesantrenFragment());
        adapter.addFragment(new StatistikPesantrenFragment());
        adapter.addFragment(new PetaLokasiPesantrenPesantren());
        viewPager.setAdapter(adapter);
    }

   /* private void setupViewPager(ViewPager viewPager) {
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfilPesantrenFragment(), R.drawable.ic_tab_profil_pesantren);
        adapter.addFragment(new DaftarLembagaPesantrenFragment(), R.drawable.ic_tab_lembaga_pesantren);
        adapter.addFragment(new StatistikPesantrenFragment(), R.drawable.ic_tab_statistik_pesantren);
        adapter.addFragment(new PetaLokasiPesantrenPesantren(),R.drawable.ic_tab_lokasi_pesantren);
        viewPager.setAdapter(adapter);
    }*/

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        List<Fragment> fragmentList = new ArrayList<>();
        List<Integer> fragmentIcon = new ArrayList<>();
        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getPageIconResId(int position) {
            return fragmentIcon.get(position);
        }

        public void addFragment(Fragment fragment, int icon) {
            fragmentList.add(fragment);
            fragmentIcon.add(icon);
        }
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
//            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_profil_pesantren, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            if (parentIntent == null) {
                finish();
                return true;
            } else {
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
            }
        }

        if (item.getItemId() == R.id.action_bookmark){
            if (isBookmark){
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_off));

//                item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_off));
//                menu.getItem(item.getItemId()).setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_off));
                isBookmark = false;
//                invalidateOptionsMenu();
                return true;
            }else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_on));
//                menu.getItem(item.getItemId()).setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_on));
//                item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_on));
//                invalidateOptionsMenu();
                isBookmark = true;
                return true;
            }
        }

        if (item.getItemId() == R.id.action_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Pesantren : "+pesantren.getNamaPesantren();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Cek Pesantren");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            return true;
        }




        return super.onOptionsItemSelected(item);
    }
}
