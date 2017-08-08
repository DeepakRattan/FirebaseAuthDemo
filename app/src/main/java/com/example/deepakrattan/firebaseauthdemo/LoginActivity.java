package com.example.deepakrattan.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtPass;
    private Button btnLogin, btnSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String email, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findViewbyId
        edtEmail = (EditText) findViewById(R.id.edtEmailID);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLog);
        btnSignUp = (Button) findViewById(R.id.btnReg);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLog:
                Toast.makeText(LoginActivity.this, "Login clicked", Toast.LENGTH_LONG).show();
                userLogin();
                break;

            case R.id.btnReg:
                Toast.makeText(LoginActivity.this, "Sign Up clicked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }

    }

    private void userLogin() {
        email = edtEmail.getText().toString().trim();
        passwd = edtPass.getText().toString().trim();

        //Checking if email and password are empty or not
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter EmailID", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Logging.Please wait...");
        progressDialog.show();


        //user logging in
        firebaseAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if task is successfull
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                } else {
                    Exception e = task.getException();
                    Toast.makeText(LoginActivity.this, "login unsuccessfull" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();

            }
        });


    }
}
