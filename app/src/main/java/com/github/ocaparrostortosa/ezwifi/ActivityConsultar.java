package com.github.ocaparrostortosa.ezwifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Oscar on 29/06/2017.
 */

public class ActivityConsultar extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        getSupportActionBar().setTitle("Consultar WiFi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
