package com.example.lumimonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Menu menu;
    ImageView playbutton;
    ImageView stopbutton;
    TextView songplaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureLumiButton();

        playbutton = findViewById(R.id.playbutton);
        stopbutton = findViewById(R.id.stopbutton);
        songplaying = findViewById(R.id.SongPlaying);

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == findViewById(R.id.playbutton)){
                    playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                    songplaying.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void configureLumiButton(){
        ImageView ToLumiMonitor;
        ToLumiMonitor = findViewById(R.id.ToLumiMonitor);

        ToLumiMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Lumi_Monitor.class));
            }
        });

    }

    /*
    public boolean onPrepareOptionsMenu(Menu menu){
        this.menu = menu;
        return super.onPrepareOptionsMenu(menu);
    } */
}
