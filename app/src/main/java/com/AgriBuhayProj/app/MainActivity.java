package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView pic1,pic2;
    Animation slide,fade;

    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic1 = findViewById(R.id.logo1);
        pic2 = findViewById(R.id.logo2);

        slide = AnimationUtils.loadAnimation(this,R.anim.slide);
        fade = AnimationUtils.loadAnimation(this,R.anim.fadein);

        pic1.setVisibility(View.GONE);
        pic2.setVisibility(View.GONE);

        pic1.setVisibility(View.VISIBLE);
        pic1.setAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pic2.setVisibility(View.VISIBLE);
                pic2.setAnimation(fade);

                //AUTO SIGN-IN FOR CURRENT USER
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //check logged-in user
                        Fauth = FirebaseAuth.getInstance();
                        if (Fauth.getCurrentUser() != null) {
                            if (Fauth.getCurrentUser().isEmailVerified()) {
                                Fauth = FirebaseAuth.getInstance();
                                databaseReference = firebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid() + "/Role");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String role = dataSnapshot.getValue(String.class);
                                        //Todo database name role path Customer, Chef, DeliveryPerson DONE
                                        if (role.equals("Retailer")) {
                                            Intent n = new Intent(MainActivity.this, RetailerProductPanel_BottomNavigation.class);
                                            startActivity(n);
                                            finish();
                                        }
                                        if (role.equals("Producer")) {
                                            Intent a = new Intent(MainActivity.this, ProducerProductPanel_BottomNavigation.class);
                                            startActivity(a);
                                            finish();
                                        }
                                        if (role.equals("LogisticsPerson")) {
                                            Intent intent = new Intent(MainActivity.this, Logistics_ProductPanelBottomNavigation.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Check whether you have verified your details, Otherwise please verify");
                                builder.setCancelable(false);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                                Fauth.signOut();
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                }, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
