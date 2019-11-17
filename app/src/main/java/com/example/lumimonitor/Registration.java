package com.example.lumimonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
        setTitle(getString(R.string.registration));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);


        findAllViews();
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistration();
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
            Toast.makeText(getApplicationContext(), getString(R.string.empField),
                    Toast.LENGTH_LONG).show();
            return; // do nothing if empty.
        }
        else if (!registerPassword1.equals(registerPassword2))
        {
            Toast.makeText(getApplicationContext(), getString(R.string.passMatch),
                    Toast.LENGTH_LONG).show();
        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword1)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("LumiMonitor", "createUserwithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            message.setText(getString(R.string.newUser) + user.getEmail() + getString(R.string.newUserReg));
                            finish();
                        } else {
                            Log.w("LumiMonitor", "createUserWithEmail:faliure", task.getException());
                            Toast.makeText(getApplicationContext(), getString(R.string.userFailed),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

}
