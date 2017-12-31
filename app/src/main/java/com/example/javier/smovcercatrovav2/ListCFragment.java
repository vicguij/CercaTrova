package com.example.javier.smovcercatrovav2;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * Created by javier on 19/12/17.
 */

public class ListCFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ListCFragment.class.getSimpleName();

    private static final int LOADER_ID = 42;

    private SimpleCursorAdapter mAdapter;

    private  String[] FROM = {
    CoordinatesContract.Column.NOMBRE, CoordinatesContract.Column.CREATED_AT, CoordinatesContract.Column.LONGITUD, CoordinatesContract.Column.LATITUD};
    private  int[] TO = {
            R.id.list_item_nombre, R.id.list_item_fecha, R.id.list_item_longitud, R.id.list_item_latitud};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_fragment,
                null, FROM, TO, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i != LOADER_ID)
        return null;
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(getActivity(), CoordinatesContract.CONTENT_URI, null, null,
                null, CoordinatesContract.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished with cursor: " + cursor.getCount());
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        // Mostramos un mensaje con el elemento pulsado
        Log.d("pinguino cachondot","pulsado"  + "posicion en la lista: "+mAdapter.getCursor().getPosition()
                +" longitud: " +mAdapter.getCursor().getString(3) +
                " latitud: " +mAdapter.getCursor().getString(2));
        //     Toast.makeText(getActivity(), "Ha pulsado lat: "+ /*+mAdapter.getCursor().getPosition()*/
        //            mAdapter.getCursor().getString(2) + "long: "+mAdapter.getCursor().getString(3),
        //           Toast.LENGTH_SHORT).show();
        Log.d("pinguino mortal", "coords actual  son" +LocationService.getLatitud() + LocationService.getLongitud());
        Log.d("pinguino emperador", "coords coche son"+ "latcoche"+ mAdapter.getCursor().getString(2) +"longcoche" + mAdapter.getCursor().getString(3));
        Intent intent = new Intent(this.getActivity(), MapsActivityCarga.class);
        //Double latcoche = mAdapter.getCursor().getDouble(2);
        intent.putExtra("latcoche", mAdapter.getCursor().getDouble(2));
        intent.putExtra("longcoche",mAdapter.getCursor().getDouble(3));
        intent.putExtra("latactual",LocationService.getLatitud());
        intent.putExtra("longactual",LocationService.getLongitud());


        startActivity(intent);
        //mAdapter.getCursor().getString(mAdapter.getCursor().getPosition()) -->
        //Posicion 2 == latitud
        //Posicion 3 == longitud
        //la lista empieza en 0
        //Ver como sacar las coordenadas a partir de esta posición.
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
       // registerForContextMenu(getActivity().findViewById(android.R.id.list));
        registerForContextMenu(getListView());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_contex, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.Opc1:
                getActivity().getContentResolver().delete(Uri.withAppendedPath(CoordinatesContract.CONTENT_URI, String.valueOf(info.id)), null, null);
                Toast.makeText(getActivity(),"Posición Borrada!",Toast.LENGTH_SHORT).show();
                Log.d("Borrada", "posicion: " +info.position + " id: " + info.id);
                //Log.d("madapter", "posicion: " +mAdapter.getCount());
                //Cuando se borra la ultima posicion, mAdapter count tiene valor 1
                if (mAdapter.getCount()==1){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.Opc2:
                //deleteNote(info.id);
                //Ver si se puede implementar el metodo update y editarlo de forma sencilla --> Si no, borrar esta opción.
                Toast.makeText(getActivity(),"Pulsado2!",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
