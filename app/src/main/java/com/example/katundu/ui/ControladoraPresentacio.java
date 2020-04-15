package com.example.katundu.ui;

import android.net.Uri;

import java.util.ArrayList;

public class ControladoraPresentacio {
    //ATRIBUTS USER
    private static String username = "testusername";
    private static String nom_real = "testname";
    private static String password = "password";
    private static String latitud = "0.0";
    private static String longitud = "0.0";
    private static String distanciaMaxima = "10.0";

    //idioma usuario (temporal)
    private static String idioma = "";

    //ATRIBUTS add product
    private static double valoracion = 4;
    private static int numero_imagen = -1;
    private static int cantidad_fotos = 0;
    private static int numero_maximo_fotos = 5;
    private static Uri[] fotos = new Uri[numero_maximo_fotos];

    //ATRIBUTS WISH
    private static ArrayList<Wish> wish_list = new ArrayList<>();
    private static String wish_id = "wish_id";
    private static String wish_name = "Audi";
    private static int wish_categoria = 5;
    private static boolean wish_service = false;
    private static String wish_PC = "Coche";
    private static Integer value = 0;

    //ATRIBUTS OFFER
    private static ArrayList<Offer> offer_list = new ArrayList<>();
    private static String offer_id = "wish_id";
    private static String offer_name = "Audi";
    private static Integer offer_categoria = 5;
    private static boolean offer_service = false;
    private static String offer_PC = "Coche";
    private static Integer offer_value = 0;


    //GET & SET DE USER
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ControladoraPresentacio.username = username;
    }

    public static String getNom_real() {
        return nom_real;
    }

    public static void setNom_real(String nom_real) {
        ControladoraPresentacio.nom_real = nom_real;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ControladoraPresentacio.password = password;
    }

    public static String getLatitud() {
        return latitud;
    }

    public static void setLatitud(String latitud) {
        ControladoraPresentacio.latitud = latitud;
    }

    public static String getLongitud() {
        return longitud;
    }

    public static void setLongitud(String longitud) {
        ControladoraPresentacio.longitud = longitud;
    }

    public static String getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public static void setDistanciaMaxima(String distanciaMaxima) {
        ControladoraPresentacio.distanciaMaxima = distanciaMaxima;
    }

    public static double getValoracion() {
        return valoracion;
    }

    public static void setValoracion(double valoracion) {
        ControladoraPresentacio.valoracion = valoracion;
    }


    //GET & SET add product
    public static int getNumero_imagen() {
        return numero_imagen;
    }

    public static void setNumero_imagen(int numero_imagen) {
        ControladoraPresentacio.numero_imagen = numero_imagen;
    }

    public static int getCantidad_fotos() {
        return cantidad_fotos;
    }

    public static void setCantidad_fotos(int cantidad_fotos) {
        ControladoraPresentacio.cantidad_fotos = cantidad_fotos;
    }

    public static int getNumero_maximo_fotos() {
        return numero_maximo_fotos;
    }

    public static Uri[] getFotos() {
        return fotos;
    }

    public static void setFotos(Uri[] foto) {
        ControladoraPresentacio.fotos = foto;
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

    public static void reset_fotos() {
        //"i" empieza en 0
        numero_imagen = 1;
        cantidad_fotos = 0;
        fotos = new Uri[numero_maximo_fotos];
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


    //GET & SET DE OFFER
    public static Offer getOffer_perName(String offer_name) {
        boolean trobat = false;
        Offer info_offer = new Offer();
        for(int i = 0; i < offer_list.size() & !trobat; ++i){
            System.out.println(offer_list.get(i).getName());
            System.out.println(offer_name);
            if(offer_list.get(i).getName().equals(offer_name)){
                trobat = true;
                info_offer = offer_list.get(i);
                System.out.println(trobat);
            }
        }
        return info_offer;
    }

    public static void setOffer_List(ArrayList<Offer> offer_list) {
        ControladoraPresentacio.offer_list = offer_list;
    }

    public static String getOffer_name() {
        return offer_name;
    }

    public static void setOffer_name(String offer_name) {
        ControladoraPresentacio.offer_name = offer_name;
    }

    public static int getOffer_Categoria() {
        return offer_categoria;
    }

    public static void setOffer_Categoria(Integer categoria) {
        ControladoraPresentacio.offer_categoria = categoria;
    }

    public static boolean isOffer_Service() {
        return offer_service;
    }

    public static void setOffer_Service(boolean service) {
        ControladoraPresentacio.offer_service = service;
    }

    public static String getOffer_PC() {
        return offer_PC;
    }

    public static void setOffer_PC(String offer_PC) {
        ControladoraPresentacio.offer_PC = offer_PC;
    }

    public static String getOffer_id() {
        return offer_id;
    }

    public static void setOffer_id(String offer_id) {
        ControladoraPresentacio.offer_id = offer_id;
    }


    public static Integer getOffer_Value() {
        return offer_value;
    }

    public static void setOffer_Value(Integer value) {
        ControladoraPresentacio.offer_value = value;
    }



    //GET & SET DE WISH
    public static ArrayList<Wish> getWish_List() {
        return wish_list;
    }

    public static Wish getWish_perName(String wish_name) {
        Wish info_wish = new Wish();
        boolean trobat = false;

        for(int i = 0; i < wish_list.size() & !trobat; ++i){
            if(wish_list.get(i).getName().equals(wish_name)){
                trobat = true;
                info_wish = wish_list.get(i);
            }
        }
        return info_wish;
    }



    public static void setWish_List(ArrayList<Wish> wish_list) {
        ControladoraPresentacio.wish_list = wish_list;
    }

    public static String getWish_name() {
        return wish_name;
    }

    public static void setWish_name(String wish_name) {
        ControladoraPresentacio.wish_name = wish_name;
    }

    public static int getWish_Categoria() {
        return wish_categoria;
    }

    public static void setWish_Categoria(int categoria) {
        ControladoraPresentacio.wish_categoria = categoria;
    }

    public static boolean isWish_Service() {
        return wish_service;
    }

    public static void setWish_Service(boolean service) {
        ControladoraPresentacio.wish_service = service;
    }

    public static String getWish_PC() {
        return wish_PC;
    }

    public static void setWish_PC(String wish_PC) {
        ControladoraPresentacio.wish_PC = wish_PC;
    }

    public static String getIdioma() {
        return idioma;
    }

    public static void setIdioma(String idioma) {
        ControladoraPresentacio.idioma = idioma;
    }

    public static String getWish_id() {
        return wish_id;
    }


    public static void setWish_id(String wish_id) {
        ControladoraPresentacio.wish_id = wish_id;
    }

    public static Integer getWish_Value() {
        return value;
    }

    public static void setWish_Value(Integer value) {
        ControladoraPresentacio.value = value;
    }

    public static void afegir_offer_id(String toString) {
    }
}
