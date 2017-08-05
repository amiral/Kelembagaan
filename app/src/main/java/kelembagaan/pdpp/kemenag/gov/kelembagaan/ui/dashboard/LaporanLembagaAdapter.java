package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;

/**
 * Created by Amiral on 6/12/17.
 */

public class LaporanLembagaAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Lembaga> mDataSource;
    private KabupatenDbHelper dbKabupaten;
    private ProvinsiDbHelper dbProvinsi;

    public LaporanLembagaAdapter(Context mContext, List<Lembaga> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbKabupaten = new KabupatenDbHelper(mContext);
        dbProvinsi = new ProvinsiDbHelper(mContext);

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
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_madrasah_laporan, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Lembaga lembaga = mDataSource.get(position);

        Kabupaten kb = dbKabupaten.getKabupaten(lembaga.getKabupatenId());

        String lokasi = kb.getNamaKabupaten() + ", " + dbProvinsi.getProvinsi(kb.getProvinsiIdProvinsi()).getNamaProvinsi();

        Log.i("lmbg", "NamaDuplikat : "+lembaga.getIdLembaga());
        String nama = ""+lembaga.getNamaLembaga();
        holder.nama.setText(nama);
        holder.nomor.setText(""+lembaga.getNsm());
        holder.lokasi.setText(lokasi);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.text_nama_madrasah)
        TextView nama;
        @BindView(R.id.text_nsm_madrasah) TextView nomor;
        @BindView(R.id.text_lokasi) TextView lokasi;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
