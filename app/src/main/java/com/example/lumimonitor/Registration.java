package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView message;
    //EditText enterUsername;
    private EditText enterPassword;
    private EditText enterEmail;
    private EditText confPassword;
    private Button register1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registraton");
        findAllViews();
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistration();
            }
        });


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
            Toast.makeText(getApplicationContext(), "The email and/or password cannot be empty",
                    Toast.LENGTH_LONG).show();
            return; // do nothing if empty.
        }
        else if (!registerPassword1.equals(registerPassword2))
        {
            Toast.makeText(getApplicationContext(), "The password does not match, cannot continue",
                    Toast.LENGTH_LONG).show();
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword1)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Lumi-Monitor", "createUserwithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            message.setText("New user " + user.getEmail() + " is now registered");
                            finish();
                        } else {
                            Log.w("Lumi-Monitor", "createUserWithEmail:faliure", task.getException());
                            Toast.makeText(getApplicationContext(), "Create new user failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

}
