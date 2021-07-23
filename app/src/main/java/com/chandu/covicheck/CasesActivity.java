package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CasesActivity extends AppCompatActivity {

    private TextView textView_confirmed, textView_confirmed_new, textView_active, textView_active_new, textView_recovered, textView_recovered_new, textView_death, textView_death_new, textView_date, textview_time;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PieChart pieChart;



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

        pieChart = findViewById(R.id.pieChart_view);



        updateData();
        initPieChart();
//        showPieChart();


//  pull down to refresh
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
                initPieChart();
//                showPieChart();
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
//
//                active = Integer.valueOf(statewiseModel.getActive());
//                confirmed = Integer.valueOf(statewiseModel.getConfirmed());
//                death = Integer.valueOf(statewiseModel.getDeaths());
//                recovered = Integer.valueOf(statewiseModel.getRecovered());

                Date mDate = null;
                try {
                    mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(statewiseModel.getLastupdatedtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(mDate);
                textview_time.setText(dateFormat);

                showPieChart(Integer.valueOf(statewiseModel.getActive()),Integer.valueOf(statewiseModel.getConfirmed()),Integer.valueOf(statewiseModel.getDeaths()));
            }
        });


    }

    private void showPieChart(int active, int confirmed, int death){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "Cases";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Confirmed",confirmed);
        typeAmountMap.put("Active",active);
        typeAmountMap.put("Death",death);
//        typeAmountMap.put("Recovered",recovered);


        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0091EA"));//lightblue_a700

        colors.add(Color.parseColor("#C60202"));//redBackground
        colors.add(Color.parseColor("#FFAB00"));//replyOrange


//        colors.add(Color.parseColor("#00C853"));//green_a700



        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieChart.setExtraOffsets(30.f, 5.f, 30.f, 5.f);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);

        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieData.setValueFormatter(new PercentFormatter());

        pieData.setValueTextColor(Color.WHITE);

        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieData.notifyDataChanged();
        pieChart.notifyDataSetChanged();
    }

    private void initPieChart() {
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);


        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);
        Description description = pieChart.getDescription();

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EasingOption.EaseOutSine);
//        pieChart.animateX(1400, Easing.EasingOption.EaseOutBounce);

        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#FE1D1D2F"));

        pieChart.getLegend().setTextColor(R.color.white);


    }
}
