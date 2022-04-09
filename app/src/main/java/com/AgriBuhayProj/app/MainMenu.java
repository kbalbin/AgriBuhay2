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
        signinemail = (Button) findViewById(R.id.SignwithEmail);
        signinphone = (Button) findViewById(R.id.SignwithPhone);
        signup = (Button) findViewById(R.id.SignUp);

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signemail = new Intent(MainMenu.this, ChooseOne.class);
                signemail.putExtra("Home", "Email");
                startActivity(signemail);
                finish();
            }
        });

        signinphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signphone = new Intent(MainMenu.this, ChooseOne.class);
                signphone.putExtra("Home", "Phone");
                startActivity(signphone);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainMenu.this, ChooseOne.class);
                signup.putExtra("Home", "SignUp");
                startActivity(signup);
                finish();
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    //DISABLE BACK PRESS
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
        },2000);
    }
}
