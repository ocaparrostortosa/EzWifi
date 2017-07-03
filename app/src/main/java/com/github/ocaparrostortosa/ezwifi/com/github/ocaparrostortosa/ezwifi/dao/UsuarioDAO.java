package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.R;
import com.github.ocaparrostortosa.ezwifi.RegisterActivity;
import com.github.ocaparrostortosa.ezwifi.pojo.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Oscar on 30/06/2017.
 */

public class UsuarioDAO extends AppCompatActivity{

    private FirebaseDatabase database;
    private Usuario usuario;
    private boolean success;
    private Button boton;
    String userPath;
    DatabaseReference users;
    DatabaseReference emailRef;
    DatabaseReference usernameRef;
    DatabaseReference userPassRef;

    public UsuarioDAO(Usuario usuario){
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
    }

    public UsuarioDAO(FirebaseDatabase database, Usuario usuario) {
        this.database = database;
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
        //setUserInformationInDB();
    }

    public String getUserPath(Usuario usuario){
        return "users/"+usuario.getUsername();
    }

    private void setUserInformationInDB(){
        userPath = this.getUserPath(usuario);
        emailRef = database.getReference(userPath+"/email/");
        usernameRef = database.getReference(userPath+"/username/");
        userPassRef = database.getReference(userPath+"/password/");

        emailRef.setValue(usuario.getEmail());
        usernameRef.setValue(usuario.getUsername());
        userPassRef.setValue(usuario.getPassword());
    }

    private void crearUnUsuarioEnBD(Usuario usuario, Button boton){
        userPath = this.getUserPath(usuario);

        emailRef = database.getReference(userPath+"/email/");
        usernameRef = database.getReference(userPath+"/username/");
        userPassRef = database.getReference(userPath+"/password/");

        emailRef.setValue(usuario.getEmail());
        usernameRef.setValue(usuario.getUsername());
        userPassRef.setValue(usuario.getPassword());

        System.out.println("Usuario creado!");
        boton.setEnabled(true);
    }

    public void getUserValues(String username){
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users/" + username);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                //System.out.println(u.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public boolean esUsuarioYaExistente(final Usuario usuario, final Context context, final Button boton){
        success = true;
        //
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users/" + usuario.getUsername());

        this.boton = boton;
        boton.setText("Cargando...");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Usuario u = dataSnapshot.getValue(Usuario.class);
                    System.out.println("Dntro: " + u.getPassword());
                    accionUsuarioInvalido(usuario.getUsername(), context);
                    return;
                }catch (NullPointerException e){
                    System.out.println("Procedemos a la creacion del usuario");
                    new UsuarioDAO(usuario).crearUnUsuarioEnBD(usuario, boton);
                    return;
                    /**
                    System.out.println("Has entrado en el catch de addValue.");
                    usuario.setPassword("");
                    System.out.println("Dentro: " + usuario.getPassword());
                    return;

                     * EL METODO FUNCIONA CORRECTAMENTE CUANDO EL USUARIO EXISTE, PERO CUANDO NO EXISTE
                     * SALTA EL CATCH Y NO PODEMOS SALIR DE ÉL PARA PODER SEGUIR CON EL PROGRAMA.
                     */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return success;
    }

    public boolean esEmailYaExistente(String username, String email){
        /**
         * YA HAS CONSEGUIDO OBTENER UN USUARIO DE LA BASE DE DATOS Y CREARLO SI NO LO ESTÁ AÚN,
         * AHORA DEBERÁS DE HACER LO MISMO PARA EL CORREO Y A LA VEZ QUE CREAS UN USUARIO ANTES CHEQUEAR
         * EL CORREO PARA SABER SI YA ESTÁ CREADO.
         */
        success = false;
        //
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users/" + username);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                //System.out.println(u.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //
        if(usuario.getPassword().equals("null"))
            success = false;
        return success;
    }

    private void accionUsuarioInvalido(String username, Context context){
        boton.setEnabled(true);
        boton.setText("Registrarse");

        System.out.println("Has entrado en accion usuario invalido");

        CharSequence text = "El usuario '" + username + "' ya ha sido creado o no está disponible.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();

        Button botonRegistro = (Button) findViewById(R.id.botonRegistrarse);
        botonRegistro.setText("Registrarse");
        botonRegistro.setEnabled(true);
    }


}
