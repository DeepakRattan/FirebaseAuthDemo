package com.example.deepakrattan.firebaseauthdemo;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuthException;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtPasswd;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPasswd = (EditText) findViewById(R.id.edtPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    public void registerUser() {
        email = edtEmail.getText().toString().trim();
        password = edtPasswd.getText().toString().trim();

        //checking if email and password are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;

        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUpActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return;

        }

        //if email and password are not empty display a progress dialog
        progressDialog.setMessage("Registering.Please wait... ");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if success
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Registration successfull", Toast.LENGTH_LONG).show();

                } else {
                    Exception e = task.getException();
                    Toast.makeText(SignUpActivity.this, "Registration unsuccessfull" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        });


    }
}
