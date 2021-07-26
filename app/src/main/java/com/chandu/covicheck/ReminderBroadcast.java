package com.chandu.covicheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyAlert")
                .setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("Time to get your JAB")
                .setContentText("Vaccine is available...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        VaccineDataService vaccineDataService = new VaccineDataService(context);
        vaccineDataService.getAlertByPIn("560083", "08-07-2021", new VaccineDataService.AlertByPIN(){
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

                    cancelAlarm(context);
                }
            }
        });



    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
