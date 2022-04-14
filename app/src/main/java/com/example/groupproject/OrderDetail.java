package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail extends AppCompatActivity {

    OrderItemAdapter orderItemAdapter;
    RecyclerView orderDetailRowView;

    TextView txtOrderDetailTotalPrice;
    TextView txtOrderDetailPaymentInfo;
    TextView txtOrderDetailOrderDate;
    TextView txtOrderDetailUsername;
    TextView txtOrderDetailEmail;
    TextView txtOrderDetailAddress;
    List<OrderItem> orderItems;
    ArrayList<String> orderItemStrings;
    Button btnOrderDetailToOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderDetailRowView = findViewById(R.id.orderDetailRowView);
        txtOrderDetailTotalPrice = findViewById(R.id.txtOrderDetailTotalPrice);
        txtOrderDetailPaymentInfo = findViewById(R.id.txtOrderDetailPaymentInfo);
        txtOrderDetailOrderDate = findViewById(R.id.txtOrderDetailOrderDate);
        txtOrderDetailUsername = findViewById(R.id.txtOrderDetailUsername);
        txtOrderDetailEmail = findViewById(R.id.txtOrderDetailEmail);
        txtOrderDetailAddress = findViewById(R.id.txtOrderDetailAddress);
        btnOrderDetailToOrderList = findViewById(R.id.btnOrderDetailToOrderList);

        setOrderDetailData();

        btnOrderDetailToOrderList.setOnClickListener(view -> {
            Intent orderListIntent = new Intent(this, OrderList.class);
            startActivity(orderListIntent);
        });

    }

    private void setOrderDetailData(){
        Intent i = getIntent();

        String totalprice = i.getStringExtra("orderdetail_totalprice");
        String paymentinfo = i.getStringExtra("orderdetail_paymentinfo");
        String orderdate = i.getStringExtra("orderdetail_orderdate");
        String username = i.getStringExtra("orderdetail_username");
        String email = i.getStringExtra("orderdetail_email");
        String address = i.getStringExtra("orderdetail_address");

        txtOrderDetailTotalPrice.setText(totalprice);
        txtOrderDetailPaymentInfo.setText(paymentinfo);
        txtOrderDetailOrderDate.setText("Order Date: " + orderdate);
        txtOrderDetailUsername.setText("Name: " + username);
        txtOrderDetailEmail.setText("Email: " + email);
        txtOrderDetailAddress.setText("Address: " + address);

        final Bundle extra = getIntent().getExtras();
        orderItems = new ArrayList<>();
        orderItemStrings = extra.getStringArrayList("orderdetail_orderitems");
        for (String orderItemString : orderItemStrings)
        {
            String[] items = orderItemString.split("\\|");
            OrderItem orderItem = new OrderItem(items[0], items[1], items[2], items[3]);
            orderItems.add(orderItem);
        }

        orderItemAdapter = new OrderItemAdapter(orderItems);
        orderDetailRowView.setAdapter(orderItemAdapter);
        orderDetailRowView.setLayoutManager(new LinearLayoutManager(this));
    }

}