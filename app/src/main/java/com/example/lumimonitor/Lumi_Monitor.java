package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class Lumi_Monitor extends AppCompatActivity implements SurfaceHolder.Callback {

    // Button Declarations
    ImageButton lightButton;
    ImageButton musicButton;
    ImageButton takePicture;
    //Button backButton;

    /* Camera View:
        - Go to Tools > AVD Manager
        - Click on the "Edit" button for selected emulator (Under Actions column, the Pencil icon)
        - Click on the "Advanced Settings" button
        - Change the "Camera" settings; find the webcam that you are using
        - Click "Finish"
    */
    SurfaceView babyMonitor;
    SurfaceHolder mySurfaceHolder;
    CameraDevice camera;
    CameraManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lumi__monitor2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findAllViews();
        configureLightsButton();
        configureMusicButton();
        //backMainMenu();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findAllViews() {
        lightButton = findViewById(R.id.LightButton);
        musicButton = findViewById(R.id.MusicButton);
        babyMonitor = findViewById(R.id.videoView);
        takePicture = findViewById(R.id.PictureButton);
        //backButton = findViewById(R.id.BackButton);
    }

    /*
    private void configureLightsButton() {
        lightButton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(Lumi_Monitor.this, LightActivity.class);
                startActivity(lightIntent);
            }
        });
    } */

    // TESTING: DATABASE INPUT PAGE
    /* Since we have not implemented the LEDs into the project, the Light button will
        temporarily be the input for the app's database. This will be changed later on.
    */

    private void configureLightsButton() {
        lightButton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(Lumi_Monitor.this, TestWriteDB.class);
                startActivity(lightIntent);
            }
        });
    }

    private void configureMusicButton() {
        musicButton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent musicIntent = new Intent(Lumi_Monitor.this, MusicActivity.class);
                startActivity(musicIntent);
            }
        });
    }

    private void cameraView() {
        // Allows us to add something to the surfaceview
        mySurfaceHolder = babyMonitor.getHolder();

        mySurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}