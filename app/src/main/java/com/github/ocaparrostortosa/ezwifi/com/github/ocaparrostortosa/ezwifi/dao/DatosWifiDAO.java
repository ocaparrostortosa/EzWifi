package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.pojo.RedWifi;
import com.github.ocaparrostortosa.ezwifi.pojo.Usuario;
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
    private Usuario usuario;
    private UsuarioDAO userDAO;
    private String userPath;
    private int numeroDeClave;

    DatabaseReference mDataBase = database.getInstance().getReference();
    DatabaseReference lugarRef;
    DatabaseReference nombreRef;
    DatabaseReference claveRef;

    public DatosWifiDAO(RedWifi redWifi, Usuario usuario) {
        this.redWifi = redWifi;
        this.usuario = usuario;
        setWifiParams(redWifi);
    }

    private void setWifiParams(RedWifi redWifi){
        database = FirebaseDatabase.getInstance();

        numeroDeClave = 0;
        userDAO = new UsuarioDAO(database, usuario);
        userPath = userDAO.getUserPath(usuario);

        lugarRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/lugarWifi");
        nombreRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/nombreWifi");
        claveRef = database.getReference(userPath + "/clavesGuardadas/"+ numeroDeClave + "/claveWifi");

        lugarRef.setValue(redWifi.getLugar());
        nombreRef.setValue(redWifi.getNombre());
        claveRef.setValue(redWifi.getClave());

    }
}
