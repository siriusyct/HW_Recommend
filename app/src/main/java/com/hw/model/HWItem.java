package com.hw.model;

import com.hw.recommend.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zl on 2016/5/5.
 */
public class HWItem {
    public String packageName;
    public String icon;
    public String title;
    public String webUrl;
    public String targetUrl;
    public String intentURI;

    public JSONObject toJSONObject(){
        try {
            JSONObject obj = new JSONObject();
            obj.put(Constants.PARAM_PACKAGENAME, this.packageName == null ? "" : this.packageName);
            obj.put(Constants.PARAM_ICON, this.icon == null ? "" : this.icon);
            obj.put(Constants.PARAM_TITLE, this.title == null ? "" : this.title);
            obj.put(Constants.PARAM_WEB_URL, this.webUrl == null ? "" : this.webUrl);
            obj.put(Constants.PARAM_TARGET_URI, this.targetUrl == null ? "" : this.targetUrl);
            obj.put(Constants.PARAM_INTENT, this.intentURI == null ? "" : this.intentURI);
            return obj;
        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return null;
    }

    public void initWithJSONObject(JSONObject obj){
        if (this == null || obj == null)
            return;
        try {
            String temp = obj.getString(Constants.PARAM_PACKAGENAME);
            if (temp != null){
                this.packageName = temp;
            }

            temp = obj.getString(Constants.PARAM_TITLE);
            if (temp != null){
                this.title = temp;
            }

            temp = obj.getString(Constants.PARAM_ICON);
            if (temp != null){
                this.icon = temp;
            }

            temp = obj.getString(Constants.PARAM_WEB_URL);
            if (temp != null){
                this.webUrl = temp;
            }

            temp = obj.getString(Constants.PARAM_TARGET_URI);
            if (temp != null){
                this.targetUrl = temp;
            }

            temp = obj.getString(Constants.PARAM_INTENT);
            if (temp != null){
                this.intentURI = temp;
            }
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }
}
