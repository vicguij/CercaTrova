package com.example.javier.smovcercatrovav2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivityCarga extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button btnActualizar;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_carga);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnActualizar = (Button)findViewById(R.id.BtnActualizar);

        btnActualizar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickActualizar();
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
        LatLng posicioninicio = new LatLng(LocationService.getLatitud(),LocationService.getLongitud());
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicioninicio,15));

        generarMapa(googleMap);
        /*
        mMap = googleMap;

      //  mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Bundle bundle = getIntent().getExtras();
        LatLng posicionCoche = new LatLng(bundle.getDouble("latcoche"),bundle.getDouble("longcoche"));
        LatLng posicionactual = new LatLng(bundle.getDouble("latactual"),bundle.getDouble("longactual"));
        Log.d("pinguino asesino", "posicion coche lat " + posicionCoche.latitude +"long " + posicionCoche.longitude );
        Log.d("pinguino asesino", "posicion actual lat " + posicionactual.latitude +"long " + posicionactual.longitude );

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        MapStyles.setActivity(this);
        MapStyles.setStyles(prefs);

        //SE aplican los estilos
        if(MapStyles.getMapStyle()!=0){
            mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this, MapStyles.getMapStyle()));
        }

        if(MapStyles.getEstiloItemCoche()!=0){
            mMap.addMarker(new MarkerOptions().position(posicionCoche).title("Ubicacion coche")).setIcon(BitmapDescriptorFactory.fromResource(MapStyles.getEstiloItemCoche()));
        }else{
            mMap.addMarker(new MarkerOptions().position(posicionCoche).title("Ubicacion coche"));
        }

        if(MapStyles.getEstiloItemActual()!=0){
            mMap.addMarker(new MarkerOptions().position(posicionactual).title("Ubicacion actual")).setIcon(BitmapDescriptorFactory.fromResource(MapStyles.getEstiloItemActual()));
        }else{
            mMap.addMarker(new MarkerOptions().position(posicionactual).title("Ubicacion actual"));
        }

        String url = obtenerDireccionesURL(posicionactual, posicionCoche);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);

       // mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionCoche,15));
        */

    }

    private void generarMapa(GoogleMap googleMap){
        mMap = googleMap;
        mMap.clear();

        //  mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Bundle bundle = getIntent().getExtras();
        LatLng posicionCoche = new LatLng(bundle.getDouble("latcoche"),bundle.getDouble("longcoche"));
        LatLng posicionactual = new LatLng(LocationService.getLatitud(),LocationService.getLongitud());
        //LatLng posicionactual = new LatLng(bundle.getDouble("latactual"),bundle.getDouble("longactual"));
        Log.d("pinguino asesino", "posicion coche lat " + posicionCoche.latitude +"long " + posicionCoche.longitude );
        Log.d("pinguino asesino", "posicion actual lat " + posicionactual.latitude +"long " + posicionactual.longitude );

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        MapStyles.setActivity(this);
        MapStyles.setStyles(prefs);

        //SE aplican los estilos
        if(MapStyles.getMapStyle()!=0){
            mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this, MapStyles.getMapStyle()));
        }

        if(MapStyles.getEstiloItemCoche()!=0){
            mMap.addMarker(new MarkerOptions().position(posicionCoche).title("Ubicacion coche")).setIcon(BitmapDescriptorFactory.fromResource(MapStyles.getEstiloItemCoche()));
        }else{
            mMap.addMarker(new MarkerOptions().position(posicionCoche).title("Ubicacion coche"));
        }

        if(MapStyles.getEstiloItemActual()!=0){
            mMap.addMarker(new MarkerOptions().position(posicionactual).title("Ubicacion actual")).setIcon(BitmapDescriptorFactory.fromResource(MapStyles.getEstiloItemActual()));
        }else{
            mMap.addMarker(new MarkerOptions().position(posicionactual).title("Ubicacion actual"));
        }

        String url = obtenerDireccionesURL(posicionactual, posicionCoche);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionactual,15));
    }

    public void clickActualizar() {
        Log.d("pinguino asesino", "CLICK ACTUALIZAR!!!");
        generarMapa(mMap);
    }

    private String obtenerDireccionesURL(LatLng origin,LatLng dest){

        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        String sensor = "sensor=false";

        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creamos una conexion http
            urlConnection = (HttpURLConnection) url.openConnection();

            // Conectamos
            urlConnection.connect();

            // Leemos desde URL
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("ERROR AL OBTENER INFO",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.parseColor(MapStyles.getPolylineColor()));
                //lineOptions.color(Color.rgb(0,0,255));
            }
            if(lineOptions!=null) {
                lineOptions.color(Color.parseColor(MapStyles.getPolylineColor()));
                mMap.addPolyline(lineOptions);
            }
        }
    }


}
