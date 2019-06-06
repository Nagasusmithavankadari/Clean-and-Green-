package com.example.green2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class login extends AppCompatActivity {
    private EditText name;
    private EditText Password;
    private TextView UserRegistration;
    private Button userlogin1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText) findViewById(R.id.edittext_username);
        Password = (EditText) findViewById(R.id.edittext_password);
        userlogin1 = (Button) findViewById(R.id.button_login);
        UserRegistration = (TextView) findViewById(R.id.textview_register);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        /*if (user != null) {
            finish();
            startActivity(new Intent(login.this, Registration.class));
        }*/
        userlogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), Password.getText().toString());
                startActivity(new Intent(login.this,send_to_mail.class));
            }
        });
        UserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Registration.class));
            }
        });
    }


            private void validate(String username, String userPassword) {
                progressDialog.setMessage("You can subscribe to my channel until you are verified!");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(username, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(login.this, send_to_mail.class));
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        }

