package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button pinBtn, distBtn, btnSearchPin, btnSearchDist;
    EditText pinSearch, distSearch1, distSearch2;
    LinearLayout pinSearchLayout;
    RelativeLayout distSearchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//assign values to each control of layout
        pinBtn = findViewById(R.id.pinBtn);
        distBtn = findViewById(R.id.btnDist);
        btnSearchPin = findViewById(R.id.btnSearchPin);
        btnSearchDist = findViewById(R.id.btnSearchDist);



        pinSearchLayout = findViewById(R.id.linearPin);
        distSearchLayout = findViewById(R.id.realtiveDist);

//click listeners for each button
        pinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinSearchLayout.setVisibility(view.VISIBLE);
                distSearchLayout.setVisibility(view.GONE);
            }
        });

        distBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distSearchLayout.setVisibility(view.VISIBLE);
                pinSearchLayout.setVisibility(view.GONE);
            }
        });

        btnSearchPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=560076&date=20-07-2021";

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Error occured",Toast.LENGTH_LONG).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        btnSearchDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}