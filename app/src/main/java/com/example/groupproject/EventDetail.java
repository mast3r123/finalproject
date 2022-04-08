package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
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

public class EventDetail extends AppCompatActivity {

    EventImagesAdapter imagesAdapter;
    ViewPager2 vPager;

    TextView txtEventDetailName;
    TextView txtEventDetailDescription;
    TextView txtEventDetailDuration;

    TextView txtEventDetailHostName;
    ImageView imgEventDetailHostImage;
    TextView txtEventDetailHostDescription;

    Button btnBuy;
    Button btnToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        txtEventDetailName = findViewById(R.id.txtEventDetailName);
        txtEventDetailDescription = findViewById(R.id.txtEventDetailDescription);
        txtEventDetailDuration = findViewById(R.id.txtEventDetailDuration);

        txtEventDetailHostName = findViewById(R.id.txtEventDetailHostName);
        imgEventDetailHostImage = findViewById(R.id.imgEventDetailHostImage);
        txtEventDetailHostDescription = findViewById(R.id.txtEventDetailHostDescription);

        btnBuy = findViewById(R.id.btnBuy);
        btnToList = findViewById(R.id.btnToList);

        setEventDetailData();


        btnBuy.setOnClickListener(view -> {
//            Intent checkOutIntent = new Intent(this, Checkout.class);
//            String id = i.getStringExtra("event_detail_id");
//            startActivity(checkOutIntent);
        });

        btnToList.setOnClickListener(view -> {
            Intent eventListIntent = new Intent(this, EventList.class);
            startActivity(eventListIntent);
        });
    }

    private void setEventDetailData(){
        Intent i = getIntent();

        setPagerViewImages();

        String name = i.getStringExtra("event_detail_name");
        String description = i.getStringExtra("event_detail_description");
        String duration = i.getStringExtra("event_detail_duration");

        String hostName = i.getStringExtra("event_detail_host_name");
        String hostImage = i.getStringExtra("event_detail_host_image");
        String hostDescription = i.getStringExtra("event_detail_host_description");

        txtEventDetailName.setText(name);
        txtEventDetailDescription.setText(description);
        txtEventDetailDuration.setText("Duration: " + duration);

        txtEventDetailHostName.setText(hostName);
        Glide.with(imgEventDetailHostImage.getContext()).load(hostImage).into(imgEventDetailHostImage);
        txtEventDetailHostDescription.setText(hostDescription);
    }

    private void setPagerViewImages () {
        final Bundle extra = getIntent().getExtras();
        ArrayList<String> imageUrls = extra.getStringArrayList("event_detail_images");
        imagesAdapter = new EventImagesAdapter(imageUrls);
        vPager = findViewById(R.id.viewPager2);

        // Show Left and Right Preview
        vPager.setClipToPadding(false);
        vPager.setClipChildren(false);
        vPager.setOffscreenPageLimit(2);
        vPager.setPadding(60,0, 60,0);
        vPager.setPageTransformer(new MarginPageTransformer(25));

        vPager.setAdapter(imagesAdapter);
    }
}