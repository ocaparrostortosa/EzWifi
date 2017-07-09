package com.github.ocaparrostortosa.ezwifi;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.connection.NetworkStatus;


public class MainActivity extends AppCompatActivity {

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ToolBarIcon code
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.my_icon);
        getSupportActionBar().setTitle("Bienvenido a EzWifi");
        //
        setInformationText();
        getButtonsActions();

        printCurrentUser();


    }

    private void setInformationText(){
        TextView information = (TextView) findViewById(R.id.informationTextView);
        information.setText("\n** Guardar contraseña te permitirá guardar los datos de una nueva red WiFi. \n" +
                            "** Consultar contraseñas te mostrará todos los datos de redes WiFi guardadas hasta ahora.");
    }

    private void getButtonsActions(){
        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        Button botonConsultar = (Button) findViewById(R.id.botonConsultar);

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
    }

    private void printCurrentUser(){
        TextView bienvenida = (TextView) findViewById(R.id.textViewBienvenida);
        currentUser = getIntent().getStringExtra("EXTRA_USERNAME");
        bienvenida.setText("¡Hola " + currentUser + "!\n¿Qué deseas hacer?");
    }
}
