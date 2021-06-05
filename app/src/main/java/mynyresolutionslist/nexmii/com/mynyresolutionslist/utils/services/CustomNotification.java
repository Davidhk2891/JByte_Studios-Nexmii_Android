package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.NotificationsConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.QuotesAdditionActivity;

/**
 * Created by David on 9/27/2019 for Nexmii.
 */
class CustomNotification {

    static void setCustomNotification(Context context, String title, String content){

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(context, QuotesAdditionActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent
                (0, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap main_notification_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.notificationicon);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_format_quote_white_24dp)
                        .setLargeIcon(main_notification_icon)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = NotificationsConstants.CHEST_NOTIFICATION_CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    NotificationsConstants.CHEST_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(NotificationsConstants.NOTIFICATION_NEW_CHESTS_ID
                , mBuilder.build());
    }

}
