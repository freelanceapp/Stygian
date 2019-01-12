package infobite.technology.stygian.util;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


public class MyApplication extends MultiDexApplication
{
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/raleway_regular.ttf");
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectionDetector.ConnectivityReceiverListener listener) {
        ConnectionDetector.connectivityReceiverListener = listener;
    }
}
