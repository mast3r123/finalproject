package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    FirebaseRecyclerAdapter adapter;
    RecyclerView rView;
    Button btnCheckout;
    Button btnToEventList;
    Query query;
    TextView textTotal;
    ArrayList<String> orderItemStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("total-count"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String total = intent.getStringExtra("total");
            textTotal.setText("$" + total);

            final Bundle extra = intent.getExtras();
            orderItemStrings = extra.getStringArrayList("orderitems");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        btnCheckout = findViewById(R.id.btnCheckout);
        textTotal = findViewById(R.id.textViewTotal);
        rView = findViewById(R.id.cartRecyclerView);

        //Fetch cart collection and pass the data to the recycler view
        query = FirebaseDatabase.getInstance().getReference().child("cart");
        FirebaseRecyclerOptions<Cart> cart = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(query, Cart.class).build();
        adapter = new CartAdapter(cart);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        btnCheckout.setOnClickListener(view -> {
            //If total is zero do not start the next acitivty
            if (!textTotal.getText().equals("$0.0")) {
                Intent myIntent = new Intent(this, CheckoutActivity.class);
                myIntent.putExtra("total", textTotal.getText());
                myIntent.putStringArrayListExtra("orderitems", orderItemStrings);
                this.startActivity(myIntent);
            }
        });
//        btnToEventList.setOnClickListener(view -> {
//            Intent newIntent = new Intent(this, EventList.class);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(newIntent);
//        });
    }
}