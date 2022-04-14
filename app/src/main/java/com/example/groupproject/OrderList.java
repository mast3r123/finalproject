package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OrderList extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Query query;
    OrderAdapter adapter;
    RecyclerView orderListRowView;

    Button btnSignOut;
    Button btnEventListFromOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){

            setUserOrderList();

            btnSignOut = findViewById(R.id.btnSignOut);
            btnEventListFromOrders = findViewById(R.id.btnEventListFromOrders);

            btnSignOut.setOnClickListener(view -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"You have successfully signed out", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(view.getContext(), MainActivity.class);
                                startActivity(i);
                            }
                        });
            });

            btnEventListFromOrders.setOnClickListener(view -> {
                Intent i = new Intent(this, EventList.class);
                startActivity(i);
            });


        }else{
            // User is not logged in.
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void setUserOrderList (){
        query = FirebaseDatabase.getInstance().getReference().child("orders").orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());

        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new OrderAdapter(options);

        orderListRowView = findViewById(R.id.orderListRowView);
        orderListRowView.setAdapter(adapter);
        orderListRowView.setLayoutManager(new LinearLayoutManager(this));
    }
}
