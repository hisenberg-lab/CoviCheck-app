package com.chandu.covicheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String fee,age;
        int ageInt;
        ListView alertList;

        String alertFormattedDate;
        Date alertDate;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyAlert")
                .setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("Time to get your JAB")
                .setContentText("Vaccine is available...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent alertIntent = new Intent(context,AlertActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        Log.d("AlertActivity","inside broadcast reciever");
        alertDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        alertFormattedDate = df.format(alertDate);

        SharedPreferences sh = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String shFee = sh.getString("fee","");
        int shAge = sh.getInt("age",0);
        String shPin = sh.getString("pin","");

        VaccineDataService vaccineDataService = new VaccineDataService(context);
        vaccineDataService.getAlertByPIn(shPin, alertFormattedDate,shFee, shAge, new VaccineDataService.AlertByPIN(){
            @Override
            public void onError(String message) {
                Log.d("AlertActivity","Something is wrong");
            }

            @Override
            public void onResponse(List<VaccineSlotModel> vaccineSlotModels) {



                if(!vaccineSlotModels.isEmpty())
                {

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(200, builder.build());

//                    cancelAlarm(context);
                }
            }
        });



    }

//    public void filterCenters(List<VaccineSlotModel> vaccineSlotModels,String age, String fee){
//        List<VaccineSlotModel> alertSpecific = ArrayList<>();
//        for
//    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
