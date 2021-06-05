package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdChoices;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.AdsConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.QuoteConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.AppFlavor;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

/**
 * Created by David on 9/18/2019 for Nexmii.
 */
public class ChestsControl implements RewardedVideoAdListener {

    private Context context;
    private AdsManager adsManager;
    private RewardedVideoAd rva;
    private Button done;
    private CardView mPrevCardView;
    private TextView quote, author, instructions;
    private QuotesSelectorOps qso;
    private LottieAnimationView chestAnimated;
    private LottieAnimationView playVidAnimation;
    private AppFlavor appFlavor;

    private int chestsLeft;

    public static ChestsInterface.secondChest secondChestAccess;

    private boolean premium_chest_ready = false;

    public ChestsControl(Context ctx, LottieAnimationView chestAnimated, LottieAnimationView
            playVidAnimation, TextView quote, TextView author, CardView cardView
            , TextView instructions, Button done) {
        this.context = ctx;
        this.chestAnimated = chestAnimated;
        this.playVidAnimation = playVidAnimation;
        this.quote = quote;
        this.author = author;
        this.mPrevCardView = cardView;
        this.instructions = instructions;
        this.done = done;
        playVidAnimation.setVisibility(View.GONE);
        chestAnimated.setFrame(1);
        mPrevCardView.animate().alpha(0f).translationYBy(100f).setDuration(10);
        adsManager = new AdsManager();
        rva = MobileAds.getRewardedVideoAdInstance(ctx);
        rva.setRewardedVideoAdListener(this);
        qso = new QuotesSelectorOps(ctx);
        appFlavor = new AppFlavor(context);
        doneBtnOps(0f, false, 1);
        loadVideo();
    }

