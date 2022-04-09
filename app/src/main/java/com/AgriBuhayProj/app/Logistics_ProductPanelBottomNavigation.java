package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.AgriBuhayProj.app.LogisticsProductPanel.LogisticsPendingOrderFragment;
import com.AgriBuhayProj.app.LogisticsProductPanel.LogisticsShipOrderFragment;

import com.AgriBuhayProj.app.SendNotification.Token;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Logistics_ProductPanelBottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Boolean backPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics__product_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("DeliveryOrderpage"))
            {
                loaddeliveryfragment(new LogisticsPendingOrderFragment());
            }

        } else {
            loaddeliveryfragment(new LogisticsPendingOrderFragment());
        }

    }

    private void UpdateToken() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private boolean loaddeliveryfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.pendingorders:
                fragment = new LogisticsPendingOrderFragment();
                break;

            case R.id.shiporders:
                fragment = new LogisticsShipOrderFragment();
                break;

        }
        return loaddeliveryfragment(fragment);
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
