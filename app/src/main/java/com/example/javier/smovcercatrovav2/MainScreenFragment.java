package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MainScreenFragment extends Fragment implements View.OnClickListener {

    private Button btnInsert;
    private Button btnList;

    DbHelper dbHelper;
    SQLiteDatabase db;

    private static final String TAG = "MainScreenFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        btnInsert = (Button) view.findViewById(R.id.MainGuardar);
        btnList = (Button) view.findViewById(R.id.MainVerUbicaciones);

        btnInsert.setOnClickListener(this);
        btnList.setOnClickListener(this);

        return view;
    }

    public void clickListLocations() {

        Log.d("BOTON LIST", "Mostrando las coordenadas guardadas...");

        Cursor c = this.getActivity().getContentResolver().query(CoordinatesContract.CONTENT_URI, null, null, null, CoordinatesContract.DEFAULT_SORT);
        if (c != null) {
            if (c.getCount()==0){
                Toast.makeText(getActivity(),getString(R.string.zeroguardadas),Toast.LENGTH_SHORT).show();
            } else {
                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String id = c.getString(0);
                        String nombre = c.getString(1);
                        String latitud = c.getString(2);
                        String longitud = c.getString(3);
                        String fecha = c.getString(4);
                        Log.d("Registros:  ", "ID:" + id + ", Nombre:" + nombre + ", Latitud:" + latitud + ", Longitud:" + longitud + ", Fecha:" + fecha);
                    } while (c.moveToNext());
                }
            c.close();
                startActivity(new Intent(this.getActivity(), ListCActivity.class));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MainGuardar:
                Intent intent = new Intent(this.getActivity(), MapsActivityGuardar.class);
                intent.putExtra("lat",LocationService.getLatitud());
                intent.putExtra("long",LocationService.getLongitud());
                startActivity(intent);
                break;

            case R.id.MainVerUbicaciones:
                clickListLocations();
                break;
        }
    }

}
