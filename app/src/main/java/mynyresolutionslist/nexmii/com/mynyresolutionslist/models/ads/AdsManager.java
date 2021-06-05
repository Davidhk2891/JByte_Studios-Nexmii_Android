package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.Products;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.PreferencesInterface;

/**
 * Created by David on 9/10/2019 for Nexmii.
 */
public class AdsManager {

    public AdsManager(){
    }

    public void adOps(Context context, final AdView adView){
        Preferences.savedPreferencesChecker(InAppBillingConstants.PREMIUM_EQUIPPED, context
                , new PreferencesInterface() {
                    @Override
                    public void preferenceIsSaved() {
                        //don't show ad
                        Products.NO_ADS(adView);
                    }

                    @Override
                    public void preferenceIsNotSaved() {
                        //show ad
                        AdRequest adRequest = new AdRequest.Builder().build();
                        adView.loadAd(adRequest);
                    }
                });
    }

    public void adOps(Context context, final AdView adView, final View viewToBottom){
        Preferences.savedPreferencesChecker(InAppBillingConstants.PREMIUM_EQUIPPED, context
            , new PreferencesInterface() {
                @Override
                public void preferenceIsSaved() {
                    //don't show ad
                    Products.NO_ADS(adView, viewToBottom);
                }

                @Override
                public void preferenceIsNotSaved() {
                    //show ad
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                }
            });
    }
    public void adOps(Context context, final AdView adView, final FloatingActionButton fab1
            , final FloatingActionButton fab2){
        Preferences.savedPreferencesChecker(InAppBillingConstants.PREMIUM_EQUIPPED, context
            , new PreferencesInterface() {
                @Override
                public void preferenceIsSaved() {
                    //don't show ad
                    Products.NO_ADS(adView, fab1, fab2);
                }

                @Override
                public void preferenceIsNotSaved() {
                    //show ad
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                }
            });
    }
    public void adOps(Context context, final AdChoices adChoices){

        Preferences.savedPreferencesChecker(InAppBillingConstants.PREMIUM_EQUIPPED, context
            , new PreferencesInterface() {
                @Override
                public void preferenceIsSaved() {
                    //don't show ad
                    adChoices.adsInactive();
                }

                @Override
                public void preferenceIsNotSaved() {
                    //show ad
                    adChoices.adsActive();
                }
            });
    }
}
