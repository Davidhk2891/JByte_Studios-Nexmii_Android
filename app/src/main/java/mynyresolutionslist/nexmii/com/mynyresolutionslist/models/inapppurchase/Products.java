package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import eightbitlab.com.blurview.BlurView;

/**
 * Created by David on 9/9/2019 for Nexmii.
 */
public final class Products {

    //PRODUCTS//

    //no ads//
    public static void NO_ADS(AdView adView){
        adView.setVisibility(View.GONE);
    }

    public static void NO_ADS(AdView adView, View viewToBottom){
        adView.setVisibility(View.GONE);
        LayoutParams lp = (LayoutParams) viewToBottom.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        viewToBottom.setLayoutParams(lp);
    }

    public static void NO_ADS(AdView adView, FloatingActionButton fab1
            , FloatingActionButton fab2){
        adView.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.setMargins(0, 0, 60, 60);
        fab1.setLayoutParams(params);
        fab2.setLayoutParams(params);
    }
    //------//

    //vip emojies//
    public static void VIP_EMOJIES(vipEmojiesChecker vipEmojiesChecker){
        vipEmojiesChecker.onVipEmojiesActive();
    }

    public interface vipEmojiesChecker{
        void onVipEmojiesActive();
    }
    //-----------//

    //unlock 10 quote chests//
    //----------------------//

    //Context ctx, Activity activity, BlurView bv
    //revive resolutions//
    public static void REVIVE_RESOLUTIONS(Context ctx, Activity act, BlurView bv
            , ImageView revIv, Button refBtn, String resTitle, String resContent, int killedId){
        RevivalResolution revivalResolution = new RevivalResolution(ctx,act,bv);
        revivalResolution.reviveResolution(revIv,refBtn,resTitle,resContent,killedId);
    }
    //------------------//

}
