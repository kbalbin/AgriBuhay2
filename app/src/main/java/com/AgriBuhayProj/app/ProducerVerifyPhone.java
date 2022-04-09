package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AgriBuhayProj.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ProducerVerifyPhone extends AppCompatActivity {

    Button verify, resend;
    TextView txt;
    EditText enterCode;
    private ProgressDialog progress;

    String verificationId;
    String phoneNumber;

    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_verify_phone);

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Phone Verification");

        //XML
        enterCode = (EditText) findViewById(R.id.phoneno);
        txt = (TextView) findViewById(R.id.text);
        resend = (Button) findViewById(R.id.Resendotp);
        verify = (Button) findViewById(R.id.Verify);

        //PROGRESS DIALOG
        progress = new ProgressDialog(ProducerVerifyPhone.this);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

        //FIREBASE
        FAuth = FirebaseAuth.getInstance();

        //STRING
        phoneNumber = getIntent().getStringExtra("phoneNumber").trim();
        ReusableCodeForAll.ShowAlert(ProducerVerifyPhone.this, "PHONE NUMBER ENTERED", phoneNumber);

        //STARTUP (OTP)
        //send otp
        sendVerificationCode(phoneNumber);
        //count down timer
        new CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                //hide resend button
                resend.setVisibility(View.INVISIBLE);
                //show timer
                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code within " + millisUntilFinished/1000 + " Seconds");
            }
            @Override
            public void onFinish() {
                //hide
                txt.setVisibility(View.INVISIBLE);
                //show resend button
                resend.setVisibility(View.VISIBLE);
            }
        }.start();

        //BUTTON EVENTS.
        //verify code
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = enterCode.getText().toString().trim();
                resend.setVisibility(View.INVISIBLE);
                if (code.isEmpty() && code.length() < 6) {
                    enterCode.setError("Enter code");
                    enterCode.requestFocus();
                }
                verifyCode(code);
            }
        });
        //resend code
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide resend button
                resend.setVisibility(View.INVISIBLE);
                //resend OTP
                resendOTP(phoneNumber);
                //timer
                new CountDownTimer(60000, 1000) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code within " + millisUntilFinished / 1000 + " Seconds");
                    }
                    @Override
                    public void onFinish() {
                        resend.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.INVISIBLE);
                    }
                }.start();
            }
        });
    }

    //RESEND OTP
    private void resendOTP(String phoneNumber) {
        sendVerificationCode(phoneNumber);
    }

    //VERIFY OTP
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        linkCredential(credential);
    }

    //LINK ACCOUNT
    private void linkCredential(PhoneAuthCredential credential) {
        progress.setMessage("Verifying....");
        progress.show();
        FAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progress.dismiss();
                    /*Intent intent = new Intent(ChefVerifyPhone.this,MainMenu.class);
                    startActivity(intent);
                    finish();*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProducerVerifyPhone.this);
                    builder.setMessage("Phone Registration Successful! You can now login using phone number");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface sDialog, int i) {
                            //Direct to Main
                            startActivity(new Intent(ProducerVerifyPhone.this, MainMenu.class));
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    progress.dismiss();
                    ReusableCodeForAll.ShowAlert(ProducerVerifyPhone.this,"Error",task.getException().getMessage());
                }
            }
        });
    }

    //SEND OTP
    private void sendVerificationCode(String number) {
        /*PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FAuth).setPhoneNumber(phonenumber).setTimeout(60L,TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallBack).build();
        PhoneAuthProvider.verifyPhoneNumber(options);*/
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // CALLBACKS
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            //  super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                enterCode.setText(code);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ProducerVerifyPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    //DISABLE BACK PRESS
    public void onBackPressed(){ }
}

