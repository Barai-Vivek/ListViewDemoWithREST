package com.app.listviewdemowithrest.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.app.listviewdemowithrest.R;

public class InternetReachability {

    public static boolean hasConnection(Context context) {

        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

    public static void showConnectionErrorMessage(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.internet_err), Toast.LENGTH_SHORT).show();
    }
}