package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CasesActivity extends AppCompatActivity {

    private TextView textView_confirmed, textView_confirmed_new, textView_active, textView_active_new, textView_recovered, textView_recovered_new, textView_death, textView_death_new, textView_tests, textView_date, textView_tests_new, textview_time;
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
        textView_tests = findViewById(R.id.tests_textView);
        textView_date = findViewById(R.id.date_textView);
        textView_tests_new = findViewById(R.id.tests_new_textView);
        swipeRefreshLayout = findViewById(R.id.main_refreshLayout);
        textview_time = findViewById(R.id.time_textView);

//  pull dowm to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                isRefreshed = true;
//                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CasesActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }
