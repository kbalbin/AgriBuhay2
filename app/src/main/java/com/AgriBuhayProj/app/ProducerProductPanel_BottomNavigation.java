package com.AgriBuhayProj.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.AgriBuhayProj.app.ProducerProductPanel.ProducerHomeFragment;
import com.AgriBuhayProj.app.ProducerProductPanel.ProducerPendingOrdersFragment;
import com.AgriBuhayProj.app.ProducerProductPanel.ProducerProfileFragment;
import com.AgriBuhayProj.app.ProducerProductPanel.ProducerOrderFragment;

import com.AgriBuhayProj.app.SendNotification.Token;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class ProducerProductPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Boolean backPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_product_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.producer_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("Orderpage")) {
                loadcheffragment(new ProducerPendingOrdersFragment());
            } else if (name.equalsIgnoreCase("Confirmpage")) {
                loadcheffragment(new ProducerOrderFragment());
            } else if (name.equalsIgnoreCase("AcceptOrderpage")) {
                loadcheffragment(new ProducerHomeFragment());
            } else if (name.equalsIgnoreCase("Deliveredpage")) {
                loadcheffragment(new ProducerHomeFragment());
            }
        } else {
            loadcheffragment(new ProducerHomeFragment());
        }
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private boolean loadcheffragment(Fragment fragment) {
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
            case R.id.chefHome:
                fragment = new ProducerHomeFragment();
                break;

            case R.id.PendingOrders:
                fragment = new ProducerPendingOrdersFragment();
                break;

            case R.id.Orders:
                fragment = new ProducerOrderFragment();
                break;
            case R.id.chefProfile:
                fragment = new ProducerProfileFragment();
                break;
        }
        return loadcheffragment(fragment);
    }

    //DISABLE BACK PRESS
    public void onBackPressed(){

    }
}
