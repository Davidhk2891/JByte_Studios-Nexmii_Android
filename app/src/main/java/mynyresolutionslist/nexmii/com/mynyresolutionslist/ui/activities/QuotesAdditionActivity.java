package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.ChestsControl;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.QuoteConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.AppFlavor;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.ScheduledAlarm;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

public class QuotesAdditionActivity extends AppCompatActivity {

    private ChestsControl chestsControl;
    private AppFlavor appFlavor;
    private ScheduledAlarm scheduledAlarm;

    private LottieAnimationView mAnimatedChest;
    private CardView mPrevCardView;
    private TextView mChest_message;

    private int chestsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_addition);

        //Navigate back to parent activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable
                    .top_panel_bg_gradient));
        }
        //--------------------------------

        mPrevCardView = findViewById(R.id.prev_card_view);
        mAnimatedChest = findViewById(R.id.animated_chest);
        LottieAnimationView mAnimatedPlayVid = findViewById(R.id.animated_play_vid);
        TextView mQuote = findViewById(R.id.quote);
        TextView mAuthor = findViewById(R.id.author);
        mChest_message = findViewById(R.id.chest_message);
        Button mQuote_done = findViewById(R.id.quote_done);

        AdView cAdView = findViewById(R.id.adView4);
        AdsManager adsManager = new AdsManager();
        appFlavor = new AppFlavor(getApplicationContext());
        adsManager.adOps(this, cAdView, mChest_message);

        chestsControl = new ChestsControl(getApplicationContext(), mAnimatedChest,
                mAnimatedPlayVid, mQuote, mAuthor, mPrevCardView, mChest_message, mQuote_done);

        scheduledAlarm = new ScheduledAlarm(getApplicationContext());
        onFirstChestOpenedListener();
        onSecondChestOpenedListener();
        chestStatusChecker();
        Log.i("TEST","TEST");
        Log.i("NOTIFCATION_STATUS"," IS " + QuoteConstants.FROM_NOTIFICATION);
    }

    private void chestStatusChecker(){
        mPrevCardView.animate().alpha(0f).translationYBy(50f).setDuration(10);
        Log.i("TEST2","TEST2");
        appFlavor.appVersionChecker(new AppFlavor.AppVersionInterface() {
            @Override
            public void onAppBeingFree() {
                Log.i("STATUS_CHECKER1.0", "IS AT FREE");
                if (Preferences.getDefaults(QuoteConstants.FIRST_CHEST_KEY
                        , getApplicationContext()) != null){
                    Log.i("STATUS_CHECKER1.1", "IS AT FREE");
                    if (Preferences.getDefaults(QuoteConstants.FIRST_CHEST_KEY
                            , getApplicationContext()).equals(QuoteConstants.VAL_USED)){
                        Log.i("STATUS_CHECKER1.2", "IS AT FREE");
                        chestsControl.clearAllAnimationsFreeChest();
                    }
                }
                if (Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY
                        , getApplicationContext()) != null) {
                    Log.i("STATUS_CHECKER2.1", "IS AT FREE");
                    if (Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY
                            , getApplicationContext()).equals(QuoteConstants.VAL_USED)) {
                        Log.i("STATUS_CHECKER2.2", "IS AT FREE");
                        chestsControl.resetForSecondChestRegularAfterVideoOrPremium();
                    }
                }
            }

            @Override
            public void onAppBeingPremium() {
                Log.i("STATUS_CHECKER", "IS AT PREMIUM");
                if (Preferences.getDefaults(QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER
                        , QuotesAdditionActivity.this) != null) {
                    if (chestsLeft == 1){
                        String finalSentence = getApplicationContext().getResources()
                                .getString(R.string.you_have_opened_all);
                        mChest_message.setText(finalSentence);
                    }else {
                        chestsLeft = 10 - Integer.parseInt(Preferences.getDefaults(QuoteConstants
                                        .SAVED_PREMIUM_CHEST_COUNTER, getApplicationContext()));
                        Log.i("GOLD", "counter at: " + QuoteConstants
                                .PREMIUM_CHESTS_COUNTER + ". int at: " + chestsLeft);
                        String finalSentence = getApplicationContext().getResources().getString
                                (R.string.you_have) + " " + chestsLeft + " "
                                + getApplicationContext().getResources().getString
                                (R.string.chests_left_to_open);
                        mChest_message.setText(finalSentence);
                    }
                }
            }
        });
    }

    //--------------CHESTS--------------//

    private void onFirstChestOpenedListener(){

        //Animated chest
        mAnimatedChest.setOnClickListener(view -> {

            Log.i("GREEN1", "ran");

            chestsControl.animateChest();

            //Save pref for 1st vid and timer service till 24:00
            Preferences.setDefaults(QuoteConstants.FIRST_CHEST_KEY, QuoteConstants.VAL_USED
                    , QuotesAdditionActivity.this);

            scheduledAlarm.scheduleAlarm();
            QuoteConstants.CHEST_USED = true;
        });
    }

    private void onSecondChestOpenedListener(){
        ChestsControl.secondChestAccess = () -> {
            Log.i("GREEN2", "ran");
            //Save pref for all vids and timer service till 24:00
            scheduledAlarm.cancelFirstAlarm();

            Preferences.setDefaults(QuoteConstants.ALL_CHESTS_KEY, QuoteConstants.VAL_USED
                    , QuotesAdditionActivity.this);

            scheduledAlarm.scheduleAlarm();
            QuoteConstants.CHEST_USED = true;
        };
    }
    //----------------------------------//

    private void backToQuotesList(){
        if (QuoteConstants.FROM_NOTIFICATION){
            startActivity(new Intent(QuotesAdditionActivity.this
                    , BaseActivity.class));
        }else{
            finish();
        }
    }

    public void doneWithQuotes(View v){
        backToQuotesList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Log.i("toHome", "ran");
            //finish();
            backToQuotesList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i("toHome", "ran");
            //moveTaskToBack(true);
            backToQuotesList();
        }
        /*
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            //Do nothing for now
        }
        */
        return false;
    }
}
