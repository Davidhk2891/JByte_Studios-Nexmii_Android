package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.QuoteConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

/**
 * Created by David on 9/25/2019 for Nexmii.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //TODO: NOT GETTING CALLED. FIND OUT WHY

        Log.i("SPUTNIK", "1.0");

        if (Preferences.getDefaults(QuoteConstants.FIRST_CHEST_KEY, context) != null){

            Log.i("SPUTNIK", "1.1");

            Toast.makeText(context, "purple 1", Toast.LENGTH_SHORT).show();

            if (!Preferences.getDefaults(QuoteConstants.FIRST_CHEST_KEY, context)
                    .equals(QuoteConstants.VAL_UNUSED)){

                Log.i("SPUTNIK", "1.2");

                Toast.makeText(context, "purple 1.1", Toast.LENGTH_SHORT).show();

                Preferences.setDefaults(QuoteConstants.FIRST_CHEST_KEY, QuoteConstants.VAL_UNUSED
                        , context);
                CustomNotification.setCustomNotification(context, context.getResources().getString(R
                        .string.noti_new_quotes), context.getResources()
                        .getString(R.string.noti_your_quote_chest));
            }
            QuoteConstants.FROM_NOTIFICATION = true;
        }

        if (Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY, context) != null){

            Log.i("SPUTNIK", "2.0");

            Toast.makeText(context, "purple 2", Toast.LENGTH_SHORT).show();


            if (!Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY, context)
                    .equals(QuoteConstants.VAL_UNUSED)) {

                Log.i("SPUTNIK", "2.1");

                Toast.makeText(context, "purple 2.1", Toast.LENGTH_SHORT).show();

                Preferences.setDefaults(QuoteConstants.ALL_CHESTS_KEY, QuoteConstants.VAL_UNUSED
                        , context);
                CustomNotification.setCustomNotification(context, context.getResources().getString(R
                        .string.noti_new_quotes), context.getResources()
                        .getString(R.string.noti_your_quote_chest));
            }
            QuoteConstants.FROM_NOTIFICATION = true;
        }
    }
}
