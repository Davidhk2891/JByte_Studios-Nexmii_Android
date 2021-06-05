package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.QuoteConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.CustomTimer;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.ShareThing;

public class QuoteDetailActivity extends AppCompatActivity {

    private TextView mDetailed_quote_category, mDetailed_quote_text;

    private ShareButton share;
    private ShareThing shareThing;
    private CallbackManager callbackManager;
    private String quoteText;
    private LottieAnimationView mDetailedQuoteHeartAnimation;
    private FrameLayout mDetailedQuoteHeartContainer;
    private int quoteID;
    private boolean quoteLiked;
    private String likedOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);
        mDetailed_quote_category = findViewById(R.id.detailed_quote_category);
        mDetailed_quote_text = findViewById(R.id.detailed_quote_text);

        //Navigate back to parent activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable
                    .top_panel_bg_gradient));
        }
        //--------------------------------

        AdView cAdView5 = findViewById(R.id.adView5);
        AdsManager adsManager = new AdsManager();
        adsManager.adOps(this, cAdView5);

        share = findViewById(R.id.share_linky_quote);
        shareThing = new ShareThing(QuoteDetailActivity.this);
        shareThing.initialize_fb_share(share,quoteText);
        callbackManager = CallbackManager.Factory.create();
        mDetailedQuoteHeartAnimation = findViewById(R.id.detailed_quote_heart_animation);
        mDetailedQuoteHeartContainer = findViewById(R.id.detailed_quote_heart_container);
        CardView mCardView = findViewById(R.id.cardView);

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        ViewCompat.setElevation(findViewById(R.id.detailed_quote_heart_container), mCardView.getElevation() * 2);

        displaySelectedQuote();
        likeUnlikeQuote();
    }

    private void likeUnlikeQuote(){
        final NexmiiDatabaseHandler ndbh = new NexmiiDatabaseHandler(getApplicationContext());
        mDetailedQuoteHeartContainer.setOnClickListener(v ->{
            //TODO: set up system with db to add liked quote
            if (!quoteLiked) {
                mDetailedQuoteHeartAnimation.setSpeed(2f);
                mDetailedQuoteHeartAnimation.playAnimation();
                ndbh.likeDislikeQuote(quoteID, likedOrNot);
                CustomTimer.CUSTOM_TIMER(1100, this::finish);
                QuoteConstants.QUOTE_LIKED_UNLIKED_USED = true;
            }else{
                mDetailedQuoteHeartAnimation.setProgress(0);
                ndbh.likeDislikeQuote(quoteID, likedOrNot);
                CustomTimer.CUSTOM_TIMER(500, this::finish);
                QuoteConstants.QUOTE_LIKED_UNLIKED_USED = true;
            }
        });
    }

    public void displaySelectedQuote(){
        Bundle quoteDetsIntent = getIntent().getExtras();
        if(quoteDetsIntent != null){
            quoteText = quoteDetsIntent.getString("quote_text");
            String quoteCategory = quoteDetsIntent.getString("quote_category");
            likedOrNot = quoteDetsIntent.getString("quote_liked");
            quoteID = quoteDetsIntent.getInt("quote_id");
            if (quoteText != null && quoteCategory != null && likedOrNot != null){
                String quotedText = " \" " + quoteText + " \" ";
                mDetailed_quote_text.setText(quotedText);
                mDetailed_quote_category.setText(quoteCategory);
                quoteLiked = likedOrNot.equals("true");
                if (quoteLiked){
                    mDetailedQuoteHeartAnimation.setProgress(1);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quote_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if(item.getItemId() == R.id.action_share_quote){
            View menuItemView = findViewById(R.id.action_share_quote);
            shareThing.shareTo(menuItemView, share, quoteText, getResources()
                            .getString(R.string.share_reso_with), R.menu.share_menu
                    , R.id.share_on_fb, R.id.share_on_other_apps);
        }else if(item.getItemId() == R.id.action_copy_quote){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("quote", quoteText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(QuoteDetailActivity.this, getResources().getString
                            (R.string.copied), Toast.LENGTH_SHORT).show();
        }
//        else if(item.getItemId() == R.id.action_alarm_quote){
//            startActivity(new Intent(QuoteDetailActivity.this
//                    , NotificationSettingsActivity.class));
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
