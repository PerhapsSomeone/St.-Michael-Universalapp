package de.privateger.stmichaeluniversalapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.UUID;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = findViewById(R.id.imageView);

        mImageView.setImageResource(R.drawable.stmichaelfront);

        if(Constants.production) {
            MobileAds.initialize(this, "ca-app-pub-4511153742871799~9189394157");
            mAdView = findViewById(R.id.adView);
            final AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {

                String deviceModel = android.os.Build.MODEL;
                String androidVersion = Build.VERSION.RELEASE;
                String device = Build.DEVICE;

                String deviceUUID = null;

                File path = getApplicationContext().getFilesDir();
                File file = new File(path, ".stmichaeluuid");

                if(file.exists()) {
                    int length = (int) file.length();

                    byte[] bytes = new byte[length];

                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        in.read(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    deviceUUID = new String(bytes);
                } else {
                    deviceUUID = UUID.randomUUID().toString();
                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        stream.write(deviceUUID.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //file.delete();

                try {
                    String url = "https://stmichaeluniversal.ddns.net/analytics.php?deviceType=" + URLEncoder.encode(device, "UTF-8") + "&androidVersion=" +    URLEncoder.encode(androidVersion, "UTF-8") + "&deviceModel=" +  URLEncoder.encode(deviceModel, "UTF-8") + "&uuid=" + URLEncoder.encode(deviceUUID, "UTF-8");

                    URL analyticsURL = new URL(url);

                    URLConnection conn = analyticsURL.openConnection();
                    InputStream is = conn.getInputStream();

                    Log.i("St. Michael", "URL: " + url);
                } catch (UnsupportedEncodingException e) {
                    Log.e("St. Michael", "Unsupported encoding");
                } catch (MalformedURLException e) {
                    Log.e("St. Michael", "Malformed URL");
                } catch (IOException e) {
                    Log.e("St. Michael", "IOException");
                }
            }

            private void writeToFile(String data, Context context) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(".stmichaeluuid", Context.MODE_PRIVATE));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("St. Michael", "File write failed: " + e.toString());
                }
            }

            private String readFromFile(Context context) {

                String ret = "";

                try {
                    InputStream inputStream = context.openFileInput(".stmichaeluuid");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("St. Michael", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("St. Michael", "Can not read file: " + e.toString());
                }

                return ret;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void loadVertretungsplan(View view) {
        Intent vertIntent = new Intent(getBaseContext(), Vertretungsplan.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    public void loadHomepage(View view) {
        Intent vertIntent = new Intent(getBaseContext(), Homepage.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    public void loadSchulbistum(View view) {
        Intent vertIntent = new Intent(getBaseContext(), SchulbistumMenu.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    public void loadLehrerkuerzel(View view) {
        Intent vertIntent = new Intent(getBaseContext(), lehrerKuerzel.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    public void loadMensa(View view) {
        Intent vertIntent = new Intent(getBaseContext(), mensa_bestellung.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    public void loadPageTwo(View view) {
        Intent vertIntent = new Intent(getBaseContext(), MainActivityPage2.class);
        vertIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(vertIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
