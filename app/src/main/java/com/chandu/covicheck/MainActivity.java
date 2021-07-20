package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button pinSearch, distSearch;
    LinearLayout pinSearchLayout;
    RelativeLayout distSearchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//assign values to each control of layout
        pinSearch = findViewById(R.id.pinBtn);
        distSearch = findViewById(R.id.btnDist);

        pinSearchLayout = findViewById(R.id.linearPin);
        distSearchLayout = findViewById(R.id.realtiveDist);

//click listeners for each button
        pinSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinSearchLayout.setVisibility(view.VISIBLE);
                distSearchLayout.setVisibility(view.GONE);
            }
        });

        distSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distSearchLayout.setVisibility(view.VISIBLE);
                pinSearchLayout.setVisibility(view.GONE);
            }
        });
    }
}