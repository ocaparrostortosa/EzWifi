package com.github.ocaparrostortosa.ezwifi.pojo;

/**
 * Created by Oscar on 30/06/2017.
 */

public class RedWifi {

    private String lugar;
    private String nombre;
    private String clave;

    public RedWifi(String lugar, String nombre, String clave) {
        this.lugar = lugar;
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getLugar() {
        return lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
