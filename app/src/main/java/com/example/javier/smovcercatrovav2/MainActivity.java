package com.example.javier.smovcercatrovav2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.location.LocationServices;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    public static int contador=0;
    public static boolean update = false;

    public static boolean isUpdate(){
        return update;
    }

    public static void setUpdate( boolean bol){
        update = bol;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationService.setActivity(this);
        Log.d("onCreate MAIN ACTIVITY","Activamos servicio");
        startService(new Intent(this, LocationService.class));
        if(contador==0){

                Toast.makeText(this, getString(R.string.inicio1), Toast.LENGTH_LONG).show();
                Toast.makeText(this, getString(R.string.inicio2), Toast.LENGTH_LONG).show();
        }

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
protected void onResume(){
        super.onResume();
    Log.d("onCreate MAIN ACTIVITY","contador="+contador);

    if((isUpdate())&&(contador==0)) {
         Toast.makeText(this, getString(R.string.encontrada), Toast.LENGTH_LONG).show();

   }
   //De mostrarse, el mensaje de que se ha encontrado la ubicación solo debe ser cuando se abra la app.
    contador++;

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
                Toast.makeText(this, rows+" " + getString(R.string.delete), Toast.LENGTH_LONG).show();

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
    /*
    * Con este método controlamos el flujo de actividades, de forma que si el usuario está en la pantalla principal
    * y pulsa el botón de "atrás", la aplicación se cierre independientemente de que hubiese actividades que se hubiesen visitado antes
    * La necesidad de esta solución surge tras probar la interconexión entre las actividades, pues al pulsar el botón atrás
    * se detectaron anomalías.
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;

        }

        return super.onKeyDown(keyCode, event);
    }



}
