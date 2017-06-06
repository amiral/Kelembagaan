package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah;


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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilMadrasahFragment extends Fragment {

    @BindView(R.id.text_nsm)
    TextView tvNsm;

    @BindView(R.id.text_nspp_madrasah)
    TextView tvNpsn;

    @BindView(R.id.text_nama_madrasah_profil)
    TextView tvNamaMadrasah;

    @BindView(R.id.text_alamat)
    TextView tvAlamat;

    @BindView(R.id.text_telp)
    TextView tvTelp;

    @BindView(R.id.text_pimpinan)
    TextView tvPimpinan;

    @BindView(R.id.text_website)
    TextView tvWebsite;

    @BindView(R.id.text_tipe_madrasah)
    TextView tvTipeMadrasah;

    @BindView(R.id.text_izin_operasional)
    TextView tvIzinOperasional;


    @BindView(R.id.text_pembaruan_terakhir)
    TextView tvPembaruanTerakhir;

    Lembaga madrasah ;

    public ProfilMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        madrasah = Parcels.unwrap(getArguments().getParcelable("madrasah"));

        View view = inflater.inflate(R.layout.fragment_profil_madrasah, container, false);
        ButterKnife.bind(this, view);

        fillData();
        return view;
    }

    private  void fillData(){
        tvNsm.setText(madrasah.getNsm());
        tvNpsn.setText(madrasah.getNpsn());
        tvNamaMadrasah.setText(madrasah.getNamaLembaga());
        tvAlamat.setText(madrasah.getAlamat());
        tvTelp.setText(madrasah.getTelp());
        tvPimpinan.setText(madrasah.getPimpinan());
        tvWebsite.setText(madrasah.getWebsite());
        tvTipeMadrasah.setText(madrasah.getIdTipeLembaga());
        tvIzinOperasional.setText(madrasah.getMasaBerlakuIjinOperasional());
        tvPembaruanTerakhir.setText(madrasah.getPembaharuanTerakhir());
    }
}
