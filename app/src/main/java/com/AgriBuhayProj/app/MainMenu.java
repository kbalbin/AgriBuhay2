package com.AgriBuhayProj.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.AgriBuhayProj.app.R;
public class MainMenu extends AppCompatActivity {

    Button signinemail, signinphone, signup;
    ImageView bgimage;

    private Boolean backPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        signinemail = findViewById(R.id.SignwithEmail);
        signinphone = findViewById(R.id.SignwithPhone);
        signup = findViewById(R.id.SignUp);

        //BUTTON EVENTS
        //login by email
        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ChooseOne.class).putExtra("Home", "Email"));
            }
        });
        //login by phone
        signinphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ChooseOne.class).putExtra("Home", "Phone"));
            }
        });
        //register account
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ChooseOne.class).putExtra("Home", "Register"));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    //EXIT APP
    public void onBackPressed(){
        if(backPress){
            super.onBackPressed();
        }
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        backPress = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPress = false;
            }
        },5000);
    }
}
