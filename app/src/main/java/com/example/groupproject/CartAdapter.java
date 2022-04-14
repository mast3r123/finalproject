package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartAdapter extends FirebaseRecyclerAdapter<Cart, CartAdapter.MyViewHolder> {
    public CartAdapter(FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    Context context;

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Cart model) {
        Log.i("Model", model.getName());
        holder.txtName.setText(model.getName());
        holder.txtPrice.setText(model.getPrice());
        holder.txtQuantity.setText(String.valueOf(model.getQuantity()));


        Glide.with(context)
                .load(model.getUrl())
                .into(holder.imgView);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();

        //Find collection cart and a child with corresponding name,
        //if present already increase the quantity amount
        holder.btnAdd.setOnClickListener(view -> {
            Query query = reference.child("cart").orderByChild("name").equalTo(model.getName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey(); // get child key
                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("quantity", model.getQuantity() + 1);
                    reference.child(path).updateChildren(result, (databaseError, databaseReference) -> {
                        Log.i("Pushed", "Pushed");
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });

        //Find collection cart and a child with corresponding name,
        //if present already decrease the quantity amount. If the amount is zero then remove from the collection
        holder.btnMinus.setOnClickListener(view -> {
            Query query = reference.child("cart").orderByChild("name").equalTo(model.getName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();

                    result.put("quantity", model.getQuantity() - 1);
                    reference.child(path).updateChildren(result);
                    if (model.getQuantity() == 1) {
                        reference.child(path).removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });

        //Find collection cart and a child with corresponding name,
        //if present already decrease the quantity amount
        holder.btnDelete.setOnClickListener(view -> {
            Query query = reference.child("cart").orderByChild("name").equalTo(model.getName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();

                    result.put("quantity", 0);
                    reference.child(path).updateChildren(result);
                    reference.child(path).removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });

        //Firebase query to count the total value by iterating over all the children in the collection
        Query query1 = reference.child("cart");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> orderItems = new ArrayList<String>();
                double count = 0;
                for (DataSnapshot foodTypeSnapshot: dataSnapshot.getChildren()) {
                    String name = foodTypeSnapshot.child("name").getValue(String.class);
                    String url = foodTypeSnapshot.child("url").getValue(String.class);
                    String price = foodTypeSnapshot.child("price").getValue(String.class);
                    double quantity = Double.valueOf(foodTypeSnapshot.child("quantity").getValue(double.class));

                    Integer finalPrice = Integer.parseInt(price.replace("$", ""));
                    Double total = finalPrice * quantity;
                    count += total;

                    // Pass orderItem data to the checkout activity.
                    String orderItemString = name + "|" + url + "|" + price + "|" + String.valueOf(quantity);
                    orderItems.add(orderItemString);
                }

                Log.d("TAG", count + "");
                Intent intent = new Intent("total-count");

                intent.putExtra("total", String.valueOf(count));
                intent.putStringArrayListExtra("orderitems", new ArrayList<String>(orderItems));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CartAdapter.MyViewHolder(inflater, parent);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView txtName;
        TextView txtPrice;
        ImageView imgView;
        TextView txtQuantity;
        Button btnAdd;
        Button btnMinus;
        Button btnDelete;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.cart_layout, parent, false));
            txtName = itemView.findViewById(R.id.txtCartProductName);
            txtPrice = itemView.findViewById(R.id.txtCartProductPrice);
            imgView = itemView.findViewById(R.id.imgCartProduct);
            txtQuantity = itemView.findViewById(R.id.txtCartQuantity);

            btnAdd = itemView.findViewById(R.id.btnIncreaseQty);
            btnMinus = itemView.findViewById(R.id.btnDecreaseQty);
            btnDelete = itemView.findViewById(R.id.btnDeleteProd);

        }

        @Override
        public void onClick(View view) {
            Log.i("onclick1", "clicked");
        }


    }
}
