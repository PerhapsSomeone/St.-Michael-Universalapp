package de.privateger.stmichaeluniversalapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.Executors;

public class lehrerKuerzel extends AppCompatActivity {

    private TextView lehrerkuerzelTextfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lehrer_kuerzel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lehrerkuerzelTextfield = findViewById(R.id.lehrerkuerzelTextfeld);

        lehrerkuerzelTextfield.setTypeface(Typeface.MONOSPACE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lädt...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        lehrerkuerzelTextfield.setText(Constants.getURLContent("https://stmichaeluniversal.ddns.net/lehrer.php"));
                        lehrerkuerzelTextfield.invalidate();
                    }
                });
            }
        });

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                lehrerkuerzelTextfield.setText(Constants.getURLContent("https://stmichaeluniversal.ddns.net/lehrer.php"));
                lehrerkuerzelTextfield.invalidate();
            }
        });
    }
}
