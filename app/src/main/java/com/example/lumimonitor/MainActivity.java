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


public class MainActivity extends AppCompatActivity {

    ImageView playbutton;
    ImageView stopbutton;
    TextView songplaying;
    ImageView ToLumiMonitor;
    TextView ToLumiMonitorTitle;
    ImageView ToBabyData;
    TextView ToBabyDataTitle;
    boolean testplay = false;
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    Select_Song mData;
    String currSongName;


    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getDatabase();
        findAllViews();
        playstop();
        configureLumiButton();
        configureBabyDataButton();
        currSongName = "none";
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

    /*

    private void getDatabase() {
        database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String path = "songPlaying/" + mAuth.getUid();
        myRef = database.getReference(path);
    }

    private void getData(){
        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Select_Song selSong = dataSnapshot.getValue(Select_Song.class);
                songplaying.setText("Song Playing " + selSong.getSongName());
                //currSongName = selSong.getSongName();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Select_Song selSong = dataSnapshot.getValue(Select_Song.class);
                songplaying.setText("Song Playing " + selSong.getSongName());
                currSongName = selSong.getSongName();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Select_Song> arraylist= new ArrayList<Select_Song>();
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        Select_Song dataStructure = new Select_Song();
                        dataStructure.setSongName(a.getValue(Select_Song.class).getSongName());
                        arraylist.add(dataStructure);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataUnavailable), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("LumiMonitor", "Data Loading Canceled/Failed.", databaseError.toException());
            }
        });
    }

     */

    private void playstop (){
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getData();
                    if (view == findViewById(R.id.playbutton)){
                    playbutton.setImageResource(R.drawable.ic_pause_black_24dp);
                    songplaying.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,getString(R.string.musicPlay) + " " + currSongName,Toast.LENGTH_LONG).show();
                    //testplay = true;
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
