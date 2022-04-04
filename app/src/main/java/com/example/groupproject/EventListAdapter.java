package com.example.groupproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class EventListAdapter extends FirebaseRecyclerAdapter<Event, EventListAdapter.MyViewHolder> {

    public EventListAdapter(FirebaseRecyclerOptions<Event> options) {
        super(options);
    }

    @NonNull
    @Override
    public EventListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, int position, @NonNull Event model) {
        holder.txtEventListName.setText(model.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtEventListName;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_layout, parent, false));
            txtEventListName = itemView.findViewById(R.id.txtEventListName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
