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
    //Button backButton;
    private FirebaseAuth mAuth;


    /* Camera View:
        - Go to Tools > AVD Manager
        - Click on the "Edit" button for selected emulator (Under Actions column, the Pencil icon)
        - Click on the "Advanced Settings" button
        - Change the "Camera" settings; find the webcam that you are using
        - Click "Finish"
    */
    TextureView babyMonitor;
    CameraDevice camera;
    CameraManager manager;
    String cameraId;
    Size imageDimensions;

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
        //backMainMenu();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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


    /*
    private void cameraView() {
        babyMonitor.setSurfaceTextureListener(surfaceTextureListener);
    }

    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private void openCamera() throws CameraAccessException {
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraId = manager.getCameraIdList()[0];

        CameraCharacteristics cc = manager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        imageDimensions = map.getOutputSizes(SurfaceTexture.class)[0];

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        manager.openCamera(cameraId, stateCallback, null);
    }

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            cameraDevice = camera;
            startCameraPreview();

        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    } */

}