package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;

/**
 * Created by Amiral on 6/12/17.
 */

public class LaporanMissingAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Laporan> mDataSource;

    public LaporanMissingAdapter(Context mContext, List<Laporan> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Laporan getItem(int position) {
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
            view = mInflater.inflate(R.layout.item_laporan_missing, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Laporan laporan = mDataSource.get(position);

        if (laporan.getIdPengguna() != 0)
            holder.nama.setText(laporan.getNamaPengguna());
        holder.pesan.setText(""+laporan.getKomentar());
        holder.tanggal.setText(laporan.getTanggal());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.text_nama)
        TextView nama;
        @BindView(R.id.text_pesan) TextView pesan;
        @BindView(R.id.text_tanggal) TextView tanggal;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
