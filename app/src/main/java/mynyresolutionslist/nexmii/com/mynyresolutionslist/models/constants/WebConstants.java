package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants;

import android.content.Context;

/**
 * Created by David on 9/10/2019 for Nexmii.
 */
public class WebConstants {
    //App's package//
    public static String packageName(Context ctx){
        return ctx.getPackageName();
    }

    public static final String FEEDBACK_URL = "market://details?id=";
    public static final String FEEDBACK_URL_BACKUP = "https://play.google.com/store/apps/details?id=";
}
