package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlertActivity extends AppCompatActivity {

    private Button addAlert,deleteButton;
    private RadioGroup ageRadio, feeRadio;
    private RadioButton age, fee;
    private RecyclerView alertList;
    private String alertFormattedDate;
    private Date alertDate;
    private TextView alertSearch,savedAlert;
    private LinearLayout alertLinear;
    private ProgressBar progressAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        createNotificationChannel();

        feeRadio = findViewById(R.id.feeRadio);
        ageRadio = findViewById(R.id.ageRadio);
        addAlert= findViewById(R.id.alertPin);
        alertList = findViewById(R.id.alertList);
        alertSearch = findViewById(R.id.alertSearch);
        savedAlert = findViewById(R.id.savedAlert);
        deleteButton = findViewById(R.id.deleteButton);
        alertLinear = findViewById(R.id.alertLinear);
        progressAlert = findViewById(R.id.progressAlert);

        alertDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        alertFormattedDate = df.format(alertDate);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String shFee = sh.getString("fee","");
        int shAge = sh.getInt("age",0);
        String shPin = sh.getString("pin","");




        if( shAge != 0){
            progressAlert.setVisibility(View.VISIBLE);
            VaccineDataService vaccineDataService = new VaccineDataService(AlertActivity.this);
            vaccineDataService.getAlertByPIn(shPin, alertFormattedDate,shFee, shAge, new VaccineDataService.AlertByPIN(){
                @Override
                public void onError(String message) {
                    Log.d("AlertActivity","Something is wrong");
                }

                @Override
                public void onResponse(List<VaccineSlotModel> vaccineSlotModels) {

                    SlotsRecViewAdapter alertAdapter = new SlotsRecViewAdapter();
                    alertAdapter.setSlots(vaccineSlotModels);
                    alertList.setAdapter(alertAdapter);
                    alertList.setLayoutManager(new LinearLayoutManager(AlertActivity.this));

                }
            });

            alertLinear.setVisibility(View.VISIBLE);
            progressAlert.setVisibility(View.GONE);
            savedAlert.setText("PIN: "+shPin+", Fee Type: "+shFee+", Min age: "+shAge);
        }



        addAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if(feeRadio.getCheckedRadioButtonId() == -1 || ageRadio.getCheckedRadioButtonId() == -1 || (addAlert.getText().equals(""))){
                    Toast.makeText(AlertActivity.this, "Option not selected", Toast.LENGTH_SHORT).show();
                    return;
                }




                Toast.makeText(AlertActivity.this, "Alert set", Toast.LENGTH_SHORT).show();

//                Log.d("AlertActivity", String.valueOf(feeRadio.getCheckedRadioButtonId()));
                age = findViewById(ageRadio.getCheckedRadioButtonId());
                fee = findViewById(feeRadio.getCheckedRadioButtonId());


                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                int ageInt;
                if(age.getText().equals( "18-44")){
                    ageInt = 18;
                }else{
                    ageInt=45;
                }

                myEdit.putString("fee", String.valueOf(fee.getText()));
                myEdit.putInt("age",ageInt);
                myEdit.putString("pin", String.valueOf(alertSearch.getText()));

                myEdit.commit();

                Intent intent = new Intent(AlertActivity.this, ReminderBroadcast.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlertActivity.this,0,intent,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                long tenSecondsInMillis = 1000*10;

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick, tenSecondsInMillis, pendingIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
                myEdit.commit();

                savedAlert.setText("");
                alertLinear.setVisibility(view.GONE);
                ReminderBroadcast reminderBroadcast = new ReminderBroadcast();
                reminderBroadcast.cancelAlarm(AlertActivity.this);
            }
        });
    }



    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "CoviCheckAlertChhannel";
            String description = "Channel for Vaccine Alert";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyAlert",name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}