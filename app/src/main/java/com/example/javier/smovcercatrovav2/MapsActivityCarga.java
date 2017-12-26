package com.example.javier.smovcercatrovav2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivityCarga extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_carga);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

      //  mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Bundle bundle = getIntent().getExtras();
        LatLng posicionCoche = new LatLng(bundle.getDouble("latcoche"),bundle.getDouble("longcoche"));
        LatLng posicionactual = new LatLng(bundle.getDouble("latactual"),bundle.getDouble("longactual"));
        Log.d("pinguino asesino", "posicion coche lat " + posicionCoche.latitude +"long " + posicionCoche.longitude );
        Log.d("pinguino asesino", "posicion actual lat " + posicionactual.latitude +"long " + posicionactual.longitude );

        mMap.addMarker(new MarkerOptions().position(posicionCoche).title("Ubicacion coche wiii"));
        mMap.addMarker(new MarkerOptions().position(posicionactual).title("Ubicacion actual"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionCoche,15));

    }
}
