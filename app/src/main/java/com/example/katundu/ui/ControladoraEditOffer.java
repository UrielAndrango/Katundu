package com.example.katundu.ui;

import android.net.Uri;

public class ControladoraEditOffer {
    //Vector de fotos
    private static int numero_imagen = -1;
    private static int cantidad_fotos = 0;
    private static int numero_maximo_fotos = 5;
    private static Uri[] fotos = new Uri[numero_maximo_fotos];
    //Parametros del usuario
    private static String nombre_producto = "Audi A4";
    private static int numero_Categoria = 6;
    private static String valor = "10000";
    private static boolean es_servicio = true;
    private static String palabras_clave = "A4";
    private static String descripcion = "Audi A4 2006 3.0";

    public static int getNumero_imagen() {
        return numero_imagen;
    }

    public static void setNumero_imagen(int numero_imagen) {
        ControladoraEditOffer.numero_imagen = numero_imagen;
    }

    public static int getCantidad_fotos() {
        return cantidad_fotos;
    }

    public static void setCantidad_fotos(int cantidad_fotos) {
        ControladoraEditOffer.cantidad_fotos = cantidad_fotos;
    }

    public static int getNumero_maximo_fotos() {
        return numero_maximo_fotos;
    }

    public static Uri[] getFotos() {
        return fotos;
    }

    public static void setFotos(Uri[] foto) {
        ControladoraEditOffer.fotos = foto;
    }

    public static Uri obtener_foto(int i) {
        //"i" empieza en 0
        Uri foto = fotos[i];
        return foto;
    }

    public static void add_foto(Uri photo, int pos) {
        //"i" empieza en 0
        fotos[pos] = photo;
        ++cantidad_fotos;
    }

    public static void borrar_foto(int i) {
        //"i" empieza en 0
        fotos[i] = null;
        if (cantidad_fotos > 0) --cantidad_fotos;
    }

    public static void reset() {
        //reset fotos
        ControladoraEditOffer.numero_imagen = 1;
        ControladoraEditOffer.cantidad_fotos = 0;
        ControladoraEditOffer.fotos = new Uri[numero_maximo_fotos];
        //reset datos
        ControladoraEditOffer.nombre_producto = "";
        ControladoraEditOffer.numero_Categoria = 0;
        ControladoraEditOffer.valor = "";
        ControladoraEditOffer.es_servicio = false;
        ControladoraEditOffer.palabras_clave = "";
        ControladoraEditOffer.descripcion = "";
    }

    public static void reordenar_fotos() {
        Uri[] actualizada = new Uri[numero_maximo_fotos];
        int new_pos = 0;
        for (int i=0; i<numero_maximo_fotos; ++i) {
            if (fotos[i] != null) {
                actualizada[new_pos] = fotos[i];
                ++new_pos;
            }
        }
        fotos = actualizada;
    }

    public static String getNombre_producto() {
        return nombre_producto;
    }

    public static void setNombre_producto(String nombre_producto) {
        ControladoraEditOffer.nombre_producto = nombre_producto;
    }

    public static int getNumero_Categoria() {
        return numero_Categoria;
    }

    public static void setNumero_Categoria(int numero_Categoria) {
        ControladoraEditOffer.numero_Categoria = numero_Categoria;
    }

    public static String getValor() {
        return valor;
    }

    public static void setValor(String valor) {
        ControladoraEditOffer.valor = valor;
    }

    public static boolean isEs_servicio() {
        return es_servicio;
    }

    public static void setEs_servicio(boolean es_servicio) {
        ControladoraEditOffer.es_servicio = es_servicio;
    }

    public static String getPalabras_clave() {
        return palabras_clave;
    }

    public static void setPalabras_clave(String palabras_clave) {
        ControladoraEditOffer.palabras_clave = palabras_clave;
    }

    public static String getDescripcion() {
        return descripcion;
    }

    public static void setDescripcion(String descripcion) {
        ControladoraEditOffer.descripcion = descripcion;
    }
}