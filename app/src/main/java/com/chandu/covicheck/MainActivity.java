package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.android.volley.Request.Method.*;

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

        pinSearch = findViewById(R.id.pinSearch);

        pinSearchLayout = findViewById(R.id.linearPin);
        distSearchLayout = findViewById(R.id.realtiveDist);

//get current date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);
        Log.d("MainActivity",formattedDate);

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
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pinSearch.getText()+"&date="+formattedDate;

                JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
            }
        });

        btnSearchDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}