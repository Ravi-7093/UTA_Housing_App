package com.example.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUP extends AppCompatActivity {
    TextInputLayout editname, editemail, editpassword,edit_cnf_password,edit_crs_date,edit_crs_end_date,edit_username;
    Button sign_in;
    DatabaseReference studentDbRef;
    FirebaseDatabase rootNode;



    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        editname =findViewById(R.id.full_name);
        editemail = findViewById(R.id.email);
        editpassword = findViewById(R.id.password);
        edit_cnf_password =findViewById(R.id.confirm_password);
        edit_username =findViewById(R.id.username);
        edit_crs_date = findViewById(R.id.crs_strt);
        edit_crs_end_date=findViewById(R.id.crs_end);
        sign_in =findViewById(R.id.signup);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertStudentData();
            }
        });


    }

    private Boolean validateName(){
        String value = String.valueOf(editname.getEditText().getText());

        if(value.isEmpty()){
            editname.setError("Field cannot be empty");
            return false;
        }
        else{
            editname.setError(null);
            return true;
        }


    }

    private Boolean validateEmail(){
        String value = String.valueOf(editemail.getEditText().getText());
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (value.isEmpty()) {
            editemail.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(emailPattern)) {
            editemail.setError("Invalid email address");
            return false;
        } else {
            editemail.setError(null);
            editemail.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validatePassword(){
        String value = String.valueOf(editpassword.getEditText().getText());
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if(value.isEmpty()){
            editpassword.setError("Field cannot be empty");
            return false;
        }
        else if (!value.matches(passwordVal)) {
            editpassword.setError("Password is too weak");
            return false;
        }
        else{
            editpassword.setError(null);
            editpassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateConfirmPassword(){
        String value = String.valueOf(edit_cnf_password.getEditText().getText());

        if(value.isEmpty()){
            edit_cnf_password.setError("Field cannot be empty");
            return false;
        }
        else{
            edit_cnf_password.setError(null);
            return true;
        }


    }

    private Boolean validateStartDate(){
        String value = String.valueOf(edit_crs_date.getEditText().getText());

        if(value.isEmpty()){
            edit_crs_date.setError("Field cannot be empty");
            return false;
        }
        else{
            edit_crs_date.setError(null);
            return true;
        }


    }
    private Boolean validateEndDate(){
        String value = String.valueOf(edit_crs_end_date.getEditText().getText());

        if(value.isEmpty()){
            edit_crs_end_date.setError("Field cannot be empty");
            return false;
        }
        else{
            edit_crs_end_date.setError(null);
            return true;
        }


    }
    private Boolean validateUsername() {
        String val = String.valueOf(edit_username.getEditText().getText());
        if (val.isEmpty()) {
            edit_username.setError("Field cannot be empty");
            return false;
        } else {
            edit_username.setError(null);
            edit_username.setErrorEnabled(false);
            return true;
        }
    }
     public void insertStudentData() {

        String name,mail,password,edit_cnfrm_password,crstartdate,crsenddate,username;
        name = String.valueOf(editname.getEditText().getText());
        name =name.trim();
        mail =String.valueOf(editemail.getEditText().getText());
        mail=mail.trim();
        password = String.valueOf(editpassword.getEditText().getText());
        password =password.trim();
        edit_cnfrm_password = String.valueOf(edit_cnf_password.getEditText().getText());
        edit_cnfrm_password=edit_cnfrm_password.trim();
        crstartdate = String.valueOf(edit_crs_date.getEditText().getText());
        crstartdate = crstartdate.trim();
        crsenddate = String.valueOf(edit_crs_end_date.getEditText().getText());
        crsenddate =crsenddate.trim();
         username = String.valueOf(edit_username.getEditText().getText()).trim();

         if(!validateName() | !validatePassword() | !validateConfirmPassword() | !validateEmail() | !validateStartDate() | !validateEndDate() | !validateUsername() ){
             return;
         }

        if(password.equals(edit_cnfrm_password)==false){
            Toast.makeText(SignUP.this,"Enter confirm password correctly",Toast.LENGTH_SHORT).show();
            return;
        }
        Students s = new Students(name,mail,password,edit_cnfrm_password,crstartdate,crsenddate,username);
         rootNode = FirebaseDatabase.getInstance();
         studentDbRef = rootNode.getReference("Students");

         studentDbRef.child(username).setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         //Log.d("Hellotwo",username);
                         // Data successfully inserted into the database
                         Toast.makeText(SignUP.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         // Data insertion failed
                         Toast.makeText(SignUP.this, "Data insertion failed", Toast.LENGTH_SHORT).show();
                     }
                 });
         //Toast.makeText(SignUP.this,"Account Created ",Toast.LENGTH_SHORT).show();


    }
}