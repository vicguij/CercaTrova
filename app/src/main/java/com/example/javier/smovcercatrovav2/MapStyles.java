package com.example.javier.smovcercatrovav2;

/*
Ingeniería Informática - Sistemas Móviles - 2017-2018
Cerca Trova
Javier Hernaz González
Victor Guijarro Esteban
*/

import android.app.Activity;
import android.content.SharedPreferences;


public class MapStyles {

    private static int itemCoche;
    private static int itemActual;
    private static String polyLineColor;
    private static int mapStyle;

    private static Activity activity;


    public static void setStyles(SharedPreferences prefs){

        // Se cogen la configuracion de las opciones
        String color = prefs.getString("aplication_color", "");
        String item_coche = prefs.getString("aplication_item_coche", "");
        String item_actual = prefs.getString("aplication_item_actual", "");
        String estilo_mapa = prefs.getString("aplication_tipo_mapa", "");

        int estilo_mapa_int = 0;
        if(estilo_mapa==null || estilo_mapa==""){
            estilo_mapa_int = 0;
        }else{
            estilo_mapa_int = Integer.parseInt(estilo_mapa);
        }
        // Se asigna el estilo del mapa
        setStyleMapa(estilo_mapa_int);

        int color_int = 0;
        if(color==null || color==""){
            color_int = 0;
        }else{
            color_int = Integer.parseInt(color);
        }
        //Se asigna el color
        setStyleColor(color_int);

        int item_coche_int = 0;
        if(item_coche==null || item_coche==""){
            item_coche_int = 0;
        }else{
            item_coche_int = Integer.parseInt(item_coche);
        }
        //Se asigna el item para el coche
        setStyleItemCoche(item_coche_int, color_int);

        int item_actual_int = 0;
        if(item_actual==null || item_actual==""){
            item_actual_int = 0;
        }else{
            item_actual_int = Integer.parseInt(item_actual);
        }
        //Se asigna el item para la posicion actual
        setStyleItemActual(item_actual_int, color_int);

    }


