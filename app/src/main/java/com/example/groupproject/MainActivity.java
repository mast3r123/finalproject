package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("hangu", "hangu commit test");

        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(view -> {
            Intent i = new Intent(this, EventList.class);
            startActivity(i);
        });

    }
}