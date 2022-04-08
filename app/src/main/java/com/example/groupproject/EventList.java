package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EventList extends AppCompatActivity {

    Query query;
    EventListAdapter adapter;
    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Log.i("eventlist", "hangu");

        // Show Plant List
        query = FirebaseDatabase.getInstance().getReference().child("events");
        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        adapter = new EventListAdapter(options);

        Log.i("eventlist  query", query.toString());
        Log.i("eventlist  adapter", adapter.toString());

        rView = findViewById(R.id.rView);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

        Log.i("eventlist", "hangu333");

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}