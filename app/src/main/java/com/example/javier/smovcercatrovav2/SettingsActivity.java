package com.example.javier.smovcercatrovav2;

/**
 * Created by javier on 8/12/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    public static boolean refresh = false;

    public static boolean isRefreshActive(){
        return refresh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Comprobar que no se ha creado la actividad antes
        if (savedInstanceState == null) {
// Crear el fragment
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment,
                            fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        refresh=true;
        stopService(new Intent(this, LocationService.class));
        Log.d("START","Setting Activity"+refresh);
    }

    @Override
    protected void onStop(){
        super.onStop();
        refresh=false;
        startService(new Intent(this, LocationService.class));
        Log.d("STOP","SettingsActivity"+false);
    }
}
