package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;

import static com.android.volley.Request.Method.*;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "MainActivity";

    private Button pinBtn, distBtn, btnSearchPin, btnSearchDist;
    private EditText pinSearch;
    private LinearLayout pinSearchLayout;
    private RelativeLayout distSearchLayout;
    private Spinner distSearch1, distSearch2;
    private RecyclerView rv_slots;
    private ProgressBar progress;
//    TextView noVaccine;

    ArrayList<StateDistModel> stateModelArrayList = new ArrayList<StateDistModel>();
    ArrayList<String> stateNames = new ArrayList<String>();

    ArrayList<StateDistModel> districtModelArrayList = new ArrayList<StateDistModel>();
    ArrayList<String> distNames = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

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

        rv_slots = findViewById(R.id.rv_slots);

        progress = findViewById(R.id.progress);
//        noVaccine = findViewById(R.id.noVaccine);

        if(getSupportActionBar()!= null) {
            getSupportActionBar().hide();
        }



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
                rv_slots.setVisibility(view.GONE);
                pinBtn.setAlpha(0.7f);
                distBtn.setAlpha(1.0f);
                pinSearchLayout.setVisibility(view.VISIBLE);
                distSearchLayout.setVisibility(view.GONE);
            }
        });

        distBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_slots.setVisibility(view.GONE);
                distBtn.setAlpha(0.7f);
                pinBtn.setAlpha(1.0f);
                distSearchLayout.setVisibility(view.VISIBLE);
                pinSearchLayout.setVisibility(view.GONE);
            }
        });

        btnSearchPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(view.VISIBLE);

//  hide keyboard
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
                vaccineDataService.getVaccineByPIN(pinSearch.getText().toString(), formattedDate, new VaccineDataService.VaccineByPIN() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<VaccineSlotModel> vaccineSlotModels) {
//                        Toast.makeText(MainActivity.this, vaccineSlotModels.toString(),Toast.LENGTH_SHORT).show();

//  put the list into listview

//                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_activated_1, vaccineSlotModels);
// recyclerView adapter
                        SlotsRecViewAdapter adapter = new SlotsRecViewAdapter();
                        adapter.setSlots(vaccineSlotModels);

                        progress.setVisibility(view.GONE);
//                        lv_slots.setAdapter((arrayAdapter));
                        rv_slots.setAdapter(adapter);
                        rv_slots.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        rv_slots.setVisibility(view.VISIBLE);
                        pinSearchLayout.setVisibility(view.GONE);

//                        if(arrayAdapter.isEmpty()){
//
//                        }
//                        else {
//                        lv_slots.setAdapter((arrayAdapter));
//                        lv_slots.setVisibility(view.VISIBLE);
//                        pinSearchLayout.setVisibility(view.GONE);inSearchLayout.setVisibility(view.GONE);
//                        }

                    }
                });
            }

        });

        btnSearchDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "District"+String.valueOf(districtModelArrayList.get(distSearch2.getSelectedItemPosition()).getID()));
                progress.setVisibility(view.VISIBLE);
                VaccineDataService vaccineDataService = new VaccineDataService(MainActivity.this);
                vaccineDataService.getVaccineByDist(districtModelArrayList.get(distSearch2.getSelectedItemPosition()).getID(), formattedDate, new VaccineDataService.VaccineByDist() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<VaccineSlotModel> vaccineSlotModels) {
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_activated_1, vaccineSlotModels);
//                        lv_slots.setAdapter((arrayAdapter));
                        SlotsRecViewAdapter adapter = new SlotsRecViewAdapter();
                        adapter.setSlots(vaccineSlotModels);

                        distSearchLayout.setVisibility(view.GONE);
                        progress.setVisibility(view.GONE);

                        rv_slots.setAdapter(adapter);
                        rv_slots.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        rv_slots.setVisibility(view.VISIBLE);
                        pinSearchLayout.setVisibility(view.GONE);


                    }
                });
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