package com.example.katundu.ui;

public class ControladoraPresentacio {
    private static String username = "Bobby";
    private static String nom_real = "Bob";
    private static String password = "12345678";
    private static String latitud = "0.0";
    private static String longitud = "0.0";

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
}