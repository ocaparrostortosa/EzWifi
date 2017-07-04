package com.github.ocaparrostortosa.ezwifi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.UsuarioDAO;
import com.github.ocaparrostortosa.ezwifi.pojo.Usuario;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by Oscar on 01/07/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private UsuarioDAO usuarioDAO;
    private EditText usuarioRegistro;
    private EditText emailRegistro;
    private EditText claveRegistro;
    private TextView textoEstado;
    private Usuario usuario;
    private String username;
    private String email;
    private String clave;
    private Button botonRegistro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkCorrectInformation();
    }

    /**
     * Método que nos permite volver atrás para el botón de la flecha en la actionBar.
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }

    public RegisterActivity(){}

    private void checkCorrectInformation(){
        Button buttonRegistro = (Button) findViewById(R.id.botonRegistrarse);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getRegisterButtonAction();
            }
        });

    }

    private boolean isCorrectPassword(){
        claveRegistro = (EditText) findViewById(R.id.editTextClaveRegistro);
        EditText repetirClaveRegistro = (EditText) findViewById(R.id.editTextRepetirClaveRegistro);
        String clave = claveRegistro.getText().toString();
        String claveRepetida = repetirClaveRegistro.getText().toString();
        if(clave.equals(""))
            return false;
        if(clave.equals(claveRepetida))
            return true;
        else
            return false;

    }

    private boolean isCorrectEmail(){
        emailRegistro = (EditText) findViewById(R.id.editTextEmailRegistro);
        String email = emailRegistro.getText().toString();
        usuarioRegistro = (EditText) findViewById(R.id.editTextUsuarioRegistro);
        String username = usuarioRegistro.getText().toString();
        String correctEmail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        //usuarioDAO.getUserValues(username);
        /**
        if(usuarioDAO.esEmailYaExistente(username, email))
            return false;
         */
        if(email.matches(correctEmail))
            return true;
        else
            return false;
    }

    private boolean isCorrectUser(){
        botonRegistro.setEnabled(false);

        textoEstado = (TextView) findViewById(R.id.lineaEstadoRegistro);
        usuarioRegistro = (EditText) findViewById(R.id.editTextUsuarioRegistro);
        emailRegistro = (EditText) findViewById(R.id.editTextEmailRegistro);
        claveRegistro = (EditText) findViewById(R.id.editTextClaveRegistro);

        email = emailRegistro.getText().toString();
        username = usuarioRegistro.getText().toString();
        clave = claveRegistro.getText().toString();

        usuario = new Usuario(email, username, clave);

        usuarioDAO.esUsuarioYaExistente(usuario, botonRegistro, textoEstado, getApplicationContext(), this);

        /*

        //usuarioDAO.getUserValues(username);

        if(username.equals(""))
            return false;
        if(usuarioDAO.esUsuarioYaExistente(username))
            return false;*/
        return false;
    }

    private void getRegisterButtonAction(){
        textoEstado = (TextView) findViewById(R.id.lineaEstadoRegistro);
        botonRegistro = (Button) findViewById(R.id.botonRegistrarse);
        usuarioRegistro = (EditText) findViewById(R.id.editTextUsuarioRegistro);
        emailRegistro = (EditText) findViewById(R.id.editTextEmailRegistro);
        claveRegistro = (EditText) findViewById(R.id.editTextClaveRegistro);

        String username = usuarioRegistro.getText().toString();
        String email = emailRegistro.getText().toString();
        String clave = claveRegistro.getText().toString();
        usuario = new Usuario(email, username, clave);
        usuarioDAO = new UsuarioDAO(database, usuario);
        //usuarioDAO.getUserValues(username);

        if(isCorrectPassword()){
            textoEstado.setText("\n\n");
            if(isCorrectEmail()){
               textoEstado.setText("\n\n");
                if(isCorrectUser()) {
                    textoEstado.setText("\n¡Registro satisfactorio! :)\n");
                    textoEstado.setTextColor(Color.parseColor("#FF0FE300"));
                    botonRegistro.setText("Redireccionando...");
                    botonRegistro.setEnabled(false);
                }
            }else{
                textoEstado.setText("\n¡El e-mail no es válido! :(\n");
                textoEstado.setTextColor(Color.parseColor("#FF0000"));
                return;
            }

        }else{
            textoEstado.setText("\n¡Las contraseñas no coinciden o son inválidas! :(\n");
            textoEstado.setTextColor(Color.parseColor("#FF0000"));
            return;
        }

    }

    public void cambiarDeActivity(String username){
        Intent activityRegistro = new Intent(RegisterActivity.this, MainActivity.class);
        activityRegistro.putExtra("EXTRA_USERNAME", username);
        RegisterActivity.this.startActivity(activityRegistro);
        finish();

    }


}
