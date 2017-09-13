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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.PesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

/**
 * Created by Amiral on 6/12/17.
 */

public class PesantrenBookmarkAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Pesantren> mDataSource;

    KabupatenDbHelper kabHelper ;
    ProvinsiDbHelper provHelper;
    PesantrenDbHelper pesantrenDbHelper;

    public PesantrenBookmarkAdapter(Context mContext, List<Pesantren> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        kabHelper = new KabupatenDbHelper(mContext);
        provHelper = new ProvinsiDbHelper(mContext);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Pesantren getItem(int position) {
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
            view = mInflater.inflate(R.layout.item_bookmark_pesantren, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Pesantren pesantren = mDataSource.get(position);

        holder.nama.setText(pesantren.getNamaPesantren());
        holder.nomor.setText(""+pesantren.getNspp());

        Kabupaten kb = kabHelper.getKabupaten(Integer.parseInt(pesantren.getKodeKabupaten()));
        Provinsi provinsi = provHelper.getProvinsi(kb.getProvinsiIdProvinsi());
        holder.lokasi.setText(kb.getNamaKabupaten() + ", " + provinsi.getNamaProvinsi());

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pesantrenDbHelper = new PesantrenDbHelper(mContext);
                int iFavorit = pesantrenDbHelper.getPesantren(pesantren.getIdPesantren()).getIsFavorit();
                if (iFavorit == 1){
                    pesantrenDbHelper.setBookmark(pesantren, 0);

                    holder.btnBookmark.setImageResource(R.drawable.ic_action_bookmark_off);
                    Snackbar.make(holder.view, "Pondok pesantren dihapus dari favorit.", Snackbar.LENGTH_SHORT).show();
                }else{
                    pesantrenDbHelper.setBookmark(pesantren, 1);
                    holder.btnBookmark.setImageResource(R.drawable.ic_action_bookmark_on);
                    Snackbar.make(holder.view, "Pondok pesantren telah dijadikan favorit", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.text_nama_pesantren_bookmark)
        TextView nama;
        @BindView(R.id.text_nspp_pesantren_bookmark) TextView nomor;

        @BindView(R.id.text_lokasi_pesantren)
        TextView lokasi;

        @BindView(R.id.book)
        ImageView btnBookmark;

        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
