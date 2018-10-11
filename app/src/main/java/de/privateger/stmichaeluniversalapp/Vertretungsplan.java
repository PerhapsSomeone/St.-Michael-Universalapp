package de.privateger.stmichaeluniversalapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Vertretungsplan extends AppCompatActivity {

    private WebView mWebView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertretungsplan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.gymnasium-sankt-michael.de/schule/vertretungsplan/");

        if(Constants.production) {
            MobileAds.initialize(this, "ca-app-pub-4511153742871799~9189394157");
            mAdView = findViewById(R.id.adView);
            final AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        mWebView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    mWebView.evaluateJavascript("document.body.innerHTML = document.getElementById(\"box_1\").innerHTML;", null);
                }
        });
    }

    public void updateData(View view) {


    }
}
