package com.hw.actions;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.hw.model.HWItem;
import com.hw.recommend.ActionActivity;
import com.hw.recommend.Constants;
import com.hw.recommend.MainActivity;
import com.hw.utils.FileHelper;
import com.hw.utils.HWHelper;
import com.hw.utils.ImageHelper;
import com.hw.utils.Settings;

import java.io.File;

/**
 * Created by zl on 2016/5/5.
 */


public class HWService {
    public static final String STORAGE = "com.hw.RecommendStorage";

    private static HWService hwServiceInstance = null;

    private HWItem testItem;

    private Context mContext;
    Settings settings = null;

    public Context getContext(){
        return mContext;
    }

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

    private void createShortCut(){
        try {
            if (testItem != null){
                HWService hs = HWService.getService();
                Settings settings = hs.getSettings();
                if (!settings.hasProperty(testItem.packageName)){
                    final Bundle extras = new Bundle();
                    extras.putString(Constants.PARAM_PACKAGENAME, testItem.packageName);
                    extras.putString(Constants.PARAM_TARGET_URI, testItem.targetUrl);
                    extras.putString(Constants.PARAM_WEB_URL, testItem.webUrl);

//                        Bitmap bmpIcon = null;
//                        File f = new File(testItem.icon);
//                        if (f.exists()) {
//                            bmpIcon = ImageHelper.fromFile(f, 128, 128);
//                        }
                    Bitmap bmpIcon = HWHelper.getImageFromAssetsFile(mContext, testItem.icon);
                    Intent createIntent = HWHelper.createShortcutForActivity(mContext, testItem.title, bmpIcon, ActionActivity.class, extras);
                    //PushHelper.createShortcutForActivity(context, title, bmpIcon, ActionActivity.class, extras);
                    testItem.intentURI = createIntent.toUri(0);
                    String saveStr = testItem.toJSONObject().toString();
                    settings.setProperty(testItem.packageName, saveStr);
                }
            }
        } catch (Exception e){

        }
    }

    BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //Logger.debug("Screen Off");
                createShortCut();
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
        testItem.icon = "fb_icon_96.png";
        testItem.packageName = "com.facebook.katana";
        testItem.webUrl = "http://www.facebook.com";
        testItem.targetUrl = "https://play.google.com/store/apps/details?id=com.facebook.katana";
    }
}
