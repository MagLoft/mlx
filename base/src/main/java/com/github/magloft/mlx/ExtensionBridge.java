package com.github.magloft.mlx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.Map;

public class ExtensionBridge {
    private final Activity activity;
    private final WebView webView;

    public ExtensionBridge(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public void request(String json) {
        Gson gson = new Gson();
        final ExtensionAction action = gson.fromJson(json, ExtensionAction.class);
        ExtensionManager.getInstance().performAction(activity, action.action, action.data, new ExtensionCallback() {
            @SuppressLint("NewApi")
            @Override
            public void onSuccess(Map<String, Object> response) {
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(response);
                String script = "MagLoftApi.resolve(" + action.requestId + ", " + jsonResponse + ");";
                webView.evaluateJavascript(script, null);
            }

            @SuppressLint("NewApi")
            @Override
            public void onError(Exception error) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(error);
                String script = "MagLoftApi.reject(" + action.requestId + ", " + jsonString + ");";
                webView.evaluateJavascript(script, null);
            }
        });
    }
}
