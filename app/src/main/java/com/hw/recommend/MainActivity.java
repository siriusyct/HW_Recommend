package com.hw.recommend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if (createShotcut)
        //    PushHelper.createShortcutForActivity(context, title, bmpIcon, ActionActivity.class, extras);
    }
}
