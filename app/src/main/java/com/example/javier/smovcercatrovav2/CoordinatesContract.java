package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.net.Uri;
import android.provider.BaseColumns;


public class CoordinatesContract {

    // Nombre del fichero de la base de datos
    public static final String DB_NAME = "appsmov2017.db";
    // Versión de la base de datos
    public static final int DB_VERSION = 1;
    // Nombre de la tabla
    public static final String TABLE = "coordenadas";
    // Para ordenar los resultados de las queries
    public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC";

    // Constantes del content provider
    public static final String AUTHORITY = "com.example.javier.smovcercatrovav2.CoordinatesProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;

    // Campos de la base de datos
    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String NOMBRE = "nombre";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String CREATED_AT = "fecha";

    }

}
