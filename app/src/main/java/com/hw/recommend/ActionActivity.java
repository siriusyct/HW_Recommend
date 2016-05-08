package com.hw.recommend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hw.actions.HWService;
import com.hw.model.HWItem;
import com.hw.utils.HWHelper;
import com.hw.utils.Settings;

import org.json.JSONObject;

public class ActionActivity extends AppCompatActivity {

    private WebView wvView;
    private WebView hideWebView;
    private Button btnInstall;
    private RelativeLayout rlAction;
    Context mContext;

    private String webUrl;
    private String targetUri;
    private String packageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        wvView = (WebView)findViewById(R.id.wvView);
        hideWebView = (WebView)findViewById(R.id.hideWebView);
        btnInstall = (Button)findViewById(R.id.btnInstall);
        rlAction = (RelativeLayout)findViewById(R.id.rlAction);

        mContext = this;
        Bundle params = this.getIntent().getExtras();
        if (params == null){
            //this.finish();
            wvView.setVisibility(View.GONE);
            rlAction.setVisibility(View.GONE);
            return;
        }

        packageName = params.getString(Constants.PARAM_PACKAGENAME);
        webUrl = params.getString(Constants.PARAM_WEB_URL);
        targetUri = params.getString(Constants.PARAM_TARGET_URI);

        hideWebView.getSettings().setJavaScriptEnabled(true);

        wvView.getSettings().setJavaScriptEnabled(true);
        wvView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                wvView.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                wvView.loadUrl("file:///android_asset/error.html");
            }
        });

        wvView.loadUrl(webUrl);

        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
                //openBrowserByHideWebView();
                //openGooglePlay();
                //removeShortCut("com.facebook.katana");
            }
        });
    }

    @Override
    public void onBackPressed(){

        if (wvView != null){
            if (wvView.canGoBack()){
                wvView.goBack();
                return;
            }
        }

        super.onBackPressed();
    }

    private void openBrowserByHideWebView(){
        wvView.loadUrl(targetUri);
    }

    private void openBrowser(){
        Intent  intent = new  Intent(Intent.ACTION_VIEW, Uri.parse(targetUri));
        startActivity(intent);
    }


    private void openGooglePlay(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetUri));
        browserIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }

    private void openMarket(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        startActivity(intent);
    }

    private void removeShortCut(String appId){
        HWService hs = HWService.getService();
        Settings settings = hs.getSettings();
        if (settings.hasProperty(appId)){
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
                //Bitmap bmpIcon = HWHelper.getImageFromAssetsFile(hs.getContext(), item.icon);
                //HWHelper.uninstallShortcutForActivity(hs.getContext(), item.title, bmpIcon, ActionActivity.class, extras);
                //settings.setProperty(item.packageName, null);
            } catch (Exception e){

            }
        }
    }
}
