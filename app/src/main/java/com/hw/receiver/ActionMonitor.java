package com.hw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hw.actions.ActionService;


public class ActionMonitor extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent i) {		

		try {
			if (Intent.ACTION_PACKAGE_ADDED.equals(i.getAction())) {
				String app = i.getDataString().substring("package:".length());
//				Logger.debug("App Added : " + app);
				
//				ts.executeAppEventMsg(app, null, TradeServiceImpl.REWARD_ACTION_INSTALL);
//				ts.executeGo2CenterAppEventMsg(app, null, TradeServiceImpl.REWARD_ACTION_INSTALL);
//				ts.eventWebInstallAppHdl(app);
//
//				if (ts.getPushHandler() != null){
//					if (ts.getPushHandler().getPushService() != null)
//						ts.getPushHandler().getPushService().changeAppStatus(app, Constants.APP_STATUS_INSTALLED);
//				}
//				Settings settings = ts.getSettings();
//				if (!settings.hasProperty(app) || settings.getIntProperty(app, -1) != TradeServiceImpl.REWARD_ACTION_DOWNLOAD)
//					return;

				Intent intent = new Intent(ActionService.ACTION_APP_ADDED);
		        intent.setClass(context, ActionService.class);
		        intent.putExtra(ActionService.PARAM_APP_ID, app);
		        context.startService(intent);
			} 
			if (Intent.ACTION_PACKAGE_REPLACED.equals(i.getAction())) {
				String app = i.getDataString().substring("package:".length());
//				Logger.debug("App Replaced : " + app);
//				ts.getPushHandler().getPushService().changeAppStatus(app, Constants.APP_STATUS_INSTALLED);
			}
			if (Intent.ACTION_BOOT_COMPLETED.equals(i.getAction())){
//				ts.reportEvent("boot", null);
			}
			Intent intent = new Intent(ActionService.ACTION_KEEP_ACTIVE);
	        intent.setClass(context, ActionService.class);
	        context.startService(intent);
		} catch (Exception e) {
//			Logger.error(e);
		}
	}
}
