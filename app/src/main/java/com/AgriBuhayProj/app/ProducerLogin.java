package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AgriBuhayProj.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

//TODO: CHANGE NAME (ProducerLogin - LoginEmailProducer)
public class ProducerLogin extends AppCompatActivity {

    TextInputLayout email, pass;
    Button signIn, signInPhone;
    TextView forgotPass;
    TextView txt;

    String em;
    String pwd;

    FirebaseAuth FAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_login);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login As Producer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

            //XML CONNECTION
            email = findViewById(R.id.Lemail);
            pass = findViewById(R.id.Lpassword);
            signIn = findViewById(R.id.button4);
            txt = findViewById(R.id.textView3);
            forgotPass = findViewById(R.id.forgotpass);
            signInPhone = findViewById(R.id.btnphone);

            //FIREBASE INSTANCE
            FAuth = FirebaseAuth.getInstance();

            //BUTTON EVENTS
            //sign-in
            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //hide keyboard
                    hideKeyboard();
                    //get string
                    em = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();
                    //validation
                    if (isValid()) {
                        //progress dialog
                        final ProgressDialog mDialog = new ProgressDialog(ProducerLogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Logging in...");
                        mDialog.show();
                        //sign-in
                        FAuth.signInWithEmailAndPassword(em, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //database reference
                                    database = FirebaseDatabase.getInstance();
                                    userRef = database.getReference().child("User");
                                    //get user id
                                    String uID = task.getResult().getUser().getUid();
                                    //check user id if producer
                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.child(uID).child("Role").getValue().equals("Producer")){
                                                mDialog.dismiss();
                                                if (FAuth.getCurrentUser().isEmailVerified()) {
                                                    Toast.makeText(ProducerLogin.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(ProducerLogin.this, ProducerProductPanel_BottomNavigation.class));
                                                    finish();
                                                } else {
                                                    ReusableCodeForAll.ShowAlert(ProducerLogin.this, "", "Please Verify your Email");
                                                }
                                            }else{
                                                mDialog.dismiss();

                                                //signOut input
                                                FAuth.signOut();

                                                email.setErrorEnabled(true);
                                                email.setError("Account doesn't exist");
                                                pass.setErrorEnabled(true);
                                                pass.setError(" ");

                                                Toast.makeText(ProducerLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(ProducerLogin.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
            //register
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProducerLogin.this, Producer_Registration.class));
                    finish();
                }
            });
            //forgot password
            forgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProducerLogin.this, ProducerForgotPassword.class));
                }
            });
            //phone sign-in
            signInPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProducerLogin.this, Producerloginphone.class));
                    finish();
                }
            });
    }

    //VALIDATION
    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isValidEmail = false, isValidPassword = false, isValid;

        //email address
        if (TextUtils.isEmpty(em)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        } else if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            email.setErrorEnabled(true);
            email.setError("Enter a valid Email Address");
        }else{
            isValidEmail = true;
        }
        //password
        if (TextUtils.isEmpty(pwd)) {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");
        } else if (pwd.length() < 8){
            pass.setErrorEnabled(true);
            pass.setError("Password must be 8 to 16 characters long");
        }else{
            isValidPassword = true;
        }
        isValid = (isValidEmail && isValidPassword) ? true : false;
        return isValid;
    }

    //HIDE KEYBOARD
    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager hide = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            hide.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

