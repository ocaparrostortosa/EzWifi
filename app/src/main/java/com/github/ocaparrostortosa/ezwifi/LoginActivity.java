package com.github.ocaparrostortosa.ezwifi;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.connection.NetworkStatus;
import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Oscar on 30/06/2017.
 */

public class LoginActivity extends AppCompatActivity{

    private FirebaseDatabase database;
    private UsuarioDAO usuarioDAO;
    private Button botonLogin;
    //FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    System.out.println("Sesion iniciada: " + user.getEmail());
                }else {
                    System.out.println("Sesion cerrada.");
                }
            }
        };
        */

        // ToolBarIcon code
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.my_icon);
        getSupportActionBar().setTitle("Bienvenido a EzWifi");
        //
        getAccionRegistro();
        clickOnLoginButton();
    }

    private void getAccionRegistro(){
        TextView textoRegistro = (TextView) findViewById(R.id.lineaRegistrarse);
        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityRegistro = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(activityRegistro);

                /**
                 * Intent activityConsultar = new Intent(MainActivity.this, ActivityConsultar.class);

                 MainActivity.this.startActivity(activityConsultar);
                 */
            }
        });

    }

    private void getAccionBotonLogueo(){
        isNetworkAvailable();

        EditText username = (EditText) findViewById(R.id.editTextUsuario);
        EditText password = (EditText) findViewById(R.id.editTextClave);

        String nombreUsuario = username.getText().toString();
        String claveUsuario = password.getText().toString();

        System.out.println(nombreUsuario + ":" + claveUsuario);
        /**
        FirebaseAuth.getInstance().signInWithEmailAndPassword(nombreUsuario,claveUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("Inicio de sesion correcta");
                }else{
                    System.out.println("Mal inicio de sesion" + task.getException().getMessage());
                }
            }
        });
         */

        database = FirebaseDatabase.getInstance();

        botonLogin.setEnabled(false);
        botonLogin.setText("Cargando...");

        usuarioDAO = new UsuarioDAO(database);
        usuarioDAO.hacerLogin(nombreUsuario, claveUsuario, this);
    }

    public void accionUsuarioCorrecto(String username){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("EXTRA_USERNAME", username);
        LoginActivity.this.startActivity(i);
        finish();

    }

    public void accionUsuarioIncorrecto(String username){
        Toast toast = Toast.makeText(getApplicationContext(), "El usuario o la contraseña son incorrectas.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER , 0, 0);
        toast.show();
        botonLogin.setEnabled(true);
        botonLogin.setText("Login");

    }

    private void clickOnLoginButton(){
        botonLogin = (Button) findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccionBotonLogueo();
            }
        });
    }

    private void isNetworkAvailable(){
        //Saber si hay conexion a internet disponible
        NetworkStatus.isNetworkAvailable(getApplicationContext(), this);
    }
}
