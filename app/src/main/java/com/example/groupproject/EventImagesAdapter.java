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

import java.util.List;

public class EventImagesAdapter extends RecyclerView.Adapter<EventImagesAdapter.MyViewHolder> {

    private List<String> imageUrls;

    public EventImagesAdapter(List<String> imgUrls){
        imageUrls = imgUrls;
    }

    @NonNull
    @Override
    public EventImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EventImagesAdapter.MyViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(holder.imgEventDetailImage.getContext()).load(imageUrl).into(holder.imgEventDetailImage);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgEventDetailImage;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pagerview, parent, false));
            imgEventDetailImage = itemView.findViewById(R.id.imgEventDetailImage);
        }
    }

}

