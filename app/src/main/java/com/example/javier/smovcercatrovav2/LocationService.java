package com.example.javier.smovcercatrovav2;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static com.google.android.gms.location.LocationServices.*;


public class LocationService extends IntentService implements LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    static final String TAG = "LocationService";

    private LocationRequest locRequest;

    private static boolean activo = false;
    private static boolean runFlag = false;

    private static double latitud;
    private static double longitud;

    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    private static GoogleApiClient apiClient;
    private static AppCompatActivity activity;
    SharedPreferences prefs;

    public static void setActivity(AppCompatActivity main) {
        activity = main;
    }

    public static boolean isActivo() {
        return activo;
    }

    public static void setLocation(Location loc) {
        if(loc!=null) {
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            Log.d(TAG, "Longitud: " + longitud + "   Latitud: " + latitud);
        }
    }

    public static double getLatitud() {
        return latitud;
    }

    public static double getLongitud() {
        return longitud;
    }

    public LocationService() {
        super(TAG);
    }

    public static GoogleApiClient getApliClient(){
        return apiClient;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
        activo = false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        runFlag = true;

        Log.d(TAG, "onStarted");
        Log.d(TAG, "Activando Location Updates con el tiempo de settings");

        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();

        // COGER TIEMPO DE LOS SETTINGS
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        String interval_str = prefs.getString("update_interval", "");

        // ###################################################
        // VALIDAR QUE SOLO SE ESCRIBAN NUMEROS Y NO CADENAS DE TEXTO

        long interval;
        long interval_min = 1;
        if (interval_str == "") {
            // Si no hay configuración, se pone por defecto 2 segundos
            interval = 2;
        } else {
            interval = Integer.parseInt(interval_str);
        }

        interval = interval * 1000;

        if(interval>1000){
            interval_min = interval - 1000;
        }else{
            interval_min = 1000;
        }

        Log.d(TAG, "Intervalo recogido int:  " + interval);

        locRequest = new LocationRequest();
        locRequest.setInterval(interval);
        locRequest.setFastestInterval(interval_min);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(TAG, "Configuración correcta");
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(TAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(activity, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            // Poner toast ###### MIRAR #######
                            Log.i(TAG, "Error al intentar solucionar configuración de ubicación");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "No se puede cumplir la configuración de ubicación necesaria");
                        // Poner toast  ###### MIRAR #######
                        break;
                }
            }
        });
        while (runFlag) {
            Log.i(TAG, "Actualizando Location!");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            if(lastLocation!=null){
                setLocation(lastLocation);
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(TAG, "Inicio de recepción de ubicaciones");

            Log.i(TAG, "activo == true");
            activo = true;

            FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, this);
        }
    }

    public void disableLocationUpdates() {

        FusedLocationApi.removeLocationUpdates(
                apiClient, this);

        Log.i(TAG, "activo == false");
        activo = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runFlag=false;
        activo = false;
        // Se desactivan location updates
        Log.d(TAG, "onDestroyed");
        if(apiClient.isConnected()) {
            disableLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        boolean conectada = apiClient.isConnected();
        Log.i(TAG, "Recibida nueva ubicación! Conexion:"+conectada);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services
        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.
        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }
}
