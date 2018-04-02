package com.github.magloft.mlx.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.github.magloft.mlx.ExtensionBridge;
import com.github.magloft.mlx.ExtensionClient;

public class MainActivity extends Activity {
	private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

		// Inject MagLoft Extension
		ExtensionClient client = new ExtensionClient(MainActivity.this);
		ExtensionBridge bridge = new ExtensionBridge(MainActivity.this, webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(client);
		webView.addJavascriptInterface(bridge, "ExtensionBridge");

		// Load WebView
		webView.loadUrl("file:///android_asset/demo.html");
	}
}
