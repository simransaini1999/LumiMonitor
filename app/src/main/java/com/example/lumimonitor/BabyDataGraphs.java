package com.example.lumimonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BabyDataGraphs extends AppCompatActivity {

    private ImageButton tempGraph;
    private ImageButton humidityGraph;
    private ImageButton lightGraph;
    private Button signout;

    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_data_graphs);
        setTitle(getString(R.string.VBD));

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
                startActivity(new Intent(BabyDataGraphs.this,Settings.class));
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
                Toast.makeText(getApplicationContext(), getString(R.string.signedOutof) + user.getEmail(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
