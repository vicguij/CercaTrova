package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class ListCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("list activity ACTIVAR", "onCreated");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_activity);

        // Comprobar si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crear un fragment
            ListCFragment fragment = new ListCFragment();
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
    }

    @Override
    protected void onStop(){
        super.onStop();
    }


}