    public static void setStyleItemCoche(int tipo, int color){

        switch (tipo){
            case 0:
                //Por defecto se pone 0 y se deja el de pordefcto de google
                setEstiloItemCoche(0);
                break;
            case 1:
                //Coche1
                switch (color){
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.coche1rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.coche1);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.coche1rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.coche1azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.coche1amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.coche1gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.coche1verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.coche1morado);
                        break;
                }
                break;
            case 2:
                //Coche2
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.coche2rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.coche2);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.coche2rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.coche2azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.coche2amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.coche2gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.coche2verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.coche2morado);
                        break;
                }
                break;
            case 3:
                //Bandera
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.flagrojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.flag);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.flagrojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.flagazul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.flagamarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.flaggris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.flagverde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.flagmorado);
                        break;
                }
                break;
            case 7:
                //Marcador 1
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.pin1rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.pin1);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.pin1rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.pin1azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.pin1amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.pin1gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.pin1verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.pin1morado);
                        break;
                }
                break;
            case 8:
                //Marcador 2
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.pin2rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.pin2);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.pin2rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.pin2azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.pin2amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.pin2gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.pin2verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.pin2morado);
                        break;
                }
                break;
            case 9:
                //Marcador 3
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.pin3rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.pin3);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.pin3rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.pin3azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.pin3amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.pin3gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.pin3verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.pin3morado);
                        break;
                }
                break;
            case 10:
                //Marcador 4
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemCoche(R.drawable.pin4rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemCoche(R.drawable.pin4);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemCoche(R.drawable.pin4rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemCoche(R.drawable.pin4azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemCoche(R.drawable.pin4amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemCoche(R.drawable.pin4gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemCoche(R.drawable.pin4verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemCoche(R.drawable.pin4morado);
                        break;
                }
                break;
        }
    }


    public static void setStyleColor( int tipo){
        long color = 0;
        String hexColor="";
        switch (tipo){
            case 0:
                //Por defecto se deja en rojo
                color = getActivity().getResources().getColor(R.color.colorRojo);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 1:
                //Negro
                color = getActivity().getResources().getColor(R.color.colorNegro);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 2:
                //Rojo
                color = getActivity().getResources().getColor(R.color.colorRojo);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 3:
                //Azul
                color = getActivity().getResources().getColor(R.color.colorAzul);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 4:
                //Amarillo
                color = getActivity().getResources().getColor(R.color.colorAmarillo);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 5:
                //Gris
                color = getActivity().getResources().getColor(R.color.colorGris);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 6:
                //Verde
                color = getActivity().getResources().getColor(R.color.colorVerde);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
            case 7:
                //Morado
                color = getActivity().getResources().getColor(R.color.colorMorado);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
                setPolyLineColor(hexColor);
                break;
        }
    }

    public static void setStyleItemActual( int tipo, int color){

        switch (tipo){
            case 0:
                //Por defecto
                setEstiloItemActual(0);
                break;
            case 3:
                // Bandera
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.flagrojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.flag);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.flagrojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.flagazul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.flagamarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.flaggris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.flagverde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.flagmorado);
                        break;
                }
                break;
            case 4:
                //Persona 1
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.person1rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.person1);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.person1rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.person1azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.person1amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.person1gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.person1verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.person1morado);
                        break;
                }
                break;
            case 5:
                //Persona 2
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.person2rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.person2);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.person2rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.person2azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.person2amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.person2gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.person2verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.person2morado);
                        break;
                }
                break;
            case 6:
                //Persona 3
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.person3rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.person3);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.person3rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.person3azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.person3amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.person3gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.person3verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.person3morado);
                        break;
                }
                break;
            case 7:
                //Marcador 1
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.pin1rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.pin1);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.pin1rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.pin1azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.pin1amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.pin1gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.pin1verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.pin1morado);
                        break;
                }
                break;
            case 8:
                //Marcador 2
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.pin2rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.pin2);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.pin2rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.pin2azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.pin2amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.pin2gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.pin2verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.pin2morado);
                        break;
                }
                break;
            case 9:
                //Marcador 3
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.pin3rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.pin3);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.pin3rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.pin3azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.pin3amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.pin3gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.pin3verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.pin3morado);
                        break;
                }
                break;
            case 10:
                //Marcador 4
                switch (color) {
                    case 0:
                        //Por defecto se deja en rojo
                        setEstiloItemActual(R.drawable.pin4rojo);
                        break;
                    case 1:
                        //Negro
                        setEstiloItemActual(R.drawable.pin4);
                        break;
                    case 2:
                        //Rojo
                        setEstiloItemActual(R.drawable.pin4rojo);
                        break;
                    case 3:
                        //Azul
                        setEstiloItemActual(R.drawable.pin4azul);
                        break;
                    case 4:
                        //Amarillo
                        setEstiloItemActual(R.drawable.pin4amarillo);
                        break;
                    case 5:
                        //Gris
                        setEstiloItemActual(R.drawable.pin4gris);
                        break;
                    case 6:
                        //Verde
                        setEstiloItemActual(R.drawable.pin4verde);
                        break;
                    case 7:
                        //Morado
                        setEstiloItemActual(R.drawable.pin4morado);
                        break;
                }
                break;
        }

    }

    public static void setStyleMapa(int tipo){
        // Se asigna el estilo del mapa
        switch (tipo){
            case 0:
                //Por defecto se deja el standard
                setMapStyle(R.raw.standar);
                break;
            case 1:
                //Standard
                setMapStyle(R.raw.standar);
                break;
            case 2:
                //Silver
                setMapStyle(R.raw.silver);
                break;
            case 3:
                //Retro
                setMapStyle(R.raw.retro);
                break;
            case 4:
                //Dark
                setMapStyle(R.raw.dark);
                break;
            case 5:
                //Night
                setMapStyle(R.raw.night);
                break;
            case 6:
                //Aubergine
                setMapStyle(R.raw.aubergine);
                break;
        }
    }

    public static int getEstiloItemCoche(){
        return itemCoche;
    }

    public static void setEstiloItemCoche( int estilo){
        itemCoche = estilo;
    }

    public static int getEstiloItemActual(){
        return itemActual;
    }

    public static void setEstiloItemActual( int estilo){
        itemActual = estilo;
    }

    public static String getPolylineColor(){
        return polyLineColor;
    }

    public static void setPolyLineColor( String color){
        polyLineColor = color;
    }

    public static int getMapStyle(){
        return mapStyle;
    }

    public static void setMapStyle( int estilo){
        mapStyle = estilo;
    }

    public static void setActivity( Activity act){
        activity = act;
    }

    public static Activity getActivity(){
        return activity;
    }

}
