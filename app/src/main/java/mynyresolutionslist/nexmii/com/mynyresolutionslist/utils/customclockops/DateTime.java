package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.customclockops;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by David on 9/3/2019 for Mynyresolutionslist.
 */
public class DateTime {

    private int localHour,localMinute,date,month,year;

    public DateTime(){
        Calendar calendarObject = Calendar.getInstance();
        localHour = calendarObject.get(Calendar.HOUR_OF_DAY);
        localMinute = calendarObject.get(Calendar.MINUTE);
        date = calendarObject.get(Calendar.DATE);
        month = calendarObject.get(Calendar.MONTH);
        year = calendarObject.get(Calendar.YEAR);
        currentMonthHandler();
    }

    private void currentMonthHandler(){
        month = month + 1;
        if (month == 13){
            month = 1;
        }
        Log.i("month", "is " + month);
    }

    public String rawCurrentTimeDate(){

        //Phone's Actual time and date--------------------------------//
        String newFullDateSS;
        newFullDateSS = date + "-" + month + "-" +
                year;

        @SuppressLint("DefaultLocale")
        final String fullTotalDateSS
                = newFullDateSS + " " +
                String.format("%02d:%02d", localHour,
                        localMinute).toUpperCase(Locale
                        .getDefault());
        //------------------------------------------------------------//

        return fullTotalDateSS;
    }

    public static Calendar setHourAndMinute(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }
}
