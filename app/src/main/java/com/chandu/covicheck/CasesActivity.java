package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CasesActivity extends AppCompatActivity {

    private TextView textView_confirmed, textView_confirmed_new, textView_active, textView_active_new, textView_recovered, textView_recovered_new, textView_death, textView_death_new, textView_date, textview_time;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);

        textView_confirmed = findViewById(R.id.confirmed_textView);
        textView_confirmed_new = findViewById(R.id.confirmed_new_textView);
        textView_active = findViewById(R.id.active_textView);
        textView_active_new = findViewById(R.id.active_new_textView);
        textView_recovered = findViewById(R.id.recovered_textView);
        textView_recovered_new = findViewById(R.id.recovered_new_textView);
        textView_death = findViewById(R.id.death_textView);
        textView_death_new = findViewById(R.id.death_new_textView);
        textView_date = findViewById(R.id.date_textView);
        swipeRefreshLayout = findViewById(R.id.main_refreshLayout);
        textview_time = findViewById(R.id.time_textView);


        updateData();

//  pull dowm to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                isRefreshed = true;
                textView_confirmed.setText("0");
                textView_confirmed_new.setText("+0");
                textView_active.setText("0");
                textView_death.setText("0");
                textView_death_new.setText("+0");
                textView_recovered.setText("0");
                textView_recovered_new.setText("+0");
                updateData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CasesActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateData(){
        StatewiseDataService statewiseDataService = new StatewiseDataService(CasesActivity.this);
        statewiseDataService.getCases(new StatewiseDataService.Cases() {
            @Override
            public void onError(String message) {
                Toast.makeText(CasesActivity.this, "Something wrong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(StatewiseModel statewiseModel) {
                textView_confirmed.setText(statewiseModel.getConfirmed());
                textView_confirmed_new.setText("+"+statewiseModel.getDeltaconfirmed());
                textView_active.setText(statewiseModel.getActive());
                textView_death.setText(statewiseModel.getDeaths());
                textView_death_new.setText("+"+statewiseModel.getDeltadeaths());
                textView_recovered.setText(statewiseModel.getRecovered());
                textView_recovered_new.setText("+"+statewiseModel.getDeltarecovered());
                Date mDate = null;
                try {
                    mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(statewiseModel.getLastupdatedtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(mDate);
                textview_time.setText(dateFormat);
            }
        });
    }
    }
