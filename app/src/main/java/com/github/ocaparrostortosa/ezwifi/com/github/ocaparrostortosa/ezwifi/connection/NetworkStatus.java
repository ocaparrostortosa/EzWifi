package com.github.ocaparrostortosa.ezwifi.com.github.ocaparrostortosa.ezwifi.connection;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.github.ocaparrostortosa.ezwifi.LoginActivity;

/**
 * Created by Oscar on 09/07/2017.
 *
 * NetworkStatus class checks if the app has a Internet network available to connect to the database.
 * @author Oscar Caparros
 * @version 1.0
 */

public class NetworkStatus extends ContextWrapper{

    /**
     * NetworkStatus() is the main constructor.
     * @param base App context.
     * @param activity Activity to be checked.
     */
    public NetworkStatus(Context base, Activity activity) {
        super(base);
    }

    /**
     * isNetworkAvailable() is the main method of the NetworkStatus class.
     * @param context
     * @param activity
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context, Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean networkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if(networkAvailable) {
            return true;
        }
        else {
            InternetDialog.bloquearAplicacionSinInternet(activity);
            return false;
        }
    }
}
