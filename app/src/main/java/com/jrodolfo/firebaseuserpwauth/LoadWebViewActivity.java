package com.jrodolfo.firebaseuserpwauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class LoadWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web_view);

        WebView myWebView = findViewById(R.id.webview);
        myWebView.loadUrl("https://jrodolfo.com");
    }
}
