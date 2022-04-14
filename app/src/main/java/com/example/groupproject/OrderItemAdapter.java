package com.example.groupproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {

    private List<OrderItem> orderItems;

    public OrderItemAdapter(List<OrderItem> items){
        orderItems = items;
    }

    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.txtOrderItemName.setText(orderItem.getName());
        holder.txtOrderItemPrice.setText(orderItem.getPrice());
        holder.txtOrderItemQuantity.setText(orderItem.getQuantity());
        Glide.with(holder.imgOrderItem.getContext()).load(orderItem.getUrl()).into(holder.imgOrderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOrderItem;
        TextView txtOrderItemName;
        TextView txtOrderItemPrice;
        TextView txtOrderItemQuantity;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.order_item_row_layout, parent, false));
            imgOrderItem = itemView.findViewById(R.id.imgOrderItem);
            txtOrderItemName = itemView.findViewById(R.id.txtOrderItemName);
            txtOrderItemPrice = itemView.findViewById(R.id.txtOrderItemPrice);
            txtOrderItemQuantity = itemView.findViewById(R.id.txtOrderItemQuantity);
        }
    }

}