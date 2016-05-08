package com.hw.actions;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;

import com.hw.model.HWItem;
import com.hw.recommend.ActionActivity;
import com.hw.recommend.Constants;
import com.hw.utils.HWHelper;
import com.hw.utils.Settings;

import org.json.JSONObject;

public class ActionService extends Service{	
	public static final String ACTION_KEEP_ACTIVE = "ACTION_KEEP_ACTIVE";
	public static final String ACTION_APP_ADDED = "ACTION_APP_ADDED";
	
	public static final String PARAM_ID = "ID";
	public static final String PARAM_APP_ID = "APP_ID";
	public static final String PARAM_URL = "URL";
	public static final String PARAM_DELAY = "DELAY";
	public static final String PARAM_DOWNLOAD_ID = "DOWNLOAD_ID";
	public static final String PARAM_SMS_NUMBER = "SMS_NUMBER";
	public static final String PARAM_SMS_MESSAGE = "SMS_MESSAGE";

	private void removeShortCut(String appId){
		HWService hs = HWService.getService();
		Settings settings = hs.getSettings();
		if (!settings.hasProperty(appId)){
			String saveDataStr = settings.getStringProperty(appId, null);
			try {
				JSONObject obj = new JSONObject(saveDataStr);
				HWItem item = new HWItem();
				item.initWithJSONObject(obj);

				final Bundle extras = new Bundle();
				extras.putString(Constants.PARAM_PACKAGENAME, item.packageName);
				extras.putString(Constants.PARAM_TARGET_URI, item.targetUrl);
				extras.putString(Constants.PARAM_WEB_URL, item.webUrl);
				Intent removeIntent =  Intent.parseUri(item.intentURI, 0);
				HWHelper.uninstallShortcut(hs.getContext(), item.title, removeIntent);
				//settings.setProperty(item.packageName, null);

				// /Users/jizhai_zl/Documents/Android/sdk/platform-tools/
			} catch (Exception e){

			}
		}
	}
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onStart(Intent intent, int startId) {
    	if (intent != null){
    		try{
    			String action = intent.getAction();
		        //Logger.debug("ActionService action: " + action);
		        if (ACTION_APP_ADDED.equals(action)) {
		            String appId = intent.getStringExtra(PARAM_APP_ID);
					removeShortCut(appId);
		        } else if (ACTION_KEEP_ACTIVE.equals(action)) {
		            //Logger.debug("Keep Active ...");
		        }
    		}catch(Exception e){
    			//Logger.error(e);
    		}
    	}
    }

    void runRewardAction(String appId, int action){
    	//Logger.debug("Billing Service runRewardAction " + appId + ":" + action + " / ");
//        if (TradeService.getInstance() == null){
//        	TradeService.start(this.getApplication(), null, null, null);
//        }
//        TradeServiceImpl ts = (TradeServiceImpl)TradeService.getInstance();
//        ts.runRewardAction(appId, null, null, action);
    }
	@Override
	public void onCreate() {
//		Logger.debug("ActionService onCreate");
		super.onCreate();
	}
	@Override
	public void onDestroy() {
//		Logger.debug("ActionService onDestroy");
		super.onDestroy();
	}
	@Override
	public void onLowMemory() {
//		Logger.debug("ActionService onLowMemory");
		super.onLowMemory();
	}


}
