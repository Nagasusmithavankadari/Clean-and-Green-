package com.example.green2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registration extends AppCompatActivity {
    private EditText username,userpassword,cnf_password;
    private Button regButton,sweeper;
    private Button userlogin;
     private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String username1 = username.getText().toString().trim();
                    final String user_password=userpassword.getText().toString().trim();
                    final String cnfpassword=cnf_password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(username1,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()&& user_password.equals(cnfpassword)) {
                                Toast.makeText(Registration.this, "activity_registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,send_to_mail.class));
                            } else {

                                Toast.makeText(Registration.this, "activity_registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
      userlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(Registration.this,login.class);
               startActivity(intent);
           }
       });

        sweeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this,send_to_firebase.class) );
            }
        });
    }
    private void setupUIViews()
    {
        username=(EditText)findViewById(R.id.edittext_username);
        userpassword=(EditText)findViewById(R.id.edittext_password);
        cnf_password=(EditText)findViewById(R.id.edittext_cnf_password);
        regButton=(Button)findViewById(R.id.button_Register);
        userlogin=(Button)findViewById(R.id.btn_Login);
        sweeper=(Button)findViewById(R.id.Sweeper);
    }
    private Boolean validate(){
        Boolean result=false;
        String name=username.getText().toString();
        String password=userpassword.getText().toString();
        if(name.isEmpty() && password.isEmpty())
        {
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }

}
