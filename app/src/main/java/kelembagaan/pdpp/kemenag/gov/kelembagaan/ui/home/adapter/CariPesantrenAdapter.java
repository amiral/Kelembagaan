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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.ProvinsiDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

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

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public CariPesantrenAdapter(Context mContext, List<Pesantren> pesantrenList){
        this.mContext = mContext;
        this.pesantrenList = pesantrenList;

        kabHelper = new KabupatenDbHelper(mContext);
        provHelper = new ProvinsiDbHelper(mContext);
        lembagaDbHelper = new LembagaDbHelper(mContext);
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
        holder.tvPimpinanPesantren.setText("Pengasuh : "+pesantren.getPimpinan());

        Kabupaten kb = kabHelper.getKabupaten(Integer.parseInt(pesantren.getKodeKabupaten()));
        Provinsi provinsi = provHelper.getProvinsi(kb.getProvinsiIdProvinsi());

        holder.tvLokasiPesantren.setText(kb.getNamaKabupaten() + "," + provinsi.getNamaProvinsi());

        holder.tvNspp.setText("NSPP : "+pesantren.getNspp());


        holder.tvJumlahMadrasah.setText(lembagaDbHelper.getAllLembagaPesantrenNumber(pesantren.getNspp()) + " Madrasah");
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


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.button_more) ImageButton btnMore;
        @BindView(R.id.text_nama_pesantren) TextView tvNamaPesantren;
        @BindView(R.id.text_pimpinan_pesantren) TextView tvPimpinanPesantren;
        @BindView(R.id.text_nspp) TextView tvNspp;
        @BindView(R.id.text_lokasi_pesantren) TextView tvLokasiPesantren;
        @BindView(R.id.text_jumlah_madrasah) TextView tvJumlahMadrasah;

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
