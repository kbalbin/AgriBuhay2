package com.AgriBuhayProj.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseOne extends AppCompatActivity {

    Button Producer, Retailer, LogisticsPerson;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);
        Producer = findViewById(R.id.producer);
        LogisticsPerson = findViewById(R.id.delivery);
        Retailer = findViewById(R.id.retailer);

        intent = getIntent();
        type = intent.getStringExtra("Home").trim();

        //TODO Find something that can replace with this line of code (DONE)
        Producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case ("Email"):
                        startActivity(new Intent(ChooseOne.this, ProducerLogin.class));
                        finish();
                        break;
                    case "Phone":
                        startActivity(new Intent(ChooseOne.this, Producerloginphone.class));
                        finish();
                        break;
                    case "Register":
                        startActivity(new Intent(ChooseOne.this, Producer_Registration.class));
                        finish();
                        break;
                }
            }
        });

        Retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case ("Email"):
                        startActivity(new Intent(ChooseOne.this, Login.class));
                        finish();
                        break;
                    case "Phone":
                        startActivity(new Intent(ChooseOne.this, LoginPhone.class));
                        finish();
                        break;
                    case "Register":
                        startActivity(new Intent(ChooseOne.this, Registeration.class));
                        finish();
                        break;
                }
            }
        });

        LogisticsPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case ("Email"):
                        startActivity(new Intent(ChooseOne.this, Logistics_Login.class));
                        finish();
                        break;
                    case "Phone":
                        startActivity(new Intent(ChooseOne.this, Logistics_LoginPhone.class));
                        finish();
                        break;
                    case "Register":
                        startActivity(new Intent(ChooseOne.this, Logistics_registration.class));
                        finish();
                        break;
                }
            }
        });
    }
}
