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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private Button loginBtn;
    private Button signUpBtn;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTitle(getString(R.string.login));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findAllViews();
        SignUpButton();
        LoginHandler();
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
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.LoginButton);
        signUpBtn = findViewById(R.id.SignUpButton);
    }



    private void SignUpButton() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrationIntent = new Intent(LoginScreen.this, Registration.class);
                startActivity(registrationIntent);
            }
        });
    }


    private void LoginHandler() {
        // Initializes Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(String.valueOf(email.getText()), String.valueOf(password.getText()));
            }
        });

    }

    /*
    // Test to enter graph
    private void LoginHandler() {
        // Initializes Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(String.valueOf(email.getText()), String.valueOf(password.getText()));

                Intent lightIntent = new Intent(LoginScreen.this, Graph_Temperature.class);
                startActivity(lightIntent);
            }
        });

    } */

    private void loginUser(String email, String password) {
        // Ensures that email and password fields are not empty
        if (email.length()==0 || password.length()==0){
            Toast.makeText(getApplicationContext(), getString(R.string.epEmpty),
                    Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d("LumiMonitor", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), getString(R.string.loginSuccess),
                                    Toast.LENGTH_LONG).show();
                            Intent babyDataIntent = new Intent(LoginScreen.this, BabyDataGraphs.class);
                            startActivity(babyDataIntent);
                        } else {
                            Log.w("LumiMonitor", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), getString(R.string.authFailed),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}

