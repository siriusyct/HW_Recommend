package com.hw.recommend;

import android.app.Application;

import com.hw.actions.HWService;

/**
 * Created by zl on 2016/5/5.
 */
public class HWApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HWService.start(this);
    }
}
