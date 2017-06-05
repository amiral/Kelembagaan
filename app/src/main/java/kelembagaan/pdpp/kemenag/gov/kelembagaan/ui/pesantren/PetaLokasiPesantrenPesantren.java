package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PetaLokasiPesantrenPesantren extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mapView;
    View mView;
    private double lattitude, longitudinal;
    String namaPesantren;
    String alamat;

    public PetaLokasiPesantrenPesantren() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_peta_lokasi_pesantren, container, false);

        lattitude = getArguments().getDouble("latitude", 0);
        longitudinal = getArguments().getDouble("longitude", 0);
        namaPesantren = getArguments().getString("namaPesantren");
        alamat = getArguments().getString("alamatPesantren");

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
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng lokasiPesantren = new LatLng(lattitude, longitudinal);
        googleMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(namaPesantren).snippet(alamat));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(lokasiPesantren).zoom(16).bearing(0).tilt(45).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
