package com.AgriBuhayProj.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {

    Button Producer, Retailer, LogisticsPerson;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);
        Producer = (Button) findViewById(R.id.producer);
        LogisticsPerson = (Button) findViewById(R.id.delivery);
        Retailer = (Button) findViewById(R.id.retailer);
        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        //TODO Find something that can replace with this line of code
        Producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")) {
                    Intent loginemail = new Intent(ChooseOne.this, ProducerLogin.class);
                    startActivity(loginemail);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphone = new Intent(ChooseOne.this, Producerloginphone.class);
                    startActivity(loginphone);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Register = new Intent(ChooseOne.this, Producer_Registration.class);
                    startActivity(Register);


                }

            }
        });

        Retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Email")) {
                    Intent loginemailcust = new Intent(ChooseOne.this, Login.class);
                    startActivity(loginemailcust);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphonecust = new Intent(ChooseOne.this, LoginPhone.class);
                    startActivity(loginphonecust);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Registercust = new Intent(ChooseOne.this, Registeration.class);
                    startActivity(Registercust);
                }
            }
        });

        LogisticsPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("SignUp")) {
                    Intent Registerdelivery = new Intent(ChooseOne.this, Logistics_registration.class);
                    startActivity(Registerdelivery);
                }
                if (type.equals("Phone")) {
                    Intent loginphone = new Intent(ChooseOne.this, Logistics_LoginPhone.class);
                    startActivity(loginphone);
                    finish();
                }
                if (type.equals("Email")) {
                    Intent loginemail = new Intent(ChooseOne.this, Logistics_Login.class);
                    startActivity(loginemail);
                    finish();
                }
            }
        });
    }
}
