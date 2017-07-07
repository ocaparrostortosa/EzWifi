package com.github.ocaparrostortosa.ezwifi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.DatosWifiDAO;
import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.dao.UsuarioDAO;
import com.github.ocaparrostortosa.ezwifi.pojo.RedWifi;
import com.github.ocaparrostortosa.ezwifi.pojo.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Oscar on 29/06/2017.
 */

public class ActivityGuardar extends AppCompatActivity{

    private String username;

    //FireBase auth object
    FirebaseDatabase database;


    //UsuarioDAO
    UsuarioDAO usuarioDAO;
    DatosWifiDAO wifiDAO;

    /*
    private DatabaseReference userPass;
    private DatabaseReference userName;
    */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar);
        getSupportActionBar().setTitle("Guardar WiFi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = getIntent().getStringExtra("EXTRA_CURRENTUSER");
        getAccionBotonGuardar();

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

    private void getAccionBotonGuardar(){
        Button botonGuardarDatos = (Button) findViewById(R.id.botonGuardarDatos);
        botonGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ETlugar = (EditText) findViewById(R.id.editTextLugar);
                EditText ETnombre = (EditText) findViewById(R.id.editTextNombre);
                EditText ETclave = (EditText) findViewById(R.id.editTextClave);
                String lugar = ETlugar.getText().toString();
                String nombre = ETnombre.getText().toString();
                String clave = ETclave.getText().toString();
                if(lugar.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "¡ERROR! Ningún campo puede estar vacío.", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(nombre.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "¡ERROR! Ningún campo puede estar vacío.", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(clave.matches("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "¡ERROR! Ningún campo puede estar vacío.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    exitoEnGuardar(lugar, nombre, clave);
                }
            }
        });
    }

    /**
     * Método que nos muestra que los datos se han cogido correctamente y nos lo guarda en la base de datos.
     * @param lugar
     * @param nombre
     * @param clave
     */
    private void exitoEnGuardar(String lugar, String nombre, String clave){
        guardarDatosEnBD(lugar, nombre, clave);

        Context context = getApplicationContext();
        CharSequence text = "¡DATOS GUARDADOS!\n\n Lugar: " + lugar + "\n Nombre: " + nombre + "\n Clave: " + clave + "";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void guardarDatosEnBD(String lugar, String nombre, String clave){
        wifiDAO = new DatosWifiDAO(new RedWifi(lugar, nombre, clave), username, this);

    }
}
