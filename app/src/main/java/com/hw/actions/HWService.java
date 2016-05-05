package com.hw.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hw.model.HWItem;
import com.hw.utils.Settings;

/**
 * Created by zl on 2016/5/5.
 */


public class HWService {
    public static final String STORAGE = "com.hw.RecommendStorage";

    private static HWService hwServiceInstance = null;

    private HWItem testItem;

    private Context mContext;
    Settings settings = null;
    public Settings getSettings(){
        if (settings == null)
            settings = new Settings(mContext, STORAGE);
        return settings;
    }

    public static HWService getService(){
        return hwServiceInstance;
    }

    public static HWService start(Context context){
        if (hwServiceInstance == null){
            hwServiceInstance = new HWService(context);
        }
        return hwServiceInstance;
    }

    BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //Logger.debug("Screen Off");

            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                try{
                    //Logger.debug("Screen On");

                } catch(Exception e){
                    //Logger.error(e);
                }
            }
        }
    };

    public HWService(Context context){
        mContext = context;
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(screenReceiver, filter);

        testItem = new HWItem();
        testItem.title = "FB Web";
        testItem.icon = "file:///android_asset/fb_icon.png";
        testItem.packageName = "com.facebook.katana";
    }
}
