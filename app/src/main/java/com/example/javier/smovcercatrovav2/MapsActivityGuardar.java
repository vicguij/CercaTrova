package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MapsActivityGuardar extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnGuardar;
    private EditText txt;
    SharedPreferences prefs;
    LatLng posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_guardar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txt = (EditText)findViewById(R.id.Nombre);
        btnGuardar = (Button)findViewById(R.id.BtnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
              clickInsert();
            }
        });
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

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        MapStyles.setActivity(this);
        MapStyles.setStyles(prefs);

        //SE aplican los estilos
        if(MapStyles.getMapStyle()!=0){
            mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this, MapStyles.getMapStyle()));
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        Bundle bundle = getIntent().getExtras();
        posicion = new LatLng(bundle.getDouble("lat"),bundle.getDouble("long"));
        mMap.addMarker(new MarkerOptions().position(posicion).title("Ubicacion"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,17));
    }


    public void clickInsert() {
        double latitud = posicion.latitude;
        double longitud = posicion.longitude;
        Log.d("BOTON INSERT", "Insertando coordenadas: "+longitud+", "+latitud);

        // Cogemos un identificador unico: un timestamp del sistema
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long id = timestamp.getTime();

        // Sacamos el tiempo actual en formato string
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Nombre de la coordenada a insertar
        String nombre = txt.getText().toString();

        try{
            ContentValues values = new ContentValues();

            // Insertar en la base de datos
            values.clear();
            values.put(CoordinatesContract.Column.ID, id );
            values.put(CoordinatesContract.Column.NOMBRE, nombre );
            values.put(CoordinatesContract.Column.LATITUD, latitud );
            values.put(CoordinatesContract.Column.LONGITUD, longitud );
            values.put(CoordinatesContract.Column.CREATED_AT, date );

            // Se inserta utilizando el content provider
            Uri uri = this.getContentResolver().insert(CoordinatesContract.CONTENT_URI, values);

        }catch (Exception e) {
            Log.d("BOTON INSERT", "EXCEPCION...");
            Toast.makeText(this,getString(R.string.falloguardar),Toast.LENGTH_SHORT).show();

        }
        Toast.makeText(this,getString(R.string.bienguardar),Toast.LENGTH_SHORT).show();
        finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
