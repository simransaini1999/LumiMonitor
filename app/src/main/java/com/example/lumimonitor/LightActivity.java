package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.util.logging.Logger.global;

public class LightActivity extends AppCompatActivity {


    LightDB mData;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    ImageView colorWheel;
    TextView displayRGB;
    View colorView;
    Button tempButton;
    Switch whiteSwitch;
    Switch offSwitch;
    Switch lightReact;
    TextView colourWheelTitle;
    TextView colourTitle;


    int r,g,b, affectState;
    int oldr, oldg, oldb;


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
        getDatabase();
        configureTempButton();
        dispLight();
        sendWhite();
        turnOff();
        turnOffLightAffect();

        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true);

        colorWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    try {
                        bitmap = colorWheel.getDrawingCache();

                        int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());

                        r = Color.red(pixel);
                        g = Color.green(pixel);
                        b = Color.blue(pixel);


                        String hex = "#" + Integer.toHexString(pixel);

                        writeData(r, g, b,affectState);

                        colorView.setBackgroundColor(Color.rgb(r, g, b));

                        whiteSwitch.setChecked(false);
                        whiteSwitch.setClickable(true);


                        displayRGB.setText("RGB: " + r + ", " + g + ", " + b + "\nHEX: " + hex);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return true;
            }
        });

    }

    private void turnOffLightAffect() {
        lightReact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true){
                    affectState = 1;
                    writeData(r, g, b,affectState);

                }
                else{
                    affectState = 0;
                    writeData(r, g, b,affectState);
                }
            }
        });

    }

    private void turnOff() {
       offSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if (isChecked == true){
                   oldr = r;
                   oldg = g;
                   oldb = b;

                   r = 0;
                   g = 0;
                   b = 0;

                   colourWheelTitle.setVisibility(View.GONE);
                   colorWheel.setVisibility(View.GONE);
                   colorView.setVisibility(View.GONE);
                   colourTitle.setVisibility(View.GONE);
                   whiteSwitch.setClickable(false);



                   writeData(r, g, b,affectState);
                   colorView.setBackgroundColor(Color.rgb(r, g, b));
               }
               else{
                   r = oldr;
                   g = oldg;
                   b = oldb;
                   writeData(r, g, b,affectState);
                   colorView.setBackgroundColor(Color.rgb(r, g, b));



                   whiteSwitch.setClickable(true);
                   colourWheelTitle.setVisibility(View.VISIBLE);
                   colorWheel.setVisibility(View.VISIBLE);
                   colorView.setVisibility(View.VISIBLE);
                   colourTitle.setVisibility(View.VISIBLE);



               }
           }
       });




    }

    private void sendWhite() {



        whiteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {

                    whiteSwitch.setClickable(false);
                    r = 255;
                    g = 255;
                    b = 255;

                    writeData(r, g, b,affectState);
                    colorView.setBackgroundColor(Color.rgb(r, g, b));
                    Toast.makeText(getApplicationContext(), getString(R.string.white_colour), Toast.LENGTH_LONG).show();
                }

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

    private void getDatabase() {
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

    private void showLightData(DataSnapshot dataSnapshot) {
        LightDB light = new LightDB();
        light.setRed(dataSnapshot.getValue(LightDB.class).getRed());
        light.setGreen(dataSnapshot.getValue(LightDB.class).getGreen());
        light.setBlue(dataSnapshot.getValue(LightDB.class).getBlue());
        light.setAffectState(dataSnapshot.getValue(LightDB.class).getAffectState());

        r = light.getRed();
        g = light.getGreen();
        b = light.getBlue();
        affectState = light.getAffectState();
    }

    private void dispLight(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showLightData(dataSnapshot);
                colorView.setBackgroundColor(Color.rgb(r, g, b));

                if (r == 255 && g == 255 && b == 255){
                    whiteSwitch.setChecked(true);
                }

                if (affectState == 1){
                    lightReact.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void findAllViews(){
        colorWheel = findViewById(R.id.colourWheel);
        displayRGB = findViewById(R.id.rgbValue);
        colorView = findViewById(R.id.colourView);
        tempButton = findViewById(R.id.tempButton);
        whiteSwitch = findViewById(R.id.whiteSwitch);
        colourWheelTitle = findViewById(R.id.colourWheelTitle);
        offSwitch = findViewById(R.id.offSwitch);
        colourTitle = findViewById(R.id.colourTitle);
        lightReact = findViewById(R.id.lightReact);

    }
}
