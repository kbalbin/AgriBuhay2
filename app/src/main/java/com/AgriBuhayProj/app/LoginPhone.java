package com.AgriBuhayProj.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.AgriBuhayProj.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

//TODO: CHANGE NAME (LoginPhone - LoginPhoneRetailer same with other LoginPhone
public class LoginPhone extends AppCompatActivity {

    EditText num;
    TextView txtsignup;
    Button sendotp,signinemail;
    CountryCodePicker cpp;

    FirebaseAuth FAuth;

    String numberr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login As Retailer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        num=(EditText)findViewById(R.id.number);
        sendotp=(Button)findViewById(R.id.otp);
        cpp=(CountryCodePicker)findViewById(R.id.CountryCode);
        signinemail=(Button)findViewById(R.id.btnEmail);
        txtsignup=(TextView)findViewById(R.id.acsignup);

        FAuth=FirebaseAuth.getInstance();

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberr=num.getText().toString().trim();
                String phoneNumber= cpp.getSelectedCountryCodeWithPlus() + numberr;
                Intent b=new Intent(LoginPhone.this,sendotp.class);
                b.putExtra("phoneNumber",phoneNumber);
                startActivity(b);
                finish();

            }
        });

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(LoginPhone.this,Registeration.class);
                startActivity(a);
                finish();
            }
        });

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent em=new Intent(LoginPhone.this, Login.class);
                startActivity(em);
                finish();
            }
        });

    }
}