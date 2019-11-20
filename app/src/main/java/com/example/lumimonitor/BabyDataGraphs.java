package com.example.lumimonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BabyDataGraphs extends AppCompatActivity {

    private Button tempGraph;
    private Button humidityGraph;
    private Button lightGraph;
    private Button signout;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_data_graphs);
        setTitle(getString(R.string.registration));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findAllViews();

        TemperatureGraphButton();
        HumidityGraphButton();
        LightValueGraphButton();
        SignOutButton();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void findAllViews() {
        tempGraph = findViewById(R.id.tempGraphBtn);
        humidityGraph = findViewById(R.id.humidityGraphBtn);
        lightGraph = findViewById(R.id.lightGraphBtn);
        signout = findViewById(R.id.signoutbutton);

    }

    private void TemperatureGraphButton() {
        tempGraph.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent tempGraphIntent = new Intent(BabyDataGraphs.this, Graph_Temperature.class);
                startActivity(tempGraphIntent);
            }
        });
    }

    private void HumidityGraphButton() {
        humidityGraph.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent humidityGraphIntent = new Intent(BabyDataGraphs.this, Graph_Humidity.class);
                startActivity(humidityGraphIntent);
            }
        });
    }
    private void LightValueGraphButton() {
        lightGraph.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent lightGraphIntent = new Intent(BabyDataGraphs.this, Graph_Light_Value.class);
                startActivity(lightGraphIntent);
            }
        });
    }

    private void SignOutButton() {
        signout.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Signed out of " + user.getEmail(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
