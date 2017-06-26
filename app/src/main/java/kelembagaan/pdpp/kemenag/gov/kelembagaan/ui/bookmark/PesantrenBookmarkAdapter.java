package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.bookmark;

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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;

/**
 * Created by Amiral on 6/12/17.
 */

public class PesantrenBookmarkAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Pesantren> mDataSource;

    public PesantrenBookmarkAdapter(Context mContext, List<Pesantren> mDataSource) {
        this.mContext = mContext;
        this.mDataSource = mDataSource;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_bookmark_pesantren, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Pesantren pesantren = mDataSource.get(position);

        holder.nama.setText(pesantren.getNamaPesantren());
        holder.nomor.setText(""+pesantren.getNspp());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.text_nama_pesantren_bookmark)
        TextView nama;
        @BindView(R.id.text_nspp_pesantren_bookmark) TextView nomor;

        @BindView(R.id.text_lokasi_pesantren)
        TextView lokasi;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
