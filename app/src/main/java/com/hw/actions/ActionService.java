package com.hw.actions;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


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
//		            runRewardAction(appId, TradeServiceImpl.REWARD_ACTION_INSTALL);
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
