package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.connection;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Oscar on 09/07/2017.
 *
 * InternetDialog class was build to support the NetworkStatus class to show to the user information about the
 * network status
 */

public class InternetDialog extends AppCompatActivity{

    /**
     * bloquearAplicacionSinInternet() Blocks the app during a not connected time to an internet network and inform to the user.
     * @param activity Activity which in its self will be shown the dialog.
     */
    public static void bloquearAplicacionSinInternet(final Activity activity){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(activity);
        a_builder.setMessage("Enciende el WiFi o los datos móviles y vuelve a intentarlo.")
                .setCancelable(false)
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.recreate();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("¡No hay conexión a Internet! :(");
        alert.show();
    }
}
