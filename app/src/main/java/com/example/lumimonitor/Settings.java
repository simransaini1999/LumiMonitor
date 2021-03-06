package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class Settings extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        listView=findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(getString(R.string.ChangeLang));
        arrayList.add(getString((R.string.viewCurrEmail)));
        arrayList.add(getString(R.string.changePassword));
        arrayList.add(getString(R.string.Sign_Out));

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.listview_style,arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("LumiMonitor", Integer.toString(i));

                if (i==0){
                    Intent langIntent = new Intent(Settings.this, Set_Language.class);
                    startActivity(langIntent);
                }

                if (i==3){
                    if (null == mAuth.getCurrentUser()){
                        Toast.makeText(getApplicationContext(), getText(R.string.not_signed_in),
                                Toast.LENGTH_LONG).show();
                    }else{
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), getString(R.string.signedOutof)+" "+ user.getEmail(),
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.this, LoginScreen.class));
                    }

                }

                if (i==2){
                    if (null == mAuth.getCurrentUser()) {
                        Toast.makeText(getApplicationContext(), getText(R.string.not_signedin_pass),
                                Toast.LENGTH_LONG).show();
                    }else {
                        Intent changePassIntent = new Intent(Settings.this, Change_Password.class);
                        startActivity(changePassIntent);
                    }
                }
                if (i==1){
                    Intent changeEmailIntent = new Intent(Settings.this, Change_Email.class);
                    startActivity(changeEmailIntent);
                }


            }
        });

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

}
