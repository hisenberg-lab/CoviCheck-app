package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.android.volley.Request.Method.*;

public class MainActivity extends AppCompatActivity {

    Button pinBtn, distBtn, btnSearchPin, btnSearchDist;
    EditText pinSearch;
    LinearLayout pinSearchLayout;
    RelativeLayout distSearchLayout;
    Spinner distSearch1, distSearch2;


    ArrayList<StateModel> stateModelArrayList = new ArrayList<StateModel>();
    ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distSearch1 = findViewById(R.id.distSearch1);

        VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
        vaccineDataService.getStates(stateModelArrayList, names, distSearch1);

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
                VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
                vaccineDataService.getVaccineByPIN(pinSearch.getText().toString(), formattedDate);
            }

        });

    }
}