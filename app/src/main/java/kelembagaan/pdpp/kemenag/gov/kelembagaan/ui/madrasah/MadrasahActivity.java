package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor.KoreksiActivity;

import static com.google.android.gms.wearable.DataMap.TAG;

public class MadrasahActivity extends AppCompatActivity {

    Lembaga madrasah;

    @BindView(R.id.tabs_madrasah)
    TabLayout tabLayout;

    @BindView(R.id.text_nama_madrasah)
    TextView tvNamaPesantren;

    @BindView(R.id.toolbar_madrasah)
    Toolbar toolbar;

    @BindView(R.id.viewpager_madrasah)
    ViewPager viewPager;

    @BindView(R.id.base_layout)
    CoordinatorLayout baseLayout;

    Menu menu;

    private ShareActionProvider mShareActionProvider;

    boolean isBookmark = false;

    private int[] tabIcons = {
            R.drawable.ic_tab_lembaga_pesantren,
            R.drawable.ic_tab_statistik_pesantren,
            R.drawable.ic_tab_lokasi_pesantren
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madrasah);

        ButterKnife.bind(this);

        madrasah = Parcels.unwrap(getIntent().getParcelableExtra("madrasah"));
        isBookmark = madrasah.getIsFavorit() == 1 ? true : false;

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Profil Madrasah");
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


        tvNamaPesantren.setText(madrasah.getNamaLembaga());
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelable("madrasah", Parcels.wrap(madrasah));

        ProfilMadrasahFragment profil = new ProfilMadrasahFragment();
        profil.setArguments(bundle);

        adapter.addFragment(profil);
        StatistikMadrasahFragment statistik = new StatistikMadrasahFragment();
        statistik.setArguments(bundle);
        adapter.addFragment(statistik);

        PetaLokasiMadrasahFragment lokasi = new PetaLokasiMadrasahFragment();
        Bundle bundleLokasi = new Bundle();
//        -6.218857, 106.663234
        bundleLokasi.putString("latitude", madrasah.getLatitude());
        bundleLokasi.putString("longitude", madrasah.getLongitude());
        bundleLokasi.putString("namaMadrasah", madrasah.getNamaLembaga());
        bundleLokasi.putString("alamatMadrasah", madrasah.getAlamat());
        lokasi.setArguments(bundleLokasi);
        adapter.addFragment(lokasi);
        viewPager.setAdapter(adapter);
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
        updateBookmark(isBookmark);
        return true;
    }

    private void updateBookmark(boolean isBookmark) {
        MenuItem item = menu.findItem(R.id.action_bookmark);
        if (isBookmark) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_on));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_menu_bookmark_off));
        }
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
            LembagaDbHelper helper = new LembagaDbHelper(this);
            if (isBookmark){
                isBookmark = false;
                helper.setBookmark(madrasah, 0);
                updateBookmark(isBookmark);
                Snackbar.make(baseLayout, "Madrasah dihapus dari favorit.", Snackbar.LENGTH_SHORT).show();
                return true;
            }else {
                helper.setBookmark(madrasah, 1);
                isBookmark = true;
                updateBookmark(isBookmark);
                Snackbar.make(baseLayout, "Madrasah dijadikan favorit", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        }

        if (item.getItemId() == R.id.action_koreksi){
            Intent intent = new Intent(MadrasahActivity.this, KoreksiActivity.class);
            intent.putExtra("tipe", 1);
            intent.putExtra("nama", madrasah.getNamaLembaga());
            intent.putExtra("id", madrasah.getIdLembaga());
            intent.putExtra("idKabupaten", madrasah.getKabupatenId());
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Madrasah : "+madrasah.getNamaLembaga() + "\n NSM : "+madrasah.getNsm() + "\n Pimpinan : "+madrasah.getPimpinan() + "\n Alamat : "+madrasah.getAlamat();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Cek Madrasah");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
