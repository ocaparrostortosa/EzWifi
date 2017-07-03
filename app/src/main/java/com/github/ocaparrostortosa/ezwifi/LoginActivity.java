package com.github.ocaparrostortosa.ezwifi;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Oscar on 30/06/2017.
 */

public class LoginActivity extends AppCompatActivity implements OnCompleteListener{

    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // mAuth = FirebaseAuth.getInstance();

       // getAccionBotonLogueo();
        getAccionRegistro();
/**
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Usuario logueado: ", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Usuario desconectado: ", "onAuthStateChanged:signed_out");
                }

            }
        };
 */

    }
/*
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void getAccionBotonLogueo() {
        String username = findViewById(R.id.editTextUsuario).toString();
        String password = findViewById(R.id.editTextClave).toString();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Se ha iniciado sesión: ", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("signInWithEmail:failed", task.getException());
                            Context context = getApplicationContext();
                            Toast.makeText(context , "ERROR al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
*/
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

    @Override
    public void onComplete(@NonNull Task task) {

    }
}
