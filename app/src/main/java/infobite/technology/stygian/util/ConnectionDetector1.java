package infobite.technology.stygian.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import infobite.technology.stygian.R;


public class ConnectionDetector1 {
    Context mContext;

    public ConnectionDetector1(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void show(Context context ){
        Toast.makeText(context, context.getResources().getString(R.string.connection), Toast.LENGTH_SHORT).show();
    }
}