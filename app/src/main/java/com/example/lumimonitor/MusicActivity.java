package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MusicActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Select_Song mData;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        setTitle(getText(R.string.changeMusic));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getDatabase();

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Songs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.settings:
                startActivity(new Intent(MusicActivity.this,Settings.class));
                return true;
            case R.id.goHome:
                    startActivity(new Intent(MusicActivity.this,MainActivity.class));
                    return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getDatabase() {
       database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String path = "songPlaying/" + mAuth.getUid();
        myRef = database.getReference(path);
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
                    Toast.makeText(getApplicationContext(), getString(R.string.now_playing) + song_name, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        String state;

        if (!text.equals("No Song Selected!")){
            state = "Play";
        }
        else{
            state = "Stop";
        }
        writeData(text,state);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
