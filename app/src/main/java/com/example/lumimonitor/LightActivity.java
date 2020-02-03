package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LightActivity extends AppCompatActivity {

    ImageView colorWheel;
    TextView displayRGB;
    View colorView;
    Button tempButton;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        setTitle(R.string.lights_act);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findAllViews();
        configureTempButton();

        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true);

        colorWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){

                    try {
                        bitmap = colorWheel.getDrawingCache();

                        int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());

                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);


                        String hex = "#" + Integer.toHexString(pixel);

                        colorView.setBackgroundColor(Color.rgb(r, g, b));

                        displayRGB.setText("RGB: " + r + ", " + g + ", " + b + "\nHEX: " + hex);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                return true;
            }
        });

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
                startActivity(new Intent(LightActivity.this,Settings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureTempButton() {
        tempButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(LightActivity.this, TestWriteDB.class);
                startActivity(lightIntent);
            }
        });
    }

    public void findAllViews(){
        colorWheel = findViewById(R.id.colourWheel);
        displayRGB = findViewById(R.id.rgbValue);
        colorView = findViewById(R.id.colourView);
        tempButton = findViewById(R.id.tempButton);
    }
}
