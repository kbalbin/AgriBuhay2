package com.AgriBuhayProj.app.ProducerProductPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.AgriBuhayProj.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Producer_Order_Products extends AppCompatActivity {

    RecyclerView recyclerViewproduct;
    private List<ProducerPendingOrders> producerPendingOrdersList;
    private ProducerOrderProductsAdapter adapter;
    DatabaseReference reference;
    String RandomUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_order_products);
        recyclerViewproduct = findViewById(R.id.Recycle_orders_product);
        recyclerViewproduct.setHasFixedSize(true);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(this));
        producerPendingOrdersList = new ArrayList<>();
        Producerorderproducts();

    }

    private void Producerorderproducts() {

        RandomUID = getIntent().getStringExtra("RandomUID");
        //TODO this is the database for the ChefPendingOrders
        reference = FirebaseDatabase.getInstance().getReference("ProducerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                producerPendingOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProducerPendingOrders producerPendingOrders = snapshot.getValue(ProducerPendingOrders.class);
                    producerPendingOrdersList.add(producerPendingOrders);
                }
                adapter = new ProducerOrderProductsAdapter(Producer_Order_Products.this, producerPendingOrdersList);
                recyclerViewproduct.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
