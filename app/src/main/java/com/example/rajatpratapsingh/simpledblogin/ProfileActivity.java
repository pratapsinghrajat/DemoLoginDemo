package com.example.rajatpratapsingh.simpledblogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAddress, editTextBirth, editTextNumber, editTextIDProof;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText)findViewById(R.id.editTexName);
        editTextAddress = (EditText)findViewById(R.id.editTexAddress);
        editTextBirth = (EditText)findViewById(R.id.editTextBirth);
        editTextNumber = (EditText)findViewById(R.id.editTexNumber);
        editTextIDProof = (EditText)findViewById(R.id.editIDProof);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView)findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome "+user.getEmail());
        buttonLogout = (Button)findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
    }

    private void saveUserInformation(){

        String name = editTextName.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();
        String birth = editTextBirth.getText().toString().trim();
        String num = editTextNumber.getText().toString().trim();
        String proof = editTextIDProof.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,add,birth,num,proof);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information is submitted",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if(v == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(v== buttonSubmit){
            saveUserInformation();
        }

    }
}
