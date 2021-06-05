package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by David on 9/10/2019 for Nexmii.
 */
public class Preferences {

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void savedPreferencesChecker(String key, Context context, PreferencesInterface
            preferencesInterface){
        if (Preferences.getDefaults(key, context) != null) {
            preferencesInterface.preferenceIsSaved();
        }else{
            preferencesInterface.preferenceIsNotSaved();
        }
    }
}
