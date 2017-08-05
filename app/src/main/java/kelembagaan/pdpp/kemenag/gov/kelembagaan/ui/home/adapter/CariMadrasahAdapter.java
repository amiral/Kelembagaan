package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.KabupatenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

/**
 * Created by Amiral on 6/1/17.
 */

public class CariMadrasahAdapter extends RecyclerView.Adapter<CariMadrasahAdapter.MyViewHolder> {

    private Context mContext;
    private List<Lembaga> madrasahList;
    private ItemClickListener clickListener;

    KabupatenDbHelper kabupatenDbHelper;
    ProvinsiDbHelper provinsiDbHelper;
    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public CariMadrasahAdapter(Context mContext, List<Lembaga> madrasahList){
        this.mContext = mContext;
        this.madrasahList = madrasahList;
        kabupatenDbHelper = new KabupatenDbHelper(mContext);
        provinsiDbHelper = new ProvinsiDbHelper(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_madrasah, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Lembaga madrasah = madrasahList.get(position);

        holder.tvNamaMadrasah.setText(madrasah.getNamaLembaga());
        holder.tvNamaPesantren.setText("Pesantren : "+madrasah.getNspp());
        Kabupaten kb = kabupatenDbHelper.getKabupaten(madrasah.getKabupatenId());
        Provinsi prov = provinsiDbHelper.getProvinsi(kb.getProvinsiIdProvinsi());
        holder.tvLokasiMadrasah.setText(kb.getNamaKabupaten() + ","+prov.getNamaProvinsi());

        holder.tvNsm.setText("NSPP : "+madrasah.getNsm());

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.btnMore);
            }
        });


    }

    @Override
    public int getItemCount() {
        return madrasahList.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu

        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_cari_pesantren, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_bookmark:
                    Toast.makeText(mContext, "Bookmark", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_report:
                    Toast.makeText(mContext, "Lapor", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.button_more) ImageButton btnMore;
        @BindView(R.id.text_nama_madrasah) TextView tvNamaMadrasah;
        @BindView(R.id.text_nama_pesantren) TextView tvNamaPesantren;
        @BindView(R.id.text_nsm) TextView tvNsm;
        @BindView(R.id.text_lokasi_madrasah) TextView tvLokasiMadrasah;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }

    }
}