    private void doneBtnOps(float visibility, boolean clickable, int duration){
        if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context) != null){
            if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context)
                    .equals(InAppBillingConstants.SAVED)){
                if (visibility == 1f) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) done
                            .getLayoutParams();
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    lp.setMargins(10, 0, 10, 20);
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    done.setLayoutParams(lp);
                }
            }
        }
        done.animate().alpha(visibility).setDuration(duration);
        done.setClickable(clickable);
    }

    public void animateChest(){
        chestAnimated.resumeAnimation();
        chestAnimated.setClickable(false);
        onChestAnimationFinished();
        qso.quoteArraySelector(quote, author);
    }

    private void onChestAnimationFinished(){
        new CountDownTimer(300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                fadeInChestQuote();
            }
        }.start();
    }

    private void fadeInChestQuote(){
        mPrevCardView.animate().alpha(1f).setDuration(1500);
        mPrevCardView.animate().setStartDelay(500);
        mPrevCardView.animate().translationYBy(-50f);
        new CountDownTimer(3500, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                //When first quote is shown and second chest(free or premium is ready to play)
//                if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context)
//                        != null){
//                    if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, context)
//                            .equals(InAppBillingConstants.SAVED)){
//
//                    }
//                }
                if (premium_chest_ready){
                    resetForAfterSecondChestOpenedPremium();
                }else{
                    clearAllAnimationsFreeChest();
                }
            }
        }.start();
    }

    public void clearAllAnimationsFreeChest(){
        chestAnimated.setFrame(1);
        chestAnimated.setClickable(true);
        adsManager.adOps(context, new AdChoices() {
            @Override
            public void adsActive() {
                resetForSecondChestRegularBeforeVideo();
            }

            @Override
            public void adsInactive() {
                resetForSecondChestPremium();
            }
        });
    }

    private void resetInstructionsRelativePosition(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, done.getId());
        params.setMargins(0,0,0,30);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        instructions.setLayoutParams(params);
    }

    //The following has 2 ways of happening. If premium bought, open next chest asap, else, video//
    //Regular flow//
    private void resetForSecondChestRegularBeforeVideo(){
        doneBtnOps(1f, true, 500);
        resetInstructionsRelativePosition();
        chestAnimated.setClickable(false);
        playVidAnimation.setVisibility(View.VISIBLE);
        playVidAnimation.playAnimation();
        instructions.setText(R.string.choose_2nd_chest_regular);
        watchVideo();
    }
    //Below is what happens after closing the video ad
    public void resetForSecondChestRegularAfterVideoOrPremium(){
        Log.i("GREEN1", "ran");
        doneBtnOps(1f, true, 500);
        if (playVidAnimation.isAnimating()) {
            playVidAnimation.cancelAnimation();
            playVidAnimation.setVisibility(View.GONE);
        }
        chestAnimated.setClickable(false);
        chestAnimated.setFrame(1);
        chestAnimated.resumeAnimation();
        qso.quoteArraySelector(quote, author);
        mPrevCardView.animate().alpha(1f).setDuration(2000);
        Log.i("CARD_POSITIONING_2", "RAN");
        instructions.setText(R.string.choose_2nd_chest_regular_after_vid);
        resetInstructionsRelativePosition();
        Log.i("GREEN1.1", "ran");
        new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                secondChestAccess.secondChestOpened();
            }
        }.start();
    }
    private void resetForAfterSecondChestOpenedPremium(){
        cardPositionReset();
        Log.i("CARD_POSITIONING_1", "RAN");
        //Here make the message modification
        appFlavor.appVersionChecker(new AppFlavor.AppVersionInterface() {
            @Override
            public void onAppBeingFree() {
                instructions.setText(R.string.choose_2nd_chest_regular_after_vid);
            }

            @Override
            public void onAppBeingPremium() {
                if (chestsLeft == 1){
                    String finalSentence = context.getResources()
                            .getString(R.string.you_have_opened_all);
                    instructions.setText(finalSentence);
                }else {
                    chestsLeft = 10 - QuoteConstants.PREMIUM_CHESTS_COUNTER;
                    String finalSentence = context.getResources().getString(R.string.you_have) + " "
                            + chestsLeft + " " + context.getResources()
                            .getString(R.string.chests_left_to_open);
                    instructions.setText(finalSentence);
                }
            }
        });
        new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                secondChestAccess.secondChestOpened();
            }
        }.start();
    }
    private void loadVideo(){
        if (!rva.isLoaded()){
            rva.loadAd(AdsConstants.AD_REWARDED_WISH_ID, new AdRequest.Builder().build());
        }
    }
    //Lottie animation as parameter
    private void watchVideo(){
        playVidAnimation.setOnClickListener(view -> rva.show());
    }
    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {
        mPrevCardView.animate().alpha(0f).setDuration(10);
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        //THIS IS THE ONE THAT RUNS AFTER CLOSING EVERYTHING ON THE AFTER VIDEO SCREEN THING
        resetForSecondChestRegularAfterVideoOrPremium();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
    //------------//

    //Premium flow//
    private void resetForSecondChestPremium(){
        premium_chest_ready = true;
        cardPositionReset();
        Log.i("CARD_POSITIONING_2", "RAN");
        chestAnimated.setFrame(1);
        chestAnimated.setClickable(true);
        chestsLeft = 10 - Integer.parseInt(Preferences.getDefaults(QuoteConstants
                        .SAVED_PREMIUM_CHEST_COUNTER, context));
        String finalSentence = context.getResources().getString(R.string.you_have) + " "
                + chestsLeft + " " + context.getResources()
                .getString(R.string.chests_left_to_open);
        instructions.setText(finalSentence);
        doneBtnOps(1f, true, 500);
        resetInstructionsRelativePosition();
    }
    //-----------//

    private void cardPositionReset() {
        try{
            if (Integer.parseInt(Preferences.getDefaults(QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER
                    , context)) != 0) {
                Log.i("STATUS_CHEST_COUNTER", Preferences.getDefaults
                        (QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER, context));
                if (Integer.parseInt(Preferences.getDefaults(QuoteConstants
                                .SAVED_PREMIUM_CHEST_COUNTER, context)) < 9) {
                    //CAN STILL OPEN CHESTS
                    premiumChestOpsAfterOpened();
                }else{
                    //LIMIT REACHED
                    premiumChestLocked();
                }
            }else{
                premiumChestOpsAfterOpened();
            }
        }catch(NullPointerException | NumberFormatException e){
            premiumChestOpsAfterOpened();
        }
    }

    private void premiumChestOpsAfterOpened(){
        chestAnimated.setClickable(true);
        mPrevCardView.animate().translationYBy(50f).setDuration(10);
        chestAnimated.setFrame(1);
        if (Preferences.getDefaults(QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER,context) != null){
            QuoteConstants.PREMIUM_CHESTS_COUNTER = Integer.parseInt(Preferences.getDefaults
                    (QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER,context));
        }
        QuoteConstants.PREMIUM_CHESTS_COUNTER++;
        Preferences.setDefaults(QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER
                , String.valueOf(QuoteConstants.PREMIUM_CHESTS_COUNTER), context);
        Log.i("PREMIUM_CHEST_COUNTER_1", "IS AT: " + QuoteConstants.PREMIUM_CHESTS_COUNTER);
        Log.i("STATUS_SAVED_COUNTER", "is " + Preferences.getDefaults
                (QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER, context));
        instructions.setText("You have " + Preferences.getDefaults(QuoteConstants
                .SAVED_PREMIUM_CHEST_COUNTER, context) + "left to open");
    }

    private void premiumChestLocked(){
        mPrevCardView.animate().translationYBy(50f).setDuration(10);
        chestAnimated.setFrame(1);
        Log.i("STATUS_SAVED_COUNTER", "is " + Preferences.getDefaults
                (QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER, context));
        instructions.setText(context.getResources().getString(R.string.you_have_opened_all));
        chestAnimated.setClickable(false);
    }
    //-------------------------------------------------------------------------------------------//
}
