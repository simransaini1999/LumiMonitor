package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_Password extends AppCompatActivity {

    EditText emailview;
    EditText oldPasswordview;
    EditText newPasswordview;
    Button cpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);
        setTitle(getString(R.string.settings));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findAllViews();

        cpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAdd = emailview.getText().toString();
                String oldPass = oldPasswordview.getText().toString();
                String newPass = newPasswordview.getText().toString();

                changePass (emailAdd,oldPass,newPass);
            }
        });

    }

    private void findAllViews (){
        emailview = findViewById(R.id.enterEmail);
        oldPasswordview = findViewById(R.id.oldPassword);
        newPasswordview = findViewById(R.id.newPassword);
        cpb = findViewById(R.id.changePassButton);

    }


    private void changePass (final String emailAdd, final String oldPass, final String newPass){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(emailAdd, oldPass);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Log.d("LumiMonitor", "Password updated");
                                        Toast.makeText(getApplicationContext(), "Password updated",
                                                Toast.LENGTH_LONG).show();
                                        Intent setIntent = new Intent(Change_Password.this, Settings.class);
                                        startActivity(setIntent);

                                    } else {
                                        Log.d("LumiMonitor", "Error password not updated");
                                        Toast.makeText(getApplicationContext(), "Error password not updated",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Log.d("LumiMonitor", "Error auth failed");
                            Toast.makeText(getApplicationContext(), "Error authentication failed.",
                                    Toast.LENGTH_LONG).show();
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
