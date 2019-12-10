package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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

public class Baby_Data extends AppCompatActivity {

    // Firebase Variables
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    DataStructure myData;

    private TextView temperature;
    private TextView humidity;
    private TextView lightValue;
    private TextView awakenTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby__data);



        findAllViews();
        getDatabase();
        retrieveData();
        ConfigureBackButton();
    }


    private void ConfigureBackButton() {
        Button back = findViewById(R.id.TempBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findAllViews() {
        temperature = findViewById(R.id.temperatureText);
        humidity = findViewById(R.id.humidityText);
        lightValue = findViewById(R.id.lightValueText);
        awakenTime = findViewById(R.id.awakenTimeText);
    }

    private void getDatabase() {
        //FirebaseApp.initializeApp(this);

       database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String path = "userdata/" + mAuth.getUid();
        myRef = database.getReference(path);
    }

    private void retrieveData() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                temperature.setText(getString(R.string.temp) + " " + ds.getTemperature());
                humidity.setText(getString(R.string.humid) + " " + ds.getHumidity());
                lightValue.setText(getString(R.string.lightVal) + " " + ds.getLightValue());

                // Convert time stamp to Date and Time
                awakenTime.setText(getString(R.string.awaken) + " " + convertTimestamp(ds.getAwakenTime()));
            }

            private String convertTimestamp(String timestamp) {
                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
                return df.format(mDate);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataStructure ds = dataSnapshot.getValue(DataStructure.class);
                temperature.setText(getString(R.string.temp) + " " + ds.getTemperature());
                humidity.setText(getString(R.string.humid) + " " + ds.getHumidity());
                lightValue.setText(getString(R.string.lightVal) + " " + ds.getLightValue());

                // Convert from timestamps to Date and time
                awakenTime.setText(getString(R.string.awaken) + " " + convertTimestamp(ds.getAwakenTime()));
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

        // TODO: Get the whole data array on a reference.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DataStructure> arraylist= new ArrayList<DataStructure>();

                // TODO: Now data is retrieved, needs to process data.
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    // iterate all the items in the dataSnapshot
                    for (DataSnapshot a : dataSnapshot.getChildren()) {
                        DataStructure dataStructure = new DataStructure();
                        dataStructure.setTemperature(a.getValue(DataStructure.class).getTemperature());
                        dataStructure.setHumidity(a.getValue(DataStructure.class).getHumidity());
                        dataStructure.setLightValue(a.getValue(DataStructure.class).getLightValue());
                        dataStructure.setAwakenTime(a.getValue(DataStructure.class).getAwakenTime());

                        arraylist.add(dataStructure);  // now all the data is in arraylist.
                        Log.d("LumiMonitor", "dataStructure " + dataStructure.getAwakenTime());
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.dataUnavailable), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting data failed, log a message
                Log.d("LumiMonitor", "Data Loading Canceled/Failed.", databaseError.toException());
            }
        });
    }

}
