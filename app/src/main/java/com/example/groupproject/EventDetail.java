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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            Intent i = getIntent();
            Bundle extra = i.getExtras();
            ArrayList<String> url = extra.getStringArrayList("event_detail_images");
            String imageUrl = url.get(0);
            Cart cart = new Cart(i.getStringExtra("event_detail_name"), imageUrl, "$100", 1.0);

            Query queryToGetData = rootRef.child("cart")
                    .orderByChild("name").equalTo(i.getStringExtra("event_detail_name"));

            queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()){
                        rootRef.child("cart").push().setValue(cart, (databaseError, databaseReference) -> {
                            Log.i("Pushed", "Pushed");
                            startActivity();
                        });
                    } else {
                        startActivity();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("Cancelled", "Cancelled");
                }
            });
        });

        btnToList.setOnClickListener(view -> {
            Intent eventListIntent = new Intent(this, EventList.class);
            startActivity(eventListIntent);
        });
    }

    private void startActivity() {
        Intent myIntent = new Intent(this, CartActivity.class);
        this.startActivity(myIntent);
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