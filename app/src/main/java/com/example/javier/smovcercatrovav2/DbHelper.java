package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    // TAG para el log
    private static final String TAG = DbHelper.class.getSimpleName();

    // Constructor
    public DbHelper(Context context) {
        super(context, CoordinatesContract.DB_NAME, null, CoordinatesContract.DB_VERSION);
    }

    // Llamado para crear la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table if not exists %s ( %s INTEGER primary key, %s TEXT, %s REAL, %s REAL, %s TEXT)",
                CoordinatesContract.TABLE,
                CoordinatesContract.Column.ID,
                CoordinatesContract.Column.NOMBRE,
                CoordinatesContract.Column.LATITUD,
                CoordinatesContract.Column.LONGITUD,
                CoordinatesContract.Column.CREATED_AT);
        Log.d(TAG, "onCreate con SQL: " + sql);
        db.execSQL(sql);
    }

    // Llamado siempre que tengamos una nueva version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aqui irían las sentencias del tipo ALTER TABLE, de momento lo hacemo mas sencillo...
        // Borramos la vieja base de datos
        db.execSQL("drop table if exists " + CoordinatesContract.TABLE);

        // Creamos una base de datos nueva
        onCreate(db);
        Log.d(TAG, "onUpgrade");
    }
}