package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase;

import android.content.Context;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

/**
 * App version checker.
 * This class handles checking which version is the user standing on.
 * (FREE or PREMIUM). Depending on which, there are two abstracts methods from an interface
 * which allow for specific actions depending on the current app version
 */
public final class AppFlavor {

    private Context context;

    public AppFlavor(Context ctx){
        this.context = ctx;
    }

    public void appVersionChecker(AppVersionInterface appVersionInterface){
        if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context) != null){
            if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context)
                    .equals(InAppBillingConstants.SAVED)){
                //App is Premium
                appVersionInterface.onAppBeingPremium();
            }
        }else{
            //App is Free
            appVersionInterface.onAppBeingFree();
        }
    }

    public interface AppVersionInterface{
        void onAppBeingFree();
        void onAppBeingPremium();
    }

}
