package com.AgriBuhayProj.app.ProducerProductPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.AgriBuhayProj.app.Printer.ProducerPrintOrder;
import com.AgriBuhayProj.app.Producer;

import com.AgriBuhayProj.app.R;
import com.AgriBuhayProj.app.SendNotification.APIService;
import com.AgriBuhayProj.app.SendNotification.Client;
import com.AgriBuhayProj.app.SendNotification.Data;
import com.AgriBuhayProj.app.SendNotification.MyResponse;
import com.AgriBuhayProj.app.SendNotification.NotificationSender;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProducerPreparedOrderView extends AppCompatActivity {


    RecyclerView recyclerViewproduct;
    private List<ProducerFinalOrders> producerFinalOrdersList;
    private ProducerPreparedOrderViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID, userid;
    TextView grandtotal, address, name, number;
    LinearLayout l1;
    Button Prepared;
    private Button printOrder;
    private ProgressDialog progressDialog;
    private APIService apiService;
    Spinner Shipper;
    String deliveryId = "ZhDir9TVrbWxM1cMBmSnP7HEakj1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_prepared_order_view);
        recyclerViewproduct = findViewById(R.id.Recycle_viewOrder);
        grandtotal = findViewById(R.id.Gtotal);
        address = findViewById(R.id.Cadress);
        name = findViewById(R.id.Cname);
        number = findViewById(R.id.Cnumber);
        l1 = findViewById(R.id.linear);
        Shipper = findViewById(R.id.shipper);
        Prepared = findViewById(R.id.prepared);
        printOrder = findViewById(R.id.print);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(ProducerPreparedOrderView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerViewproduct.setHasFixedSize(true);
        recyclerViewproduct.setLayoutManager(new LinearLayoutManager(ProducerPreparedOrderView.this));
        producerFinalOrdersList = new ArrayList<>();
        ProducerorderproductsView();
    }

    private void ProducerorderproductsView() {

        RandomUID = getIntent().getStringExtra("RandomUID");
        //TODO this is the database for the ChefFinalOrders DONE
        reference = FirebaseDatabase.getInstance().getReference("ProducerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                producerFinalOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProducerFinalOrders producerFinalOrders = snapshot.getValue(ProducerFinalOrders.class);
                    producerFinalOrdersList.add(producerFinalOrders);
                }
                if (producerFinalOrdersList.size() == 0) {
                    l1.setVisibility(View.INVISIBLE);

                } else {
                    l1.setVisibility(View.VISIBLE);
                    Prepared.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();
                            //TODO this is the database for the Chef DONE
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("Producer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final Producer producer = dataSnapshot.getValue(Producer.class);
                                    final String ProducerName = producer.getFname() + " " + producer.getLname();
                                    //TODO this is the database for the ChefFinalOrders DONE
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ProducerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Products");
                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                final ProducerFinalOrders producerFinalOrders = dataSnapshot1.getValue(ProducerFinalOrders.class);
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                String productid = producerFinalOrders.getProductId();
                                                userid = producerFinalOrders.getUserId();
                                                hashMap.put("ProducerId", producerFinalOrders.getProducerId());
                                                hashMap.put("ProductId", producerFinalOrders.getProductId());
                                                hashMap.put("ProductName", producerFinalOrders.getProductName());
                                                hashMap.put("ProductPrice", producerFinalOrders.getProductPrice());
                                                hashMap.put("ProductQuantity", producerFinalOrders.getProductQuantity());
                                                hashMap.put("RandomUID", RandomUID);
                                                hashMap.put("TotalPrice", producerFinalOrders.getTotalPrice());
                                                hashMap.put("UserId", producerFinalOrders.getUserId());
                                                //TODO this is the database for the DeliveryShipOrders DONE
                                                FirebaseDatabase.getInstance().getReference("LogisticsShipOrders").child(deliveryId).child(RandomUID).child("Products").child(productid).setValue(hashMap);
                                            }
                                            //TODO this is the database for the ChefFinalOrders DONE
                                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("ProducerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    final ProducerFinalOrders1 producerFinalOrders1 = dataSnapshot.getValue(ProducerFinalOrders1.class);
                                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                                    String producerid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    hashMap1.put("Address", producerFinalOrders1.getAddress());
                                                    hashMap1.put("ProducerId", producerid);
                                                    hashMap1.put("ProducerName", ProducerName);
                                                    hashMap1.put("GrandTotalPrice", producerFinalOrders1.getGrandTotalPrice());
                                                    hashMap1.put("MobileNumber", producerFinalOrders1.getMobileNumber());
                                                    hashMap1.put("Name", producerFinalOrders1.getName());
                                                    hashMap1.put("RandomUID", RandomUID);
                                                    hashMap1.put("Status", "Order is Prepared");
                                                    hashMap1.put("UserId", userid);
                                                    //TODO this is the database for the DeliveryShipOrders DONE
                                                    FirebaseDatabase.getInstance().getReference("LogisticsShipOrders").child(deliveryId).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            //TODO this is the database for the CustomerFinalOrders DONE
                                                            FirebaseDatabase.getInstance().getReference("RetailerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    //TODO this is the database for the Tokens
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is Prepared", "Prepared");
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                }
                                                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(deliveryId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
                                                                            sendNotifications(usertoken, "New Order", "You have a New Order", "DeliveryOrder");
                                                                            progressDialog.dismiss();
                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ProducerPreparedOrderView.this);
                                                                            builder.setMessage("Order has been sent to shipper");
                                                                            builder.setCancelable(false);
                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {

                                                                                    dialog.dismiss();
                                                                                    Intent b = new Intent(ProducerPreparedOrderView.this, ProducerPreparedOrder.class);
                                                                                    startActivity(b);
                                                                                    finish();


                                                                                }
                                                                            });
                                                                            AlertDialog alert = builder.create();
                                                                            alert.show();
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                }
                                                            });


                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });
                    printOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(ProducerPreparedOrderView.this, ProducerPrintOrder.class));
                        }
                    });

                }
                adapter = new ProducerPreparedOrderViewAdapter(ProducerPreparedOrderView.this, producerFinalOrdersList);
                recyclerViewproduct.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //TODO this is the database for the ChefFinalOrders DONE
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProducerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProducerFinalOrders1 producerFinalOrders1 = dataSnapshot.getValue(ProducerFinalOrders1.class);
                grandtotal.setText("??? " + producerFinalOrders1.getGrandTotalPrice());
                address.setText(producerFinalOrders1.getAddress());
                name.setText(producerFinalOrders1.getName());
                number.setText("+63" + producerFinalOrders1.getMobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String prepared) {

        Data data = new Data(title, message, prepared);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(ProducerPreparedOrderView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
