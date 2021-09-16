package com.mobdeve.g15.mco1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignup;

    private EditText et_username;
    private EditText et_password;

    private FirebaseAuth fbLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_Password);

        fbLogin = FirebaseAuth.getInstance();



        btnLogin = findViewById(R.id.Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                signIn(email, password);
            }
        });

        btnSignup = findViewById(R.id.Signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Login.this.startActivity(intent);
            }
        });
    }

    private void signIn(String email, String password) {
        fbLogin.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    loginSuccessful();
                }

                else {
                    loginFailed();
                }
            }
        });
    }
    private void loginSuccessful(){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginFailed(){
        Toast.makeText(this, "User Login Failed", Toast.LENGTH_SHORT);
    }
}