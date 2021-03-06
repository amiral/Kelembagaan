package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.bookmark;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

/**
 * Created by Amiral on 6/12/17.
 */

public class MadrasahBookmarkAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Lembaga> mDataSource;

    public MadrasahBookmarkAdapter(Context mContext, List<Lembaga> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Lembaga getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_bookmark_madrasah, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Lembaga madrasah = mDataSource.get(position);
        holder.nama.setText(madrasah.getNamaLembaga());
        holder.nomor.setText(""+madrasah.getNsm());
        Kabupaten k = new KabupatenDbHelper(mContext).getKabupaten(madrasah.getKabupatenId());
        Provinsi p = new ProvinsiDbHelper(mContext).getProvinsi(k.getProvinsiIdProvinsi());
        String lks = k.getNamaKabupaten() + ", " + p.getNamaProvinsi();
        holder.lokasi.setText(lks);

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LembagaDbHelper helper = new LembagaDbHelper(mContext);
                int iFavorit = helper.getLembaga(madrasah.getIdLembaga()).getIsFavorit();
                if (iFavorit == 1){
                    helper.setBookmark(madrasah, 0);
                    holder.btnBookmark.setImageResource(R.drawable.ic_action_bookmark_off);
                    Snackbar.make(holder.view, "Madrasah dihapus dari favorit.", Snackbar.LENGTH_SHORT).show();
                }else{
                    helper.setBookmark(madrasah, 1);
                    holder.btnBookmark.setImageResource(R.drawable.ic_action_bookmark_on);
                    Snackbar.make(holder.view, "Madrasah telah dijadikan favorit", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.text_nama_madrasah_bookmark)
        TextView nama;
        @BindView(R.id.text_nsm_madrasah_bookmark) TextView nomor;

        @BindView(R.id.text_lokasi_madrasah)
        TextView lokasi;

        @BindView(R.id.bookmark)
        ImageView btnBookmark;

        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
