package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilPesantrenFragment extends Fragment {


    @BindView(R.id.text_nspp)
    TextView tvNspp;

    @BindView(R.id.text_nama_pesantren_profil)
    TextView tvNamaPesantren;

    @BindView(R.id.text_tahun_berdiri)
    TextView tvTahunBerdiri;

    @BindView(R.id.text_pengasuh)
    TextView tvPengasuh;

    @BindView(R.id.text_alamat)
    TextView tvAlamat;

    @BindView(R.id.text_telp)
    TextView tvTelp;

    @BindView(R.id.text_website)
    TextView tvWebsite;

    @BindView(R.id.text_tipe_pesantren)
    TextView tvTipePesantren;

    @BindView(R.id.text_potensi_ekonomi)
    TextView tvPotensiEkonomi;

    @BindView(R.id.text_konsentrasi_agama)
    TextView tvKonsentrasiAgama;

    @BindView(R.id.text_luas_pesantren)
    TextView tvLuasPesantren;

    @BindView(R.id.text_pembaruan_terakhir)
    TextView tvPembaruanTerakhir;

    private Pesantren pesantren;
    public ProfilPesantrenFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        pesantren = Parcels.unwrap(getArguments().getParcelable("pesantren"));

        View view = inflater.inflate(R.layout.fragment_profil_pesantren, container, false);
        ButterKnife.bind(this, view);

        fillData();
        return view;
    }

    private void fillData(){
        tvNspp.setText(pesantren.getNspp());
        tvNamaPesantren.setText(pesantren.getNamaPesantren());
        tvTahunBerdiri.setText(pesantren.getTahunBerdiri());
        tvPengasuh.setText(pesantren.getPimpinan());
        tvAlamat.setText(pesantren.getAlamat());
        tvTelp.setText(pesantren.getTelp());
        tvWebsite.setText(pesantren.getWebsite());
        tvTipePesantren.setText(""+pesantren.getIdTipologi());
        tvPotensiEkonomi.setText(""+pesantren.getPotensiEkonomi());
        tvKonsentrasiAgama.setText(""+pesantren.getIdKonsentrasi());
        tvLuasPesantren.setText(pesantren.getLuasPesantren());
        tvPembaruanTerakhir.setText(pesantren.getPembaharuanTerakhir());
    }

}
