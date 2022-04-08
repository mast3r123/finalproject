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

        Log.i("hangu3444", "what");
        Log.i("model id", model.getId());
        Log.i("model g", model.getGroup());
        Log.i("model hn", model.getHost_name());
        Log.i("model d", model.getHost_description());
        Log.i("model h im", model.getHost_image());
        Log.i("model getDuration", model.getDuration());
        Log.i("model desc", model.getDescription());
        Log.i("model getImages 0", model.getImages().get(0));
        Log.i("model getImages 1", model.getImages().get(1));
        Log.i("model getImages 02", model.getImages().get(2));

        Glide.with(holder.imgEventListImage.getContext()).load(model.getImages().get(0)).into(holder.imgEventListImage);
        holder.txtEventListName.setText(model.getName());
        holder.txtEventListDuration.setText(model.getDuration());
        holder.txtEventListHostName.setText("Host: " + model.getHost_name());
        holder.txtEventListDescription.setText(model.getDescription().substring(0, 50) + "...");

        holder.model = model;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Event model;

        ImageView imgEventListImage;
        TextView txtEventListName;
        TextView txtEventListDuration;
        TextView txtEventListHostName;
        TextView txtEventListDescription;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_layout, parent, false));
            imgEventListImage = itemView.findViewById(R.id.imgEventListImage);
            txtEventListName = itemView.findViewById(R.id.txtEventListName);
            txtEventListDuration = itemView.findViewById(R.id.txtEventListDuration);
            txtEventListHostName = itemView.findViewById(R.id.txtEventListHostName);
            txtEventListDescription = itemView.findViewById(R.id.txtEventListDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent eventDetailIntent = new Intent(view.getContext(), EventDetail.class);

            eventDetailIntent.putExtra("event_detail_id", model.getId());
            eventDetailIntent.putExtra("event_detail_name", model.getName());
            eventDetailIntent.putExtra("event_detail_group", model.getGroup());
            eventDetailIntent.putExtra("event_detail_description", model.getDescription());
            eventDetailIntent.putExtra("event_detail_duration", model.getDuration());

            eventDetailIntent.putStringArrayListExtra("event_detail_images", new ArrayList<String>(model.getImages()));

            eventDetailIntent.putExtra("event_detail_host_name", model.getHost_name());
            eventDetailIntent.putExtra("event_detail_host_image", model.getHost_image());
            eventDetailIntent.putExtra("event_detail_host_description", model.getHost_description());

            view.getContext().startActivity(eventDetailIntent);
        }
    }

}
