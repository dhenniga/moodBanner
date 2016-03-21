package com.moodbanner.dev.any;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Typeface archistico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Typeface archistico = Typeface.createFromAsset(getAssets(), "fonts/archistico.ttf");

        TextView tvAppName;
        Button createNew, editExisting, viewGallery;

        createNew = (Button) findViewById(R.id.btnCreate);
        editExisting = (Button) findViewById(R.id.btnEdit);
        viewGallery = (Button) findViewById(R.id.btnGallery);
        tvAppName = (TextView) findViewById(R.id.tvAppName);


        tvAppName.setTypeface(archistico);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                startActivity(intent);
            }
        });

        editExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditExisting.class);
                startActivity(intent);
            }
        });

        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Gallery.class);
                startActivity(intent);
            }
        });

    }

}
