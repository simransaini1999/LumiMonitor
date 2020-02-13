package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    ImageView playbutton;
    ImageView stopbutton;
    TextView songplaying;
    ImageView ToLumiMonitor;
    TextView ToLumiMonitorTitle;
    ImageView ToBabyData;
    TextView ToBabyDataTitle;
    boolean testplay = false;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String UID;
    String currSongName;



    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String path = "songPlaying/" + mAuth.getUid();
        myRef = database.getReference(path);
        //user = mAuth.getCurrentUser();


       playstop();
        configureLumiButton();
        configureBabyDataButton();
        Log.d("LumiMonitor", "Oncreate");
    }

    private void showData(DataSnapshot dataSnapshot) {
            Select_Song song = new Select_Song();
            song.setSongName(dataSnapshot.getValue(Select_Song.class).getSongName());
            songplaying.setText("Song Playing " + song.getSongName());
            currSongName = song.getSongName();
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
    }

    private void configureLumiButton(){
        ToLumiMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == mAuth.getCurrentUser()){
                    Log.d("LumiMonitor", "Not Signed in!");
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                }else {
                    Log.d("LumiMonitor", "Signed in!");
                    startActivity(new Intent(MainActivity.this, Lumi_Monitor.class));
                }
            }
        });

        ToLumiMonitorTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null == mAuth.getCurrentUser()){
                    Log.d("LumiMonitor", "Not Signed in!");
                    startActivity(new Intent(MainActivity.this,LoginScreen.class));
                }else {
                    Log.d("LumiMonitor", "Signed in!");
                    startActivity(new Intent(MainActivity.this, Lumi_Monitor.class));
                }
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


    private void playstop (){
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (null == mAuth.getCurrentUser()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.not_signedin_music),
                            Toast.LENGTH_LONG).show();
                } else {

                        myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if (view == findViewById(R.id.playbutton)) {
                        playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                        songplaying.setText(getString(R.string.song_playing) + currSongName);
                        songplaying.setVisibility(View.VISIBLE);

                        Toast.makeText(MainActivity.this, getString(R.string.musicPlay), Toast.LENGTH_LONG).show();
                    }
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
