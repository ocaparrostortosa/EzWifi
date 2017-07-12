package com.github.ocaparrostortosa.ezwifi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * MainActivity is the main class for the project. Is not the first frame but its the initial menu for the user. It contains
 * some user information and the menu buttons.
 *
 * @author Óscar Caparrós
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBarIcon code
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("  Bienvenido a EzWifi");
        //
        setInformationText();
        getButtonsActions();

        printCurrentUser();


    }

    /**
     * setInformationText() is used to set the text information in the bottom of the activity.
     */
    private void setInformationText(){
        TextView information = (TextView) findViewById(R.id.informationTextView);
        information.setText("\n** Guardar contraseña te permitirá guardar los datos de una nueva red WiFi. \n" +
                            "** Consultar contraseñas te mostrará todos los datos de redes WiFi guardadas hasta ahora.");
    }

    /**
     * getButtonsActions() contains the action for the activity buttons.
     */
    private void getButtonsActions(){
        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        Button botonConsultar = (Button) findViewById(R.id.botonConsultar);
        TextView textoDisponible = (TextView) findViewById(R.id.disponibleProximamente);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityGuardar = new Intent(MainActivity.this, ActivityGuardar.class);
                activityGuardar.putExtra("EXTRA_CURRENTUSER", currentUser);
                MainActivity.this.startActivity(activityGuardar);
            }
        });

        botonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityConsultar = new Intent(MainActivity.this, ActivityConsultar.class);
                activityConsultar.putExtra("EXTRA_CURRENTUSER", currentUser);
                MainActivity.this.startActivity(activityConsultar);
            }
        });

        textoDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialogoInformacion();
            }
        });
    }

    /**
     * printCurrentUser() add to the top of the activity a text with a greeting to the user.
     */
    private void printCurrentUser(){
        TextView bienvenida = (TextView) findViewById(R.id.textViewBienvenida);
        currentUser = getIntent().getStringExtra("EXTRA_USERNAME");
        bienvenida.setText("¡Hola " + currentUser + "!\n¿Qué deseas hacer?");
    }

    /**
     * crearDialogoInformacion() contains the code for set an Dialog which bring to the user some information about the future of the app.
     */
    private void crearDialogoInformacion(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);

        a_builder.setMessage("*Email: La contraseña y tus datos serán enviados en un email.\n\n" +
                            "*Claves disponibles: Los datos de las redes WiFi almacenadas en el registro de tu dispositivo podrán añadirse" +
                                " a tu usuario de Firebase.\n\n" +
                            "*Seguridad: Las claves ya no serán almacenadas en un servidor público, sino que necesitaras autorización mediante registro" +
                            " para poder guardarlas y verlas.\n\n" +
                            "*Idioma: Se podrá elegir entre el Español y el Inglés entre los idiomas de la aplicación.\n\n" +
                            "*¡Y mucho mas! (Botón buscar, almacenamiento local de credenciales...)")
                .setCancelable(false)
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Próximamente...");
        alert.show();
    }
}
