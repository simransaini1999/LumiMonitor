package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_Email extends AppCompatActivity {
EditText oldEmailview;
EditText newEmailview;
EditText currentPassview;
Button ceb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__email);

        findAllViews();

        ceb.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              String oldEmail = oldEmailview.getText().toString();
              String newEmail = newEmailview.getText().toString();
              String currentPass = currentPassview.getText().toString();

              changeEmail(oldEmail, newEmail, currentPass);
              Intent gotosettings = new Intent(Change_Email.this, Settings.class);
              startActivity(gotosettings);
              Toast.makeText(getApplicationContext(), "Email Changed",
                      Toast.LENGTH_LONG).show();
          }
        });

    }
    private void findAllViews(){
        oldEmailview = findViewById(R.id.enter_old_Email);
        newEmailview = findViewById(R.id.enter_new_email);
        currentPassview = findViewById(R.id.current_password);
        ceb = findViewById(R.id.change_Email);
    }
    private void changeEmail(final String oldEmail, final String newEmail, final String currentPass){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider
                .getCredential(oldEmail, currentPass); // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("LumiMonitor", "User re-authenticated.");
                        //Now change your email address \\
                        //----------------Code for Changing Email Address----------\\
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(newEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("LumiMonitor", "User email address updated.");
                                            Toast.makeText(getApplicationContext(), "Email Changed",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    }
                });
    }

}

