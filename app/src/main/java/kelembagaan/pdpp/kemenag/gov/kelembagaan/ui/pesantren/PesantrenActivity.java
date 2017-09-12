package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor.KoreksiActivity;

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
    boolean isBookmark = false;
    Context mContext;
    PesantrenDbHelper helper;
    @BindView(R.id.base_layout)
    CoordinatorLayout baseLayout;
    View myView;
    private ShareActionProvider mShareActionProvider;
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
        mContext = this;

        int idPesantren = getIntent().getIntExtra("idPesantren", 0);
         helper = new PesantrenDbHelper(this);
        pesantren = helper.getPesantren(idPesantren);

        isBookmark = pesantren.getIsFavorit() == 1 ? true : false;

//        pesantren = Parcels.unwrap(getIntent().getParcelableExtra("pesantren"));

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

        int fromJumlahLembaga = getIntent().getIntExtra("from", 0);
        if (fromJumlahLembaga == 1)
            viewPager.setCurrentItem(1);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelable("pesantren", Parcels.wrap(pesantren));

        ProfilPesantrenFragment profil = new ProfilPesantrenFragment();
        profil.setArguments(bundle);

        adapter.addFragment(profil);

        DaftarLembagaPesantrenFragment lembagaPesantren = new DaftarLembagaPesantrenFragment();
        lembagaPesantren.setArguments(bundle);
        adapter.addFragment(lembagaPesantren);

        StatistikPesantrenFragment statistik = new StatistikPesantrenFragment();
        statistik.setArguments(bundle);
        adapter.addFragment(statistik);

        PetaLokasiPesantrenPesantren lokasi = new PetaLokasiPesantrenPesantren();
        Bundle bundleLokasi = new Bundle();
//        -6.218857, 106.663234
        bundleLokasi.putString("latitude", pesantren.getLatitude());
        bundleLokasi.putString("longitude",pesantren.getLongitude());
        bundleLokasi.putString("namaPesantren", pesantren.getNamaPesantren());
        bundleLokasi.putString("alamatPesantren", pesantren.getAlamat());
        lokasi.setArguments(bundleLokasi);
        adapter.addFragment(lokasi);
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
            if (isBookmark) {
                helper.setBookmark(pesantren, 0);
                isBookmark = false;
                updateBookmark(isBookmark);
                Snackbar.make(baseLayout, "Pondok pesantren dihapus dari favorit.", Snackbar.LENGTH_SHORT).show();
                return true;
            }else {
                helper.setBookmark(pesantren, 1);
                isBookmark = true;
                updateBookmark(isBookmark);
                Snackbar.make(baseLayout, "Pondok pesantren telah dijadikan favorit", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        }

        if (item.getItemId() == R.id.action_koreksi){
            Intent intent = new Intent(PesantrenActivity.this, KoreksiActivity.class);
            intent.putExtra("tipe", 0);
            intent.putExtra("nama", pesantren.getNamaPesantren());
            intent.putExtra("id", pesantren.getIdPesantren());
            int idKabupaten = Integer.parseInt(pesantren.getKodeKabupaten());
            intent.putExtra("idKabupaten",idKabupaten);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_share){
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            String shareBodyText = "Pesantren : "+pesantren.getNamaPesantren() + "\n NSPP : "+pesantren.getNspp() + "\n Pimpinan : "+pesantren.getPimpinan() + "\n Alamat : "+pesantren.getAlamat();
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Cek Pesantren");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

            shareActivityView(PesantrenActivity.this);
            return true;
        }




        return super.onOptionsItemSelected(item);
    }


    ProgressDialog dialog;

    @Override
    public void onResume() {
        super.onResume();

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public void shareActivityView(Activity activity){
        dialog  = new ProgressDialog(activity);
        dialog.setMessage("Menyiapkan gambar...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        dialog.show();
        //Ambil Tampilan
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.destroyDrawingCache();

        //Simpan Tampilan
        File shareFolder = new File(activity.getFilesDir(), "share");
        if (!shareFolder.exists()) {
            shareFolder.mkdirs();
        }
        File shareFile = new File(shareFolder, "pesantren.png");
        if (!shareFile.exists()) {
            try {
                shareFile.createNewFile();
            } catch (IOException e2) {
                Log.e(getClass().getName(), e2.getMessage());
            }
        }


        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(shareFile);
            if (null != fos)
            {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

//                dialog.dismiss();

                intent.setFlags(1);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", shareFile));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Cek Pesantren");
                String shareBodyText = "Pesantren : "+pesantren.getNamaPesantren() + "\nNSPP : "+pesantren.getNspp() + "\nPimpinan : "+pesantren.getPimpinan() + "\nAlamat : "+pesantren.getAlamat()
                        +"\nhttp://pbsb.ditpdpontren.kemenag.go.id/pdpp/";
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(intent, "Shearing Option"));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
}
