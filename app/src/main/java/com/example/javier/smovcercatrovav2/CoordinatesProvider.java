package com.example.javier.smovcercatrovav2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class CoordinatesProvider extends ContentProvider {

    private static final String TAG = CoordinatesProvider.class.getSimpleName();
    private DbHelper dbHelper;
    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(CoordinatesContract.AUTHORITY, CoordinatesContract.TABLE,
                CoordinatesContract.STATUS_DIR);
        sURIMatcher.addURI(CoordinatesContract.AUTHORITY, CoordinatesContract.TABLE + "/#",
                CoordinatesContract.STATUS_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        Log.d(TAG, "onCreated");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case CoordinatesContract.STATUS_DIR:
                where = s;
                break;
            case CoordinatesContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = CoordinatesContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(s) ? "" : " and ( " + s + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        String orderBy = (TextUtils.isEmpty(s1)) ? CoordinatesContract.DEFAULT_SORT : s1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CoordinatesContract.TABLE, strings, where, strings1, null, null,
                orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "registros recuperados: " + cursor.getCount());
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert");
        Uri ret = null;
        // Nos aseguramos de que la URI es correcta
        if (sURIMatcher.match(uri) != CoordinatesContract.STATUS_DIR) {
            throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(CoordinatesContract.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        // ¿Se insertó correctamente?
        if (rowId != -1) {
            long id = contentValues.getAsLong(CoordinatesContract.Column.ID);
            ret = ContentUris.withAppendedId(uri, id);
            Log.d(TAG, "uri insertada: " + ret);
            // Notificar que los datos para la URI han cambiado
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ret;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String where;
        switch (sURIMatcher.match(uri)) {
            case CoordinatesContract.STATUS_DIR:
                where = s;
                break;
            case CoordinatesContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = CoordinatesContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(s) ? "" : " and ( " + s + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(CoordinatesContract.TABLE, where, strings);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros borrados: " + ret);
        return ret;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case CoordinatesContract.STATUS_DIR:
                where = s;
                break;
            case CoordinatesContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = CoordinatesContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(s) ? "" : " and ( " + s + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(CoordinatesContract.TABLE, contentValues, where, strings);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros actualizados: " + ret);
        return ret;
    }

}
