package com.example.javier.smovcercatrovav2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.location.LocationServices;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    public static int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationService.setActivity(this);
        Log.d("onCreate MAIN ACTIVITY","Activamos servicio");
        startService(new Intent(this, LocationService.class));
        if(contador==0){
            Toast.makeText(this, "Buscando posición GPS.", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Esto puede tardar unos instantes.", Toast.LENGTH_LONG).show();

        }
        contador++;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("START","Se vuelve a primer plano");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("STOP","...");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d("MENU","Action settings");
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_purge:
                Log.d("MENU","Purgar base de datos");

                int rows = getContentResolver().delete(CoordinatesContract.CONTENT_URI, null, null);

                // Para borrar un solo registro
                // String id = "1512251902951";
                //int rows = getContentResolver().delete(Uri.withAppendedPath(CoordinatesContract.CONTENT_URI,id), null, null);

                Toast.makeText(this, rows+" filas de la base de datos borradas", Toast.LENGTH_LONG).show();

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permiso concedido
                Log.e("PERMISOS", "Permiso concedido");
                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(LocationService.getApliClient());
                LocationService.setLocation(lastLocation);
                if(!LocationService.isActivo()) {
                    LocationService.setActivity(this);
                    Log.d("onCreate MAIN ACTIVITY", "Activamos servicio");
                    startService(new Intent(this, LocationService.class));
                }
            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e("PERMISOS", "Permiso denegado");
            }
        }
    }


}
