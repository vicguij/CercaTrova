package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


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

        Intent intent = new Intent(this.getActivity(), MapsActivityCarga.class);
        intent.putExtra("latcoche", mAdapter.getCursor().getDouble(2));
        intent.putExtra("longcoche",mAdapter.getCursor().getDouble(3));
        intent.putExtra("latactual",LocationService.getLatitud());
        intent.putExtra("longactual",LocationService.getLongitud());

        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
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
                Toast.makeText(getActivity(),getString(R.string.selectdelete),Toast.LENGTH_SHORT).show();
                Log.d("Borrada", "posicion: " +info.position + " id: " + info.id);
                //Cuando se borra la ultima posicion, mAdapter count tiene valor 1
                if (mAdapter.getCount()==1){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.Opc2:
                Intent intent = new Intent(this.getActivity(), EditActivity.class);
                intent.putExtra("id", info.id);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
