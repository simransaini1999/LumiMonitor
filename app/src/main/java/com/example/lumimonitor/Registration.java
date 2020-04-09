package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView message;
    //EditText enterUsername;
    private EditText enterPassword;
    private EditText enterEmail;
    private EditText confPassword;
    private Button register1;

    LightDB mData;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    int r,g,b,affState;

    Select_Song mData2;
    private FirebaseDatabase database2;
    private DatabaseReference myRef2;
    String currSongName;
    String currSongState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle(getString(R.string.registration));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);


        findAllViews();
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistration();
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
                startActivity(new Intent(Registration.this,Settings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void findAllViews(){
        //enterUsername = findViewById(R.id.EnterUsername);
        enterPassword = findViewById(R.id.EnterPassword);
        enterEmail = findViewById(R.id.EnterEmail);
        confPassword = findViewById(R.id.enterPassword);
        register1 = findViewById(R.id.regButton);
        message =findViewById(R.id.message);

    }

    private void startRegistration() {
        String registerEmail = String.valueOf(enterEmail.getText());
        //String registerUsername = String.valueOf(enterUsername.getText());
        String registerPassword1 = String.valueOf(enterPassword.getText());
        String registerPassword2 = String.valueOf(confPassword.getText());

        if (registerEmail.length() == 0 ||enterPassword.length() == 0 || confPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.empField),
                    Toast.LENGTH_LONG).show();
            return; // do nothing if empty.
        }
        else if (!registerPassword1.equals(registerPassword2))
        {
            Toast.makeText(getApplicationContext(), getString(R.string.passMatch),
                    Toast.LENGTH_LONG).show();
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword1)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("LumiMonitor", "createUserwithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            message.setText(getString(R.string.newUser) + user.getEmail() + getString(R.string.newUserReg));

                            getLightDatabase();
                            r=0;
                            g=0;
                            b=0;
                            affState = 0;
                            writeData(r, g, b,affState);

                            getSongDataBase();
                            currSongState = "Stop";
                            currSongName = "No Song Selected!";
                            writeSongData(currSongName,currSongState);


                            finish();
                        } else {
                            Log.w("LumiMonitor", "createUserWithEmail:faliure", task.getException());
                            Toast.makeText(getApplicationContext(), getString(R.string.userFailed),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }



    private void getLightDatabase() {
        database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String path = "lumiColour/" + mAuth.getUid();
        myRef = database.getReference(path);
    }

    private LightDB createData(int red, int green, int blue, int affect_State){
        return new LightDB(red, blue, green, affect_State);
    }

    private void writeData(int red, int blue, int green, int affect_State){
        mData = createData(red, green, blue,affect_State);
        myRef.setValue(mData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Lumi Monitor", "Value was set to: "+red+" "+green+" "+blue + " with a state of" +affect_State);
                //Toast.makeText(getApplicationContext(), "Value was set to: "+red+" "+green+" "+blue, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Could not update Lumi Colour", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getSongDataBase(){
        mAuth = FirebaseAuth.getInstance();
        database2 = FirebaseDatabase.getInstance();
        String path = "songPlaying/" + mAuth.getUid();
        myRef2 = database2.getReference(path);
        //user = mAuth.getCurrentUser();
    }

    private Select_Song createSongData(String song_name,String song_state){
        return new Select_Song(String.valueOf(song_name),String.valueOf(song_state));
    }

    private void writeSongData(String song_name,String song_state){
        mData2 = createSongData(song_name,song_state);
        myRef2.setValue(mData2).addOnSuccessListener(new OnSuccessListener<Void>() {
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
