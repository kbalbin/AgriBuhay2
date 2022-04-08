package com.AgriBuhayProj.app.RetailerProductPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.AgriBuhayProj.app.Producer;
import com.AgriBuhayProj.app.ProducerProductPanel.UpdateProductModel;
import com.AgriBuhayProj.app.Retailer;
import com.AgriBuhayProj.app.RetailerProductPanel_BottomNavigation;

import com.AgriBuhayProj.app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrderProduct extends AppCompatActivity {


    String RandomId, ProducerID;
    ImageView imageView;
    ElegantNumberButton additem;
    TextView Productname, ProducerName, ProducerLoaction, ProductQuantity, ProductPrice, ProductDescription;
    DatabaseReference databaseReference, dataaa, producerdata, reference, data, dataref;
    String State, City, Sub, productname;
    int productprice;
    String custID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);


        Productname = (TextView) findViewById(R.id.product_name);
        ProducerName = (TextView) findViewById(R.id.producer_name);
        ProducerLoaction = (TextView) findViewById(R.id.producer_location);
        ProductQuantity = (TextView) findViewById(R.id.product_quantity);
        ProductPrice = (TextView) findViewById(R.id.product_price);
        ProductDescription = (TextView) findViewById(R.id.product_description);
        imageView = (ImageView) findViewById(R.id.image);
        additem = (ElegantNumberButton) findViewById(R.id.number_btn);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Retailer").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Retailer cust = dataSnapshot.getValue(Retailer.class);
                State = cust.getState();
                City = cust.getCity();
                Sub = cust.getSuburban();

                RandomId = getIntent().getStringExtra("ProductMenu");
                ProducerID = getIntent().getStringExtra("ProducerId");

                databaseReference = FirebaseDatabase.getInstance().getReference("ProductSupplyDetails").child(State).child(City).child(Sub).child(ProducerID).child(RandomId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateProductModel updateProductModel = dataSnapshot.getValue(UpdateProductModel.class);
                        Productname.setText(updateProductModel.getProducts());
                        String qua = "<b>" + "Quantity: " + "</b>" + updateProductModel.getQuantity();
                        ProductQuantity.setText(Html.fromHtml(qua));
                        String ss = "<b>" + "Description: " + "</b>" + updateProductModel.getDescription();
                        ProductDescription.setText(Html.fromHtml(ss));
                        String pri = "<b>" + "Price/kg: ₱ " + "</b>" + updateProductModel.getPrice();
                        ProductPrice.setText(Html.fromHtml(pri));
                        Glide.with(OrderProduct.this).load(updateProductModel.getImageURL()).into(imageView);

                        producerdata = FirebaseDatabase.getInstance().getReference("Producer").child(ProducerID);
                        producerdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Producer producer = dataSnapshot.getValue(Producer.class);

                                String name = "<b>" + "Producer Name: " + "</b>" + producer.getFname() + " " + producer.getLname();
                                ProducerName.setText(Html.fromHtml(name));
                                String loc = "<b>" + "Location: " + "</b>" + producer.getSuburban();
                                ProducerLoaction.setText(Html.fromHtml(loc));
                                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Cart cart = dataSnapshot.getValue(Cart.class);
                                        if (dataSnapshot.exists()) {
                                            additem.setNumber(cart.getProductQuantity());
                                        }
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
                additem.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dataref = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Cart cart1=null;
                                if (dataSnapshot.exists()) {
                                    int totalcount=0;
                                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        totalcount++;
                                    }
                                    int i=0;
                                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        i++;
                                        if(i==totalcount){
                                            cart1= snapshot.getValue(Cart.class);
                                        }
                                    }

                                    if (ProducerID.equals(cart1.getProducerId())) {
                                        data = FirebaseDatabase.getInstance().getReference("ProductSupplyDetails").child(State).child(City).child(Sub).child(ProducerID).child(RandomId);
                                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                UpdateProductModel update = dataSnapshot.getValue(UpdateProductModel.class);
                                                productname = update.getProducts();
                                                productprice = Integer.parseInt(update.getPrice());

                                                int num = Integer.parseInt(additem.getNumber());
                                                int totalprice = num * productprice;
                                                if (num != 0) {
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put("ProductName", productname);
                                                    hashMap.put("ProductID", RandomId);
                                                    hashMap.put("ProductQuantity", String.valueOf(num));
                                                    hashMap.put("Price", String.valueOf(productprice));
                                                    hashMap.put("Totalprice", String.valueOf(totalprice));
                                                    hashMap.put("ProducerId", ProducerID);
                                                    custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                    reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(OrderProduct.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                } else {

                                                    firebaseDatabase.getInstance().getReference("Cart").child(custID).child(RandomId).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderProduct.this);
                                        builder.setMessage("You can't add product items of multiple producers at a time. Try to add items of same producers");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                Intent intent = new Intent(OrderProduct.this, RetailerProductPanel_BottomNavigation.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                } else {
                                    data = FirebaseDatabase.getInstance().getReference("ProductSupplyDetails").child(State).child(City).child(Sub).child(ProducerID).child(RandomId);
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            UpdateProductModel update = dataSnapshot.getValue(UpdateProductModel.class);
                                            productname = update.getProducts();
                                            productprice = Integer.parseInt(update.getPrice());
                                            int num = Integer.parseInt(additem.getNumber());
                                            int totalprice = num * productprice;
                                            if (num != 0) {
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("ProductName", productname);
                                                hashMap.put("ProductID", RandomId);
                                                hashMap.put("ProductQuantity", String.valueOf(num));
                                                hashMap.put("Price", String.valueOf(productprice));
                                                hashMap.put("Totalprice", String.valueOf(totalprice));
                                                hashMap.put("ProducerId", ProducerID);
                                                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        Toast.makeText(OrderProduct.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } else {

                                                firebaseDatabase.getInstance().getReference("Cart").child(custID).child(RandomId).removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}
