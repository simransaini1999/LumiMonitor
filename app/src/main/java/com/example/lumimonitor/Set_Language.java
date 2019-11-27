package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


import java.util.Locale;

public class Set_Language extends AppCompatActivity {
    Button engBox;
    Button frBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__language);
        frBox = findViewById(R.id.French);
        engBox = findViewById(R.id.English);
        setTitle(getString(R.string.settings));
    engBox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setLocale("en");
            setContentView(R.layout.activity_main);
            setContentView(R.layout.activity_change__email);
            setContentView(R.layout.activity_baby__data);
            setContentView(R.layout.activity_baby_data_graphs);
            setContentView(R.layout.activity_change__password);
            setContentView(R.layout.activity_graph_humidity);
            setContentView(R.layout.activity_graph_light_value);
            setContentView(R.layout.activity_graph_temperature);
            setContentView(R.layout.activity_light);
            setContentView(R.layout.activity_login_screen);
            setContentView(R.layout.activity_lumi__monitor2);
            setContentView(R.layout.activity_music);
            setContentView(R.layout.activity_registration);
            setContentView(R.layout.activity_reminders);
            setContentView(R.layout.activity_set__language);
            setContentView(R.layout.activity_settings);
            setContentView(R.layout.activity_test_write_db);
        }
    });
        frBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale("fr");
                setContentView(R.layout.activity_main);
                setContentView(R.layout.activity_change__email);
                setContentView(R.layout.activity_baby__data);
                setContentView(R.layout.activity_baby_data_graphs);
                setContentView(R.layout.activity_change__password);
                setContentView(R.layout.activity_graph_humidity);
                setContentView(R.layout.activity_graph_light_value);
                setContentView(R.layout.activity_graph_temperature);
                setContentView(R.layout.activity_light);
                setContentView(R.layout.activity_login_screen);
                setContentView(R.layout.activity_lumi__monitor2);
                setContentView(R.layout.activity_music);
                setContentView(R.layout.activity_registration);
                setContentView(R.layout.activity_reminders);
                setContentView(R.layout.activity_set__language);
                setContentView(R.layout.activity_settings);
                setContentView(R.layout.activity_test_write_db);
            }
        });
        /*if (frBox.isClickable()){
            setLocale("fr");
            setContentView(R.layout.activity_main);

        }
        else if (engBox.isClickable()){
            setLocale("en");
            setContentView(R.layout.activity_main);
        }
*/
       // setLocale("fr");
       // setContentView(R.layout.activity_main);

    }
    public void setLocale(String lang) {
        //Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(lang.toLowerCase()));
        }else {
            conf.locale = new Locale(lang.toLowerCase());
        }
        //conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
       Intent refresh = new Intent(this, MainActivity.class);
       finish();
       startActivity(refresh);
    }
}
