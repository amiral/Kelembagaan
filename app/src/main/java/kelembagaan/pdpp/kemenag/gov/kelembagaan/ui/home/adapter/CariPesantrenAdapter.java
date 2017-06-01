package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;

/**
 * Created by Amiral on 6/1/17.
 */

public class CariPesantrenAdapter extends RecyclerView.Adapter<CariPesantrenAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pesantren> pesantrenList;


    public CariPesantrenAdapter(Context mContext, List<Pesantren> pesantrenList){
        this.mContext = mContext;
        this.pesantrenList = pesantrenList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_pesantren, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Pesantren pesantren = pesantrenList.get(position);

        holder.tvNamaPesantren.setText(pesantren.getNamaPesantren());
        holder.tvPimpinanPesantren.setText(pesantren.getPimpinan());
        holder.tvLokasiPesantren.setText(pesantren.getLokasiPesantren());
        holder.tvNspp.setText(pesantren.getNspp());

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.btnMore);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pesantrenList.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu

        PopupMenu popup = new PopupMenu(mContext, view);

//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.button_more) ImageButton btnMore;
        @BindView(R.id.text_nama_pesantren) TextView tvNamaPesantren;
        @BindView(R.id.text_pimpinan_pesantren) TextView tvPimpinanPesantren;
        @BindView(R.id.text_nspp) TextView tvNspp;
        @BindView(R.id.text_lokasi_pesantren) TextView tvLokasiPesantren;
        @BindView(R.id.text_jumlah_madrasah) TextView tvJumlahMadrasah;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
