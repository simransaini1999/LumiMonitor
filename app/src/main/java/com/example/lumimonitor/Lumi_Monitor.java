package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
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

public class Lumi_Monitor extends AppCompatActivity {

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
    FrameLayout babyMonitor;
  //  private Camera mCamera;
  //  private CameraPreview mPreview;

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
       // checkCameraHardware(this);
       // videoCamera();

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

    /** Check if this device has a camera */
    /*
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            //Toast.makeText(getApplicationContext(), "Camera Works",
             //       Toast.LENGTH_LONG).show();
            return true;
        } else {
            // no camera on this device
            //Toast.makeText(getApplicationContext(), "No Camera",
                 //   Toast.LENGTH_LONG).show();
            return false;
        }
    }

    // Creates a folder in the external storage to save the photos in.
    private File getFile() {

        File pictureFolder = new File("sdcard/camera_app");

        if(!pictureFolder.exists()) {
            pictureFolder.mkdir();
        }

        File image_file = new File(pictureFolder,"camera_image.jpg");
        return image_file;
    }

    /** A safe way to get an instance of the Camera object. */
    /*
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void videoCamera() {
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        babyMonitor.addView(mPreview);
    } */

    /*

    private void backMainMenu() {
        backButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                       finish();
            }
        });
    }*/

}
/*
class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("MapleLeaf", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d("MapleLeaf", "Error starting camera preview: " + e.getMessage());
        }
    }
} */