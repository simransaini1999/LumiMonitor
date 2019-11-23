package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

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
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = mAuth.getCurrentUser();


    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        playstop();
        configureLumiButton();
        configureBabyDataButton();
        configureRemindersButton();
        Log.d("LumiMonitor", "Oncreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(MainActivity.this,Settings.class));
                return true;
            default:  return super.onOptionsItemSelected(item);
        }

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

                if (null == mAuth.getCurrentUser()){
                    Log.d("LumiMonitor", "Not Signed in!");
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                }else {
                    Log.d("LumiMonitor", "Signed in!");
                    startActivity(new Intent(MainActivity.this, BabyDataGraphs.class));
                }
            }
        }));

        ToBabyDataTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == mAuth.getCurrentUser()){
                    Log.d("LumiMonitor", "Not Signed in!");
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                }else {
                    Log.d("LumiMonitor", "Signed in!");
                    startActivity(new Intent(MainActivity.this, BabyDataGraphs.class));
                }
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
                    Toast.makeText(MainActivity.this,getString(R.string.musicPlay),Toast.LENGTH_LONG).show();
                    testplay = true;
                }
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playbutton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                songplaying.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,getString(R.string.musicStop),Toast.LENGTH_LONG).show();
            }
        });

    }



}
