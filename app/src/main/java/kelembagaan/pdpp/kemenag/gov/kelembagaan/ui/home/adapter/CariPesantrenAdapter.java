package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.lapor.KoreksiActivity;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren.PesantrenActivity;

/**
 * Created by Amiral on 6/1/17.
 */

public class CariPesantrenAdapter extends RecyclerView.Adapter<CariPesantrenAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pesantren> pesantrenList;
    private ItemClickListener clickListener;

    KabupatenDbHelper kabHelper ;
    ProvinsiDbHelper provHelper;
    LembagaDbHelper lembagaDbHelper;
    PesantrenDbHelper helper;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public CariPesantrenAdapter(Context mContext, List<Pesantren> pesantrenList){
        this.mContext = mContext;
        this.pesantrenList = pesantrenList;

        kabHelper = new KabupatenDbHelper(mContext);
        provHelper = new ProvinsiDbHelper(mContext);
        lembagaDbHelper = new LembagaDbHelper(mContext);

        helper = new PesantrenDbHelper(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_pesantren, parent, false);

        return new MyViewHolder(itemView);
    }

    Pesantren pesantrenClick;
    boolean isBookmark;
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Pesantren pesantren = pesantrenList.get(position);

        holder.tvNamaPesantren.setText(pesantren.getNamaPesantren());
        holder.tvPimpinanPesantren.setText("Pengasuh : "+pesantren.getPimpinan());

        Kabupaten kb = kabHelper.getKabupaten(Integer.parseInt(pesantren.getKodeKabupaten()));
        Provinsi provinsi = provHelper.getProvinsi(kb.getProvinsiIdProvinsi());

        holder.tvLokasiPesantren.setText(kb.getNamaKabupaten() + "," + provinsi.getNamaProvinsi());

        holder.tvNspp.setText("NSPP : "+pesantren.getNspp());


        holder.tvJumlahMadrasah.setText(lembagaDbHelper.getAllLembagaPesantrenNumber(pesantren.getNspp()) + " Madrasah");
        holder.lytMadrasah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PesantrenActivity.class);
                intent.putExtra("idPesantren", pesantren.getIdPesantren());
                intent.putExtra("from", 1);
                mContext.startActivity(intent);
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesantrenClick = pesantren;
                isBookmark = pesantren.getIsFavorit() == 1 ? true : false;
                showPopupMenu(holder.btnMore, holder.view);
            }

        });


    }

    @Override
    public int getItemCount() {
        return pesantrenList.size();
    }

    private void showPopupMenu(View view, View base) {
        // inflate menu


        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_cari_pesantren, popup.getMenu());

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(base));
        popup.show();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        View view;
        public MyMenuItemClickListener(View view) {
            this.view = view;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_bookmark:
                    if (isBookmark) {
                        helper.setBookmark(pesantrenClick, 0);
                        isBookmark = false;
                        Toast.makeText(mContext, "Pondok pesantren dihapus dari favorit.", Toast.LENGTH_SHORT).show();
                    }else {
                        helper.setBookmark(pesantrenClick, 1);
                        isBookmark = true;
                        Toast.makeText(mContext, "Pondok pesantren telah dijadikan favorit", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.action_report:

                    Intent intent = new Intent(mContext, KoreksiActivity.class);
                    intent.putExtra("tipe", 0);
                    intent.putExtra("nama", pesantrenClick.getNamaPesantren());
                    intent.putExtra("id", pesantrenClick.getIdPesantren());
                    int idKabupaten = Integer.parseInt(pesantrenClick.getKodeKabupaten());
                    intent.putExtra("idKabupaten",idKabupaten);
                    mContext.startActivity(intent);

                    return true;
                case R.id.action_share:
                    shareView(mContext, view);
                    return true;
                default:
            }
            return false;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.button_more) ImageButton btnMore;
        @BindView(R.id.text_nama_pesantren) TextView tvNamaPesantren;
        @BindView(R.id.text_pimpinan_pesantren) TextView tvPimpinanPesantren;
        @BindView(R.id.text_nspp) TextView tvNspp;
        @BindView(R.id.text_lokasi_pesantren) TextView tvLokasiPesantren;
        @BindView(R.id.text_jumlah_madrasah) TextView tvJumlahMadrasah;
        @BindView(R.id.layout_bottom) RelativeLayout lytMadrasah;

        View view;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }
    }

    public void shareView(Context activity, View view){
        //Ambil Tampilan
//        View view = view.getWindow().getDecorView();
        Bitmap bitmap;
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheBackgroundColor(Color.argb(200, 255, 255, 255));
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();

//        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
//        view.destroyDrawingCache();

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
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

//                dialog.dismiss();

                intent.setFlags(1);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", shareFile));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Cek Pesantren");
                String shareBodyText = "Pesantren : "+pesantrenClick.getNamaPesantren() + "\nNSPP : "+pesantrenClick.getNspp() + "\nPimpinan : "+pesantrenClick.getPimpinan() + "\nAlamat : "+pesantrenClick.getAlamat()
                        +"\nhttp://pbsb.ditpdpontren.kemenag.go.id/pdpp/";
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                activity.startActivity(Intent.createChooser(intent, "Shearing Option"));
                view.destroyDrawingCache();
                view.setDrawingCacheEnabled(false);
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
}
