package com.example.rajatpratapsingh.simpledblogin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity

            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }


        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmail = (EditText)findViewById(R.id.editTexEtmail);
        editTextPassword = (EditText)findViewById(R.id.editTexPassword);

        textViewSignIn = (TextView)findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show();
            //stop function from further execution
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty

            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            //stop function from further execution
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //user succesffully registerd
                            //we start profile activity here
                            //right now just display toast
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                            Toast.makeText(MainActivity.this,"Registered Successfully ",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Registeration Failed ",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {

        if(v==buttonRegister){
            registerUser();
        }

        if(v == textViewSignIn){
            startActivity(new Intent(this, LoginActivity.class));

        }

    }
}
