package com.example.javier.smovcercatrovav2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private Button btnEditar;
    private EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edit = (EditText)findViewById(R.id.editText1);
        edit.requestFocus();
        if(edit.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        btnEditar = (Button)findViewById(R.id.botonEditar);
        btnEditar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickEdit();
            }
        });
    }

    public void clickEdit(){
        Bundle bundle = getIntent().getExtras();
        Long id= bundle != null ? bundle.getLong("id") : 0;
        String nombre = edit.getText().toString();
        if ((!nombre.isEmpty())&&(id!=0)){
            int num=0;
            try{
                ContentValues values = new ContentValues();

                // Meter en el content values
                values.clear();
                values.put(CoordinatesContract.Column.NOMBRE, nombre );

                //Se modifica utilizando el content provider
                num = getContentResolver().update(Uri.withAppendedPath(CoordinatesContract.CONTENT_URI, String.valueOf(id)), values, null, null );

            }catch (Exception e){
                Log.d("BOTON UPDATE", "EXCEPCION...");
            }

            Log.d("Update!","Actualizando el nombre de "+num+" tuplas.");

        }
        Intent intent = new Intent(this, ListCActivity.class);
        startActivity(intent);
        /*
    FORMA DE USO PARA CAMBIAR EL NOMBRE DE UNA COOORDENADA PASANDO EL ID:

        // Nuevo nombre de la la coordinada con el id de abajo
        String nuevo_nombre = "Update name";
        //Id del objeto a cambiar el nombre
        String id = "1514828341088";

        int num=0;
        try{
            ContentValues values = new ContentValues();

            // Meter en el content values
            values.clear();
            values.put(CoordinatesContract.Column.NOMBRE, nuevo_nombre );

            //Se modifica utilizando el content provider
            num = getContentResolver().update(Uri.withAppendedPath(CoordinatesContract.CONTENT_URI,id), values, null, null );

        }catch (Exception e){
            Log.d("BOTON UPDATE", "EXCEPCION...");
        }

        Log.d("Update!","Actualizando el nombre de "+num+" tuplas.");

     */
    }
}
