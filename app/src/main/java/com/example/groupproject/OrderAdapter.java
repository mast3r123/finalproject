package com.example.groupproject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.MyViewHolder> {

    public OrderAdapter(FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position, @NonNull Order model) {
        holder.txtOrderListOrderDate.setText("Order Date: " + model.getOrderdate());
        holder.txtOrderListTotalPrice.setText("Total Price: " + model.getTotalprice());

        holder.model = model;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Order model;
        TextView txtOrderListOrderDate;
        TextView txtOrderListTotalPrice;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.order_list_row_layout, parent, false));
            txtOrderListOrderDate = itemView.findViewById(R.id.txtOrderListOrderDate);
            txtOrderListTotalPrice = itemView.findViewById(R.id.txtOrderListTotalPrice);
            
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent orderDetailIntent = new Intent(view.getContext(), OrderDetail.class);

            List<String> orderItemStrings = new ArrayList<String>();
            for (OrderItem orderItem : model.getOrderItems())
            {
                String orderItemString = orderItem.getName() + "|" + orderItem.getUrl() + "|" + orderItem.getPrice() + "|" + orderItem.getQuantity();
                orderItemStrings.add(orderItemString);
            }

            orderDetailIntent.putExtra("orderdetail_username", model.getUsername());
            orderDetailIntent.putStringArrayListExtra("orderdetail_orderitems", new ArrayList<String>(orderItemStrings));
            orderDetailIntent.putExtra("orderdetail_email", model.getEmail());
            orderDetailIntent.putExtra("orderdetail_address", model.getAddress());

            orderDetailIntent.putExtra("orderdetail_totalprice", model.getTotalprice());
            orderDetailIntent.putExtra("orderdetail_paymentinfo", model.getPaymentinfo());
            orderDetailIntent.putExtra("orderdetail_orderdate", model.getOrderdate());

            view.getContext().startActivity(orderDetailIntent);
        }
    }

}
