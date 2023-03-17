package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText editpass, editemail;
            TextView editsubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editpass = findViewById(R.id.password);
        editemail=findViewById(R.id.email);
        editsubmit=findViewById(R.id.submit);

        editsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginUser();
            }
        });
    }
    public void loginUser() {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private Boolean validateUsername() {
        String val = (String)(editemail.getText().toString());
        if (val.isEmpty()) {
            editemail.setError("Field cannot be empty");
            return false;
        } else {
            editemail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = (String)(editemail.getText().toString());
        if (val.isEmpty()) {
            editpass.setError("Field cannot be empty");
            return false;
        } else {
            editpass.setError(null);

            return true;
        }
    }

    private void isUser() {
        final String userEnteredUsername = (String)(editemail.getText().toString().trim());
        Log.d("userEnteredUsername", userEnteredUsername);
        final String userEnteredPassword = editpass.getText().toString().trim();
        Log.d("userEnteredPassword", userEnteredPassword);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        Log.d("checkUser", String.valueOf(checkUser));
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    editemail.setError(null);
                    //editemail.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    Intent intent = new Intent(Login.this,Mystudent.class);
                    startActivity(intent);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        editpass.setError(null);
                        //editpass.setErrorEnabled(false);
                        startActivity(intent);
                    } else {
                        editpass.setError("Wrong Password");
                        editpass.requestFocus();
                    }
                } else {
                    editpass.setError("No such User exist");
                    editpass.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this,"Error in database. Try again",Toast.LENGTH_SHORT).show();
            }

        });
    }
}