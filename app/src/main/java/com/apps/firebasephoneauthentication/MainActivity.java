package com.apps.firebasephoneauthentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(haveNetwork()) {
                    Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    alertDialog(getString(R.string.network_unavailable));
                }
            }
        }, SPLASH_TIME_OUT);
    }

    //===============================================| Check Internet Connection
    private boolean haveNetwork() {
        boolean have_Wifi = false;
        boolean have_MobileData = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        for (NetworkInfo info : infos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    have_Wifi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    have_MobileData = true;
                }
            }
        }
        return have_Wifi | have_MobileData;
    }

    //====================================================| Alert Message
    public void alertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(haveNetwork()) {
                            dialog.dismiss();
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            alertDialog(getString(R.string.network_unavailable));
                        }
                    }
                }).show();
    }
}
