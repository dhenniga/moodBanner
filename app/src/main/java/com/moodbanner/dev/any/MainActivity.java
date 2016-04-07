package com.moodbanner.dev.any;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.moodbanner.dev.any.Create.CreateNew;

public class MainActivity extends AppCompatActivity {

    private Typeface archistico;


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Access", "Permission is granted");
                return true;
            } else {

                Log.v("Access","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Access","Permission is granted");
            return true;
        }

    }

    int screenWidth, screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface archistico = Typeface.createFromAsset(getAssets(), "fonts/archistico.ttf");
        Typeface RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Light.ttf");

        TextView tvAppName;
        Button createNew, editExisting, viewGallery, btnInstagramPortrait, btnInstagramLandscape, btnInstagramSquare;

        createNew = (Button) findViewById(R.id.btnCreate);

        btnInstagramPortrait = (Button) findViewById(R.id.btnInstagramPortrait);
        btnInstagramPortrait.setTypeface(RalewayLight);
        btnInstagramLandscape = (Button) findViewById(R.id.btnInstagramLandscape);
        btnInstagramLandscape.setTypeface(RalewayLight);
        btnInstagramSquare = (Button) findViewById(R.id.btnInstagramSquare);
        btnInstagramSquare.setTypeface(RalewayLight);


        editExisting = (Button) findViewById(R.id.btnEdit);
        viewGallery = (Button) findViewById(R.id.btnGallery);
        tvAppName = (TextView) findViewById(R.id.tvAppName);


        isStoragePermissionGranted();

        tvAppName.setTypeface(archistico);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                startActivity(intent);
            }
        });


        btnInstagramPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                screenHeight = 1800;
                intent.putExtra("screenHeight", screenHeight);
                startActivity(intent);
            }
        });

        btnInstagramLandscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                screenHeight = 755;
                intent.putExtra("screenHeight", screenHeight);
                startActivity(intent);
            }
        });

        btnInstagramSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                screenHeight = 1440;
                intent.putExtra("screenHeight", screenHeight);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Access","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }



}
