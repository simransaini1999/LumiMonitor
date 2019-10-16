package com.example.lumimonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Lumi_Monitor extends AppCompatActivity {


    // Button Declarations
    ImageButton lightButton;
    ImageButton musicButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lumi__monitor2);

        findAllViews();
        configureLightsButton();
        configureMusicButton();
        backMainMenu();

    }

    private void findAllViews() {
        lightButton = findViewById(R.id.LightButton);
        musicButton = findViewById(R.id.MusicButton);
        backButton = findViewById(R.id.BackButton);
    }

    private void configureLightsButton() {
        lightButton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent lightIntent = new Intent(Lumi_Monitor.this, LightActivity.class);
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

    private void backMainMenu() {
        backButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                       finish();
            }
        });
    }

}
