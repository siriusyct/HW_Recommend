package com.hw.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.hw.recommend.Constants;

/**
 * Created by zl on 2016/5/5.
 */
public class HWHelper {

    public static void createShortcut(Context context, String title, Bitmap bmpIcon, Intent i){
        Intent installer = new Intent();
        installer.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        installer.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        if (bmpIcon != null)
            installer.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmpIcon);
        installer.putExtra("duplicate", false);
        installer.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.sendBroadcast(installer);
    }

    public static void createShortcutForActivity(Context context, String title, Bitmap largeIcon, Class<?> activityClass, Bundle extraParams){
        extraParams.putBoolean(Constants.PARAM_SHORTCUT, true);
        Intent notificationIntent = new Intent(context, activityClass);
        notificationIntent.putExtras(extraParams);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        createShortcut(context, title, largeIcon, notificationIntent);
    }

    public static void uninstallShortcut(Context context, String title, Intent i){
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        shortcut.putExtra("duplicate", false);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        context.sendBroadcast(shortcut);
    }

    public static void uninstallShortcutForActivity(Context context, String title, Class<?> activityClass, Bundle extraParams){
        extraParams.putBoolean(Constants.PARAM_SHORTCUT, true);
        Intent notificationIntent = new Intent(context, activityClass);
        notificationIntent.putExtras(extraParams);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        uninstallShortcut(context, title, notificationIntent);
    }
}
