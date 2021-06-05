package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.WebConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.Products;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.QuotesFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.ResolutionsFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.PreferencesInterface;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.ShareThing;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.tests.FirebaseFCMToken;

public class BaseActivity extends AppCompatActivity implements
        ResolutionsFragment.OnFragmentInteractionListener
        , QuotesFragment.OnFragmentInteractionListener {

    private AdView cAdView;
    private FloatingActionButton fab, mFabQuote;
    private String webUrlHolderString;

    private int navPosition;
    private ImageButton mEmoji_res_sel;
    private ImageButton mEmoji_quote_sel;

    private ShareThing shareThing;
    private ShareButton share;

    private Fragment resolutionFragment;
    private Fragment quotesFragment;

    public FloatingActionButton getFab(){
        return fab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //Tests//
        //-----//

        //Slider tutorial
        goToTutorialSlider();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();

        // Commented by Kirill
        //toolbar.setElevation(10f);

        //Fragment starter
        fragmentStarter();

        //Navigation
        topNavOperations();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                new ResolutionsFragment()).commit();

        //Push notifications initialization//
        FirebaseFCMToken.getFCMToken();
        //---------------------------------//

        //AdMob Processor (mandatory)//
        MobileAds.initialize(this, initializationStatus -> {

        });
        //--------------//

        share = findViewById(R.id.share_linky_base);
        shareThing = new ShareThing(BaseActivity.this);
        shareThing.initialize_fb_share(share,getResources().getString(R.string.start_making));

        fab = findViewById(R.id.fab);
        mFabQuote = findViewById(R.id.fab_quote);
        mFabQuote.hide();

        cAdView = findViewById(R.id.adView);
        AdsManager adsManager = new AdsManager();
        adsManager.adOps(this, cAdView, fab, mFabQuote);

        fab.setOnClickListener(view -> {
            Intent i = new Intent(BaseActivity. this
                    , WishEditActivity. class);
            startActivity(i);
        });

        killAd();
    }

    //Top navigation listener
    private void topNavOperations(){
        mEmoji_res_sel = findViewById(R.id.emoji_res_sel);
        mEmoji_quote_sel = findViewById(R.id.emoji_quote_sel);
        menuBtnStatesSwitcher(R.drawable.emojires,R.drawable.emojiquotesunsel);

        @SuppressLint("NonConstantResourceId") ImageButton.OnClickListener navListener = v -> {
            switch(v.getId()){
                case R.id.emoji_res_sel:
                    menuBtnStatesSwitcher(R.drawable.emojires,R.drawable.emojiquotesunsel);
                    fragmentInitiator(resolutionFragment);
                    navPosition = 0;
                    break;
                case R.id.emoji_quote_sel:
                    menuBtnStatesSwitcher(R.drawable.emojiresunsel,R.drawable.emojiquotes);
                    fragmentInitiator(quotesFragment);
                    navPosition = 1;
                    break;
            }
            animateFab(navPosition);
        };
        mEmoji_res_sel.setOnClickListener(navListener);
        mEmoji_quote_sel.setOnClickListener(navListener);
    }

    private void fragmentStarter(){
        resolutionFragment = new ResolutionsFragment();
        quotesFragment = new QuotesFragment();
    }

    private void fragmentInitiator(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    fragment).commit();
        }
    }

    public void menuBtnStatesSwitcher(int res, int quote){
        mEmoji_res_sel.setImageResource(res);
        mEmoji_quote_sel.setImageResource(quote);
    }

    private void goToTutorialSlider(){
        Preferences.savedPreferencesChecker("tutran", BaseActivity.this
                , new PreferencesInterface() {
            @Override
            public void preferenceIsSaved() {

            }

            @Override
            public void preferenceIsNotSaved() {
                startActivity(new Intent(BaseActivity.this
                        , TutorialActivity.class));
            }
        });
    }

    public void animateFab(int position) {
        switch (position) {
            case 0:
                mFabQuote.hide();
                fab.show();
                break;
            case 1:
                fab.hide();
                mFabQuote.show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_premium){
            buyPremiumPurchase();
        }else if (item.getItemId() == R.id.action_menu){
            try {
                View menuItemView = findViewById(R.id.action_menu);
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.inflate(R.menu.main_more_menu);
                popupMenu.show();
                runPopupMenuItemclick(popupMenu);
            }catch(IllegalStateException e){
                Toast.makeText(getApplicationContext(), "Cannot open menu. Try again later."
                        , Toast.LENGTH_SHORT).show();
            }
        }else if (item.getItemId() == R.id.action_share_app){
            View menuItemView = findViewById(R.id.action_share_app);
            shareThing.shareTo(menuItemView, share, getResources().getString(R.string.start_making)
                ,getResources().getString(R.string.share_app_with),R.menu.share_menu
                    , R.id.share_on_fb, R.id.share_on_other_apps);
        }
        return super.onOptionsItemSelected(item);
    }
    private void buyPremiumPurchase(){
        startActivity(new Intent(BaseActivity.this, StoreActivity.class));
    }

    //VIP FEATURE -> KILL AD
    private void killAd(){
        if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED
                , BaseActivity.this) != null){
            if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED
                    , BaseActivity.this).equals(InAppBillingConstants.SAVED)){
                Products.NO_ADS(cAdView, fab, mFabQuote);
            }
        }
    }

    private void runPopupMenuItemclick(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.rateUs) {
                //Launch Google Play rating
                webUrlHolderString = WebConstants.FEEDBACK_URL
                        + WebConstants.packageName(BaseActivity.this);
                feedBackOperation(webUrlHolderString);
                return true;
            }
            return false;
        });
    }

    public void feedBackOperation(String feedBackUrl){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(feedBackUrl)));
            finish();
        } catch (android.content.ActivityNotFoundException anfe) {
            webUrlHolderString = WebConstants.FEEDBACK_URL_BACKUP + WebConstants
                    .packageName(BaseActivity.this);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrlHolderString)));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        killAd();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}