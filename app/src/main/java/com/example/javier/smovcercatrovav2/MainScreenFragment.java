package com.example.javier.smovcercatrovav2;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by javier on 8/12/17.
 */

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

    // Se inserta un registro a la base de datos
    public void clickInsert() {

        double latitud = LocationService.getLatitud();
        double longitud = LocationService.getLongitud();
        Log.d("BOTON INSERT", "Insertando coordenadas: "+longitud+", "+latitud);

        // Cogemos un identificador unico: un timestamp del sistema
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long id = timestamp.getTime();

        // Sacamos el tiempo actual en formato string
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Nombre de la coordenada a insertar
        String nombre = "Posición de prueba";

        Log.d("BOTON INSERT", "Insertando: "+id+", "+nombre+", "+date+", "+latitud+", "+longitud);

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
            Uri uri = this.getActivity().getContentResolver().insert(CoordinatesContract.CONTENT_URI, values);

        }catch (Exception e) {
            Log.d("BOTON INSERT", "EXCEPCION...");
        }

    }

    public void clickListLocations() {
        Log.d("BOTON LISTAR", "Listando las posiciones guardadas");
        //Se listan en el log
        Log.d("BOTON LIST", "Mostrando las coordenadas guardadas...");

        Cursor c = this.getActivity().getContentResolver().query(CoordinatesContract.CONTENT_URI, null, null, null, CoordinatesContract.DEFAULT_SORT);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String id = c.getString(0);
                String nombre = c.getString(1);
                String latitud = c.getString(2);
                String longitud = c.getString(3);
                String fecha = c.getString(4);
                Log.d("Registros:  ", "ID:"+id+", Nombre:"+nombre+", Latitud:"+latitud+", Longitud:"+longitud+", Fecha:"+fecha);
            } while(c.moveToNext());
        }

        startActivity(new Intent(this.getActivity(), ListCActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MainGuardar:
                //clickInsert();
                Intent intent = new Intent(this.getActivity(), MapsActivityGuardar.class);
                intent.putExtra("lat",LocationService.getLatitud());
                intent.putExtra("long",LocationService.getLongitud());
                Log.d("pinguino ninja", "son" +LocationService.getLatitud() + LocationService.getLongitud());
                startActivity(intent);
                break;

            case R.id.MainVerUbicaciones:
                clickListLocations();
                break;
    }

    }
}
