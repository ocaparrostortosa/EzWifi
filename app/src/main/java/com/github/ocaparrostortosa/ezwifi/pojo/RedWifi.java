package com.github.ocaparrostortosa.ezwifi.pojo;

/**
 * Created by Oscar on 30/06/2017.
 * RedWifi is a POJO class to create new WiFi networks objects.
 * @author Oscar Caparros
 * @version 1.0
 */

public class RedWifi {

    private String lugar;
    private String nombre;
    private String clave;

    /**
     * RefWifi() main constructor. It contains getters and setters for all the attributes.
     * @param lugar The place were the wifi network is in.
     * @param nombre The name of the wifi network.
     * @param clave The password of the wifi network.
     */
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
