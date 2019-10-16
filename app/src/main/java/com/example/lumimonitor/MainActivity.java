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
    ImageView ToLumiMonitor;
    TextView ToLumiMonitorTitle;
    ImageView ToBabyData;
    TextView ToBabyDataTitle;
    ImageView ToReminders;
    TextView ToRemindersTitle;
    boolean testplay = false;


    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        playstop();
        configureLumiButton();
        configureBabyDataButton();
        configureRemindersButton();



    }

    private void findAllViews() {
        playbutton = findViewById(R.id.playbutton);
        stopbutton = findViewById(R.id.stopbutton);
        songplaying = findViewById(R.id.SongPlaying);
        ToLumiMonitor = findViewById(R.id.ToLumiMonitor);
        ToLumiMonitorTitle = findViewById(R.id.ToLumiMonitorTitle);
        ToBabyData = findViewById(R.id.ToBabyData);
        ToBabyDataTitle = findViewById(R.id.ToBabyDataTitle);
        ToReminders = findViewById(R.id.ToReminders);
        ToRemindersTitle = findViewById(R.id.ToRemindersTitle);
    }

    private void configureLumiButton(){
        ToLumiMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Lumi_Monitor.class));
            }
        });

        ToLumiMonitorTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Lumi_Monitor.class));
            }
        });

    }

    private void configureBabyDataButton(){
        ToBabyData.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Baby_Data.class));
            }
        }));

        ToBabyDataTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Baby_Data.class));
            }
        });

    }

    private void configureRemindersButton(){
        ToReminders.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Reminders.class));
            }
        }));

        ToRemindersTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Reminders.class));
            }
        });
    }

    private void playstop (){
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == findViewById(R.id.playbutton)){
                    playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                    songplaying.setVisibility(View.VISIBLE);
                    testplay = true;
                }
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playbutton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                songplaying.setVisibility(View.GONE);
            }
        });

    }







    /*
    public boolean onPrepareOptionsMenu(Menu menu){
        this.menu = menu;
        return super.onPrepareOptionsMenu(menu);
    } */
}
