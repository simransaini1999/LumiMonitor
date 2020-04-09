package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ImageView mainImage;
    boolean testplay = false;
    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth2;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private FirebaseDatabase database2;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String UID;
    String currSongName;
    String currSongState;
    String currSongTime;
    String currSongLen;


    Select_Song mData;

    int redLight;
    int greenLight;
    int blueLight;






    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();

        getSongDataBase();
        getLightDataBase();

        dispLight();
        //retrieveData();
        playstop();
        configureLumiButton();
        configureBabyDataButton();
        Log.d("LumiMonitor", "Oncreate");
    }



    private void showData(DataSnapshot dataSnapshot) {
            Select_Song song = new Select_Song();
            song.setSongName(dataSnapshot.getValue(Select_Song.class).getSongName());
            song.setSongState(dataSnapshot.getValue(Select_Song.class).getSongState());
            songplaying.setText(getString(R.string.song_playing) + "\n" + song.getSongName());
            currSongName = song.getSongName();
            currSongState = song.getSongState();
    }

    private void showLightData(DataSnapshot dataSnapshot) {
        LightDB light = new LightDB();
        light.setRed(dataSnapshot.getValue(LightDB.class).getRed());
        light.setGreen(dataSnapshot.getValue(LightDB.class).getGreen());
        light.setBlue(dataSnapshot.getValue(LightDB.class).getBlue());

        redLight = light.getRed();
        greenLight = light.getGreen();
        blueLight = light.getBlue();
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
        mainImage = findViewById(R.id.imageView);
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

    private void getSongDataBase(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String path = "songPlaying/" + mAuth.getUid();
        myRef = database.getReference(path);
        //user = mAuth.getCurrentUser();
    }

    private void getLightDataBase(){
        mAuth2 = FirebaseAuth.getInstance();
        database2 = FirebaseDatabase.getInstance();
        String path2 = "lumiColour/" + mAuth2.getUid();
        myRef2 = database2.getReference(path2);
        //user = mAuth.getCurrentUser();
    }


    private void playstop () {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                if (currSongState.equals("Play")){
                    songplaying.setText(getString(R.string.song_playing) + "\n" + currSongName);
                    songplaying.setVisibility(View.VISIBLE);
                    playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                }else if (currSongState.equals("Pause")){
                    playbutton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    songplaying.setText(currSongName + "\n" + getString(R.string.is_pause));
                    songplaying.setVisibility(View.VISIBLE);
                }else if (currSongState.equals("Stop")){
                    playbutton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    songplaying.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Toast.makeText(getApplicationContext(), "Current Song: " + currSongName + "Current State: " + currSongState, Toast.LENGTH_LONG).show();
               if (currSongState.equals("Play")){
                    currSongState = "Pause";
                    writeData(currSongName,currSongState);
                    playbutton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    songplaying.setText(currSongName + "\n" + getString(R.string.is_pause));
                    songplaying.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), currSongName + " " + getString(R.string.is_now) + " " + getString(R.string.paused),  Toast.LENGTH_LONG).show();
               }else if (currSongState.equals("Stop")){
                   startActivity(new Intent(MainActivity.this, MusicActivity.class));
               }else if (currSongState.equals("Pause")){
                   currSongState = "Play";
                   writeData(currSongName,currSongState);
                   playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                   songplaying.setText(getString(R.string.song_playing) + "\n" + currSongName);
                   songplaying.setVisibility(View.VISIBLE);
                   Toast.makeText(getApplicationContext(), getString(R.string.now_playing) + " " + currSongName, Toast.LENGTH_SHORT).show();
               }
                    /* Old code kept for reference.

                    if (view == findViewById(R.id.playbutton)) {
                        playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                        songplaying.setText(getString(R.string.song_playing) + currSongName);
                        songplaying.setVisibility(View.VISIBLE);

                        Toast.makeText(MainActivity.this, getString(R.string.musicPlay), Toast.LENGTH_LONG).show();
                    }

                     */
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (currSongState.equals("Play") || currSongState.equals("Pause")){
                    Toast.makeText(getApplicationContext(), currSongName + " " + getString(R.string.is_now) + " " + getString(R.string.is_stopped), Toast.LENGTH_LONG).show();
                    currSongState = "Stop";
                    currSongName = "No Song Selected!";
                    writeData(currSongName,currSongState);
                    songplaying.setVisibility(View.GONE);
                // }


            }
        });

    }

    private void dispLight(){
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showLightData(dataSnapshot);
                    mainImage.setColorFilter(Color.rgb(redLight, greenLight, blueLight));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private Select_Song createData(String song_name,String song_state){
        return new Select_Song(String.valueOf(song_name),String.valueOf(song_state));
    }

    private void writeData(String song_name,String song_state){
        mData = createData(song_name,song_state);
        myRef.setValue(mData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (!song_name.equals("No Song Selected!")) {
                    //Toast.makeText(getApplicationContext(), getString(R.string.now_playing) +" " + song_name, Toast.LENGTH_SHORT).show();
                    Log.d("LumiMonitor", "Song added");
                }

                //gotoRead();  after write the data, read it from another screen.
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.song_failed), Toast.LENGTH_LONG).show();
            }
        });

    }
}
