package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetaLokasiMadrasahFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap mGoogleMap;
    private MapView mapView;
    View mView;
    private double lattitude, longitudinal;
    String namaMadrasah;
    String alamat;

    public PetaLokasiMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_peta_lokasi_madrasah, container, false);
        lattitude = Double.parseDouble(getArguments().getString("latitude"));
        longitudinal = Double.parseDouble(getArguments().getString("longitude"));
        namaMadrasah = getArguments().getString("namaMadrasah");
        alamat = getArguments().getString("alamatMadrasah");
        return mView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) mView.findViewById(R.id.mapView) ;
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng lokasiPesantren = new LatLng(lattitude, longitudinal);
        googleMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(namaMadrasah).snippet(alamat));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(lokasiPesantren).zoom(16).bearing(0).tilt(45).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
