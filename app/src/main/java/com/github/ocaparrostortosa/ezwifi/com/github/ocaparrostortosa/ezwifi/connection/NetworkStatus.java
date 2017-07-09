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
 */

public class NetworkStatus extends ContextWrapper{

    public NetworkStatus(Context base, LoginActivity loginActivity) {
        super(base);
    }

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
