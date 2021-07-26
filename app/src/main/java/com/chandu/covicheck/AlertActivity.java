package com.chandu.covicheck;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AlertActivity extends AppCompatActivity {

    private Button addAlert;
    private RadioGroup ageRadio, feeRadio;
    private RadioButton age, fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        createNotificationChannel();

        feeRadio = findViewById(R.id.feeRadio);
        ageRadio = findViewById(R.id.ageRadio);
        addAlert= findViewById(R.id.alertPin);

        addAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(feeRadio.getCheckedRadioButtonId() == -1 || ageRadio.getCheckedRadioButtonId() == -1){
                    Toast.makeText(AlertActivity.this, "Option not selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(AlertActivity.this, "Alert set", Toast.LENGTH_SHORT).show();

//                Log.d("AlertActivity", String.valueOf(feeRadio.getCheckedRadioButtonId()));
                age = findViewById(ageRadio.getCheckedRadioButtonId());
                fee = findViewById(feeRadio.getCheckedRadioButtonId());

                Bundle bundle = new Bundle();
                bundle.putString("fee", String.valueOf(fee.getText()));
                bundle.putString("age", String.valueOf(age.getText()));


                Intent intent = new Intent(AlertActivity.this, ReminderBroadcast.class);
                intent.putExtras(bundle);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlertActivity.this,0,intent,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                long tenSecondsInMillis = 1000*10;

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeAtButtonClick, tenSecondsInMillis, pendingIntent);
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