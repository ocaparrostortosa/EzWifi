package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.LoginActivity;
import com.github.ocaparrostortosa.ezwifi.R;
import com.github.ocaparrostortosa.ezwifi.RegisterActivity;
import com.github.ocaparrostortosa.ezwifi.pojo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Oscar on 30/06/2017.
 */

public class UsuarioDAO extends AppCompatActivity{

    private FirebaseDatabase database;
    private Usuario usuario;
    private boolean success;
    private Button boton;
    private RegisterActivity registerActivity;
    private LoginActivity loginActivity;
    String userPath;
    DatabaseReference users;
    DatabaseReference emailRef;
    DatabaseReference usernameRef;
    DatabaseReference userPassRef;

    public UsuarioDAO(Usuario usuario){
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
    }

    public UsuarioDAO(FirebaseDatabase database){
        this.database = FirebaseDatabase.getInstance();
    }

    public UsuarioDAO(FirebaseDatabase database, Usuario usuario) {
        this.database = database;
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
    }

    public String getUserPath(Usuario usuario){
        return "users/"+usuario.getUsername();
    }

    public String getUserPath(String username){
        return "users/"+username;
    }

    private void crearUnUsuarioEnBD(Usuario usuario, Button boton, TextView estado, RegisterActivity registerActivity){
        userPath = this.getUserPath(usuario);

        emailRef = database.getReference(userPath+"/email/");
        usernameRef = database.getReference(userPath+"/username/");
        userPassRef = database.getReference(userPath+"/password/");

        emailRef.setValue(usuario.getEmail());
        usernameRef.setValue(usuario.getUsername());
        userPassRef.setValue(usuario.getPassword());

        System.out.println("Usuario creado!");
        boton.setEnabled(true);

        estado.setText("\n¡Usuario creado satisfactoriamente!:D\n");
        estado.setTextColor(Color.parseColor("#FF0FE300"));

        registerActivity.cambiarDeActivity(usuario.getUsername());
        //System.exit(0);
    }

    public void hacerLogin(final String username, final String contraseña, final LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        database = FirebaseDatabase.getInstance();

        users = database.getReference("users/" + username);
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Usuario u = dataSnapshot.getValue(Usuario.class);
                    System.out.println("Usuario logueado: " + u.toString());
                    if(u.getUsername().equals(username) && u.getPassword().equals(contraseña))
                        loginActivity.accionUsuarioCorrecto(u.getUsername());
                    else
                        loginActivity.accionUsuarioIncorrecto(username);
                }catch (NullPointerException e){
                    System.out.println("Catch de error al loguearse.");
                    loginActivity.accionUsuarioIncorrecto(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean esUsuarioYaExistente(final Usuario usuario, final Button boton, final TextView estado,
                                        final Context context, final RegisterActivity registerActivity){
        success = false;
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
                    System.out.println("Dntro: " + u.toString());
                    accionUsuarioInvalido(u.getUsername(), context, boton);

                }catch (NullPointerException e){

                    System.out.println("Procedemos a la creacion del usuario");
                    new UsuarioDAO(usuario).crearUnUsuarioEnBD(usuario, boton, estado, registerActivity);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return success;
    }

    private void accionUsuarioInvalido(String username, Context context, Button botonRegistro){
        boton.setEnabled(true);
        boton.setText("Registrarse");

        CharSequence texto = "El nombre '" + username + "' ya ha sido creado o es usado por otro usuario.";

        Toast toast = Toast.makeText(context, texto, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0,0);
        toast.show();

        botonRegistro.setText("Registrarse");
        botonRegistro.setEnabled(true);

    }


}
