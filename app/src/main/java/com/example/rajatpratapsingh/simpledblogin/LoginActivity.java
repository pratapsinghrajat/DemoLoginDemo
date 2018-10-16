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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonSgnIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity

            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }

        editTextEmail = (EditText)findViewById(R.id.editTexEtmail);
        editTextPassword = (EditText)findViewById(R.id.editTexPassword);
        buttonSgnIn = (Button)findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView)findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        buttonSgnIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }


    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


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

        //if email and password are not empty then
        //show the progress dialog


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity


                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == buttonSgnIn){
            userLogin();
        }

        if(v==textViewSignUp){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
