package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao;

import android.content.Intent;

import com.github.ocaparrostortosa.ezwifi.ActivityConsultar;
import com.github.ocaparrostortosa.ezwifi.ActivityGuardar;
import com.github.ocaparrostortosa.ezwifi.pojo.RedWifi;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Oscar on 30/06/2017.
 */

public class DatosWifiDAO {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RedWifi redWifi;
    private String username;
    private UsuarioDAO userDAO;
    private String userPath;
    private static Long numeroDeClave = 0L;
    private ActivityGuardar activityGuardar;

    DatabaseReference lugarRef;
    DatabaseReference nombreRef;
    DatabaseReference claveRef;
    DatabaseReference wifiElementsRef;
    DatabaseReference setClavesGuardadas;
    DatabaseReference savedPasswords;

    public DatosWifiDAO(){}

    public DatosWifiDAO(RedWifi redWifi, String username, ActivityGuardar activityGuardar) {
        this.activityGuardar = activityGuardar;
        this.redWifi = redWifi;
        this.username = username;
        setWifiParams();
    }

    private void setWifiParams(){
        database = FirebaseDatabase.getInstance();

        userDAO = new UsuarioDAO(database);
        userPath = userDAO.getUserPath(username);

        getNumberOfWifiElements();
    }

    private void getNumberOfWifiElements(){
        wifiElementsRef = database.getReference(userPath);

        wifiElementsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("clavesGuardadas")) {
                    System.out.println("Numero de hijos: " + dataSnapshot.getChildrenCount());
                    numeroDeClave = dataSnapshot.getChildrenCount();
                    createNewWifiElement(numeroDeClave);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setClavesGuardadas = database.getReference(userPath + "/clavesGuardadas/clavesTotales");
        setClavesGuardadas.setValue(numeroDeClave);

    }

    private void createNewWifiElement(Long numeroTotal){
        numeroDeClave = numeroTotal;

        savedPasswords = database.getReference(userPath + "/clavesGuardadas/" + numeroDeClave);

        lugarRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/lugarWifi/");
        nombreRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/nombreWifi/");
        claveRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/claveWifi/");

        lugarRef.setValue(redWifi.getLugar());
        nombreRef.setValue(redWifi.getNombre());
        claveRef.setValue(redWifi.getClave());

        setClavesGuardadas = database.getReference(userPath + "/clavesGuardadas/clavesTotales");
        setClavesGuardadas.setValue(numeroTotal);

        /**
         * YA HAS CONSEGUIDO OBTENER EL NUMERO DE CLAVES POR USUARIO Y AHORA SOLO TE FALTA IMPRIMIR LAS CLAVES
         * EN EL MENU DE CONSULTAR WIFI.
         */
    }

    public static String getNumeroTotalDeClaves(){
        return numeroDeClave.toString();
    }
}
