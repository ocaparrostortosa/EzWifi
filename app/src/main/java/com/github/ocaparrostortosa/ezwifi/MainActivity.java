package com.github.ocaparrostortosa.ezwifi;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    }

    private void setInformationText(){
        TextView information = (TextView) findViewById(R.id.informationTextView);
        information.setText("\n** Guardar contraseña te permitirá guardar los datos de una nueva red WiFi. \n" +
                            "** Consultar contraseñas te mostrará todos los datos de redes WiFi guardadas hasta ahora.");
    }

    private void getButtonsActions(){
        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        Button botonConsultar = (Button) findViewById(R.id.botonConsultar);
        Button botonLogin = (Button) findViewById(R.id.botonLogin);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityGuardar = new Intent(MainActivity.this, ActivityGuardar.class);
                MainActivity.this.startActivity(activityGuardar);
            }
        });

        botonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityConsultar = new Intent(MainActivity.this, ActivityConsultar.class);

                MainActivity.this.startActivity(activityConsultar);
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityLogin = new Intent(MainActivity.this, LoginActivity.class);

                MainActivity.this.startActivity(activityLogin);
            }
        });
    }

}
