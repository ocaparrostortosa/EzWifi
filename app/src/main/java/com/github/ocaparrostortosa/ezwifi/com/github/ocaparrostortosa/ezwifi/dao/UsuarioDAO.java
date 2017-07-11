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
 *
 * UsuarioDAO class is the DAO class for the users. It creates new users in the database and check the already existing users.
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

    /**
     * UsuarioDAO() main constructor.
     * @param usuario new Usuario() to be saved.
     */
    public UsuarioDAO(Usuario usuario){
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
    }

    /**
     * UsuarioDAO() constructor to get the instance of the database.
     * @param database Firebase database.
     */
    public UsuarioDAO(FirebaseDatabase database){
        this.database = FirebaseDatabase.getInstance();
    }

    public UsuarioDAO(FirebaseDatabase database, Usuario usuario) {
        this.database = database;
        this.usuario = usuario;
        this.database = FirebaseDatabase.getInstance();
    }

    /**
     * getUserPath() returns the user database path.
     * @param usuario an Usuario() object.
     * @return String
     */
    public String getUserPath(Usuario usuario){
        return "users/"+usuario.getUsername();
    }

    public String getUserPath(String username){
        return "users/"+username;
    }

    /**
     * crearUnUsuarioEnBD() creates a new user in the database.
     * @param usuario Usuario() object.
     * @param boton Button to set graphic params.
     * @param estado TextView to set user information.
     * @param registerActivity RegisterActivity() to set graphic params.
     */
    private void crearUnUsuarioEnBD(Usuario usuario, Button boton, TextView estado, RegisterActivity registerActivity){
        userPath = this.getUserPath(usuario);

        emailRef = database.getReference(userPath+"/email/");
        usernameRef = database.getReference(userPath+"/username/");
        userPassRef = database.getReference(userPath+"/password/");

        emailRef.setValue(usuario.getEmail());
        usernameRef.setValue(usuario.getUsername());
        userPassRef.setValue(usuario.getPassword());

        boton.setEnabled(true);

        estado.setText("\n¡Usuario creado satisfactoriamente!:D\n");
        estado.setTextColor(Color.parseColor("#FF0FE300"));

        registerActivity.cambiarDeActivity(usuario.getUsername());
    }

    /**
     * hacerLogin() starts the login with the especific user.
     * @param username String User name to log in.
     * @param contraseña String User password.
     * @param loginActivity LoginActivity() to change the Intent.
     */
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

    /**
     * esUsuarioYaExistente() checks if the user who wants to register already exists.
     * @param usuario Usuario() object.
     * @param boton Button to set graphic settings.
     * @param estado TextView to set user information.
     * @param context Current context.
     * @param registerActivity LoginActivity() to change graphic settings.
     * @return boolean
     */
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
                    System.out.println("¿Usuario ya existente?: " + u.toString());
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

    /**
     * accionUsuarioInvalido() contains the code to say to the user that this user is invalid or already exists.
     * @param username String user name.
     * @param context Current context for graphic settings.
     * @param botonRegistro Button for graphic settings.
     */
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
