package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;

public class Lumi_Monitor extends AppCompatActivity {

    // Button Declarations
    ImageView lightButton;
    ImageView musicButton;
    ImageView takePicture;
    VideoView babyMonitor;
    //Button backButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lumi__monitor2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        findAllViews();
        configureLightsButton();
        configureMusicButton();
        babyMonitorView();
        //backMainMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                startActivity(new Intent(Lumi_Monitor.this,Settings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void findAllViews() {
        lightButton = findViewById(R.id.goToLight);
        musicButton = findViewById(R.id.goToMusic);
        babyMonitor = findViewById(R.id.videoView);
        takePicture = findViewById(R.id.takePicture);
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
        lightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(Lumi_Monitor.this, TestWriteDB.class);
                startActivity(lightIntent);
            }
        });
    }

    private void configureMusicButton() {
        musicButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == mAuth.getCurrentUser()){
                    Toast.makeText(getApplicationContext(), "Not Logged in! Log in to play music!",
                            Toast.LENGTH_LONG).show();
                }else {
                    Intent musicIntent = new Intent(Lumi_Monitor.this, MusicActivity.class);
                    startActivity(musicIntent);
                }
            }
        });
    }

    private void babyMonitorView() {

        // setting the media controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(babyMonitor);

        // set the url of the video
        Uri uri = Uri.parse("https://wowzaprod222-i.akamaihd.net/hls/live/1005344/f81647c6/playlist.m3u8");

        // setting the video player
        babyMonitor.setMediaController(mediaController);
        babyMonitor.setVideoURI(uri);
        babyMonitor.start();

    }


}