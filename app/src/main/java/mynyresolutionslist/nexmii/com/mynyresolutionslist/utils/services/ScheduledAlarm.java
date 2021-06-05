package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.customclockops.DateTime;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by David on 9/25/2019 for Nexmii.
 */
public class ScheduledAlarm {

    private Context context;

    private static final int CHESTS_ALARM_REQUEST_CODE = 101;

    public ScheduledAlarm(Context ctx){
        this.context = ctx;
    }

    //delay is after how much time(in millis) from current time you want to schedule the notification
    public void scheduleAlarm() {

        Log.i("SPUTNIK", "FIRST ORBIT");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, CHESTS_ALARM_REQUEST_CODE
                , intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar chest_cleared_time = DateTime.setHourAndMinute(23,59);
        long numbered_chest_cleared_time = chest_cleared_time.getTimeInMillis();

        //For testing the alarm
        //long chest_cleared_time_non_calendar = System.currentTimeMillis() + 30000;

        alarmManager.set(AlarmManager.RTC_WAKEUP, numbered_chest_cleared_time
                , pendingIntent);

        Log.i("SPUTNIK", "SECOND ORBIT");
    }

    public void cancelFirstAlarm(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, CHESTS_ALARM_REQUEST_CODE
                , intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null)
            alarmManager.cancel(pendingIntent);//check
    }
}
