package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao;

import com.github.ocaparrostortosa.ezwifi.pojo.RedWifi;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Oscar on 30/06/2017.
 */

public class DatosWifiDAO {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private RedWifi redWifi;
    private String username;
    private UsuarioDAO userDAO;
    private String userPath;
    private Long numeroDeClave;
    private long numeroTotalDeClaves;

    DatabaseReference lugarRef;
    DatabaseReference nombreRef;
    DatabaseReference claveRef;
    DatabaseReference wifiElementsRef;

    public DatosWifiDAO(RedWifi redWifi, String username) {
        this.redWifi = redWifi;
        this.username = username;
        setWifiParams(redWifi);
    }

    private void setWifiParams(RedWifi redWifi){
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
                    numeroTotalDeClaves = dataSnapshot.getChildrenCount();
                    createNewWifiElement(numeroTotalDeClaves);
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
    }

    private void createNewWifiElement(Long numeroTotal){
        numeroDeClave = numeroTotal;
        numeroDeClave++;

        lugarRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/lugarWifi");
        nombreRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/nombreWifi");
        claveRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/claveWifi");

        lugarRef.setValue(redWifi.getLugar());
        nombreRef.setValue(redWifi.getNombre());
        claveRef.setValue(redWifi.getClave());

        /**
         * YA HAS CONSEGUIDO OBTENER EL NUMERO DE CLAVES POR USUARIO Y AHORA SOLO TE FALTA IMPRIMIR LAS CLAVES
         * EN EL MENU DE CONSULTAR WIFI.
         */
    }
}
