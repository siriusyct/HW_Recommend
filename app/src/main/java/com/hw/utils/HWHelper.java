package com.hw.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.hw.recommend.Constants;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zl on 2016/5/5.
 */
public class HWHelper {

    public static void createShortcut(Context context, String title, Bitmap bmpIcon, Intent i){
        Intent installer = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        installer.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        installer.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        if (bmpIcon != null)
            installer.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmpIcon);
        installer.putExtra("duplicate", false);
        //installer.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(installer);
    }

    public static Intent createShortcutForActivity(Context context, String title, Bitmap largeIcon, Class<?> activityClass, Bundle extraParams){
        extraParams.putBoolean(Constants.PARAM_SHORTCUT, true);
        Intent notificationIntent = new Intent(context, activityClass);
        notificationIntent.putExtras(extraParams);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createShortcut(context, title, largeIcon, notificationIntent);

        return notificationIntent;
    }

    public static void uninstallShortcut(Context context, String title, Intent i){
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        shortcut.putExtra("duplicate", false);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        context.sendBroadcast(shortcut);
    }

    public static void uninstallShortcut(Context context, String title, Bitmap bmpIcon, Intent i){
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        shortcut.putExtra("duplicate", false);
        if (bmpIcon != null)
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmpIcon);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        context.sendBroadcast(shortcut);
    }

    public static void uninstallShortcutForActivity(Context context, String title, Bitmap bmpIcon, Class<?> activityClass, Bundle extraParams){
        extraParams.putBoolean(Constants.PARAM_SHORTCUT, true);
        Intent notificationIntent = new Intent(context, activityClass);
        notificationIntent.putExtras(extraParams);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        uninstallShortcut(context, title, bmpIcon, notificationIntent);
    }

    public static Bitmap getImageFromAssetsFile(Context context, String fileName)
    {
        Bitmap image = null;
        try {
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public static Bitmap getImageFromAssetsFile(Context context, String fileName, int expectedWidth, int expectedHeight)
    {
        Bitmap image = null;
        try {
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            image = ImageHelper.fromStream(is, expectedWidth, expectedHeight);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
