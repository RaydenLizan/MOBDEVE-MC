package com.mobdeve.g15.mco1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class Register extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private Button btn_register;
    private Button btn_login;

    private FirebaseAuth fbAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_password = findViewById(R.id.et_Password_sign);
        et_email = findViewById(R.id.et_email_sign);

        this.fbAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance("https://egame-55b1c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        btn_login = findViewById(R.id.Login_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                Register.this.startActivity(intent);

            }
        });

        btn_register = findViewById(R.id.Signup_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();

                if(!checkEmpty(email, password))
                {
                    User user = new User(email, password);
                    Log.i("REGISTRATION", "WHAT");
                    createUser(user);
                }
            }
        });

        Log.i("REGISTRATION", "WHAT");




    }

    private void createUser(User user)
    {

        fbAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    database.getReference("users").child(fbAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Log.i("REGISTRATION", "successful");
                                successfulRegistration();
                            }

                            else {
                                failedRegistration();
                                Log.i("REGISTRATION", "SET");
                            }
                        }
                    });

                }

                else {
                    Log.i("REGISTRATION", "CREATE");
                    failedRegistration();
                }
            }
        });
    }


    private void successfulRegistration() {
        Toast.makeText(this, "User Registration Successful", Toast.LENGTH_LONG);
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void failedRegistration() {
        Toast.makeText(this, "User Registration Failed", Toast.LENGTH_LONG);
        Log.i("REGISTRATION", "FAILED");

    }

    private boolean checkEmpty(String e, String p)
    {
        if(e.isEmpty())
        {
            Log.i("REGISTRATION", "Email missing");
            return true;
        }

        else if(p.isEmpty())
        {
            //error msg
            return true;
        }


        return false;
    }
}