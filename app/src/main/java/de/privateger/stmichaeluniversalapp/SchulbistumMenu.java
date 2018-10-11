package de.privateger.stmichaeluniversalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class SchulbistumMenu extends AppCompatActivity {

    private WebView mWebView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schulbistum_menu);

        mWebView = findViewById(R.id.schulbistumView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("https://www.schulbistum.de/wws/9.php#/wws/100001.php");

        if(Constants.production) {
            MobileAds.initialize(this, "ca-app-pub-4511153742871799~9189394157");
            mAdView = findViewById(R.id.adView);
            final AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}