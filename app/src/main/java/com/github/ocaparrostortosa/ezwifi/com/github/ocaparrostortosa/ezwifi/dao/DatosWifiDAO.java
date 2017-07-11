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
 *
 * DatosWifiDao is the DAO class for the WiFi networks. I use it to create new WiFi elements, set the params ...
 * @author Oscar Caparros
 * @version 1.0
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

    /**
     * DatosWifiDAO() main constructor.
     */
    public DatosWifiDAO(){}

    /**
     * DatosWifiDAO is a second constructor who initialice the value for the params.
     * @param redWifi A new RedWifi() to save it in the database.
     * @param username Current username logued in.
     * @param activityGuardar Current ActivityGuardar to change graphic things.
     */
    public DatosWifiDAO(RedWifi redWifi, String username, ActivityGuardar activityGuardar) {
        this.activityGuardar = activityGuardar;
        this.redWifi = redWifi;
        this.username = username;
        setWifiParams();
    }

    /**
     * setWifiParams() get the UserPath to save in the FirebaseDatabase the user information.
     */
    private void setWifiParams(){
        database = FirebaseDatabase.getInstance();

        userDAO = new UsuarioDAO(database);
        userPath = userDAO.getUserPath(username);

        getNumberOfWifiElements();
    }

    /**
     * getNumberOfWifiElements() gives the number of wifi elements to control the app.
     */
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

    /**
     * createNewWifiElement(numeroTotal) Takes the total number of the wifi elements and create a new one.
     * @param numeroTotal Total number of the wifi elements in the database.
     */
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
    }
}
