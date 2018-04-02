package com.github.magloft.mlx;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

public class ExtensionClient extends WebViewClient {
    private final Activity activity;

    public ExtensionClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        webView.loadUrl("javascript:(function() { "
                + "var script = document.createElement('script');"
                + "script.setAttribute('type','text/javascript');"
                + "script.setAttribute('src', 'asset://MagLoftApi.js');"
                + "document.body.appendChild(script);"
                + "})();");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (request.getUrl().equals("asset://MagLoftApi.js")) {
            try {
                return new WebResourceResponse("text/javascript", "utf-8", activity.getAssets().open("MagLoftApi.js"));
            } catch (IOException ignored) {
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    @Deprecated
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.equals("asset://MagLoftApi.js")) {
            try {
                return new WebResourceResponse("text/javascript", "utf-8", activity.getAssets().open("MagLoftApi.js"));
            } catch (IOException ignored) {
            }
        }
        return super.shouldInterceptRequest(view, url);
    }
}
