package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    final private String TAG = "MainActivity";

    Button pinBtn, distBtn, btnSearchPin, btnSearchDist;
    EditText pinSearch;
    LinearLayout pinSearchLayout;
    RelativeLayout distSearchLayout;
    Spinner distSearch1, distSearch2;


    ArrayList<StateDistModel> stateModelArrayList = new ArrayList<StateDistModel>();
    ArrayList<String> stateNames = new ArrayList<String>();

    ArrayList<StateDistModel> districtModelArrayList = new ArrayList<StateDistModel>();
    ArrayList<String> distNames = new ArrayList<String>();



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

        distSearch1 = findViewById(R.id.distSearch1);
        distSearch2 = findViewById(R.id.distSearch2);

//get current date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);
        Log.d(TAG,formattedDate);

//populate spinner 1
        VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
        vaccineDataService.getStateDist(stateModelArrayList, stateNames, distSearch1, 999);

//click listeners for each button
        pinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinBtn.setAlpha(0.7f);
                distBtn.setAlpha(1.0f);
                pinSearchLayout.setVisibility(view.VISIBLE);
                distSearchLayout.setVisibility(view.GONE);

            }
        });

        distBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distBtn.setAlpha(0.7f);
                pinBtn.setAlpha(1.0f);
                distSearchLayout.setVisibility(view.VISIBLE);
                pinSearchLayout.setVisibility(view.GONE);
            }
        });

        btnSearchPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
                vaccineDataService.getVaccineByPIN(pinSearch.getText().toString(), formattedDate, new VaccineDataService.VaccineByPIN() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(VaccineSlotModel vaccineSlotModel) {
                        Toast.makeText(MainActivity.this, vaccineSlotModel.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        btnSearchDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "District"+String.valueOf(districtModelArrayList.get(distSearch2.getSelectedItemPosition()).getID()));
                VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
                vaccineDataService.getVaccineByDist(districtModelArrayList.get(distSearch2.getSelectedItemPosition()).getID(), formattedDate);
            }
        });

        distSearch1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "State"+String.valueOf(stateModelArrayList.get(distSearch1.getSelectedItemPosition()).getID()));
                districtModelArrayList = new ArrayList<StateDistModel>();
                distNames = new ArrayList<String>();
                VaccineDataService vaccineDataService2 = new VaccineDataService(MainActivity.this);
                vaccineDataService2.getStateDist(districtModelArrayList, distNames, distSearch2, stateModelArrayList.get(distSearch1.getSelectedItemPosition()).getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}