package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.LocalMaterial;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.ResolutionConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.AppFlavor;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.Products;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.ResolutionsFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.animations.BlurEffect;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.GeneralMessageBox;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.GeneralMsgBoxInterface;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.ShareThing;

public class WishDetailActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private int id;
    private TextView content;
    private ShareButton share;
    private LocalMaterial localMaterial = new LocalMaterial();
    private NexmiiDatabaseHandler db;

    private String totalDate;
    private String totalTitle;
    private String totalContent;
    private String emojiAddress;
    private String emojiRevived;
    private String total_content_for_edit;
    private ShareThing shareThing;

    private int killColor;
    private int achColor;

    private ImageView mRes_detail_emoji;
    private ImageView mBig_emoji_image;
    private TextView cantReviveMsg;
    private TextView title, date;
    private ImageButton mReviveRes;
    private Button buttonOne;
    private Button buttonTwo;
    private String resStatus;
    private BlurView mBlurViewResDetail;

    private MenuItem edit_item;
    private MenuItem share_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);

        //Navigate back to parent activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable
                    .top_panel_bg_gradient));
        }
        //--------------------------------

        mRes_detail_emoji = findViewById(R.id.res_detail_emoji);
        mBig_emoji_image = findViewById(R.id.big_emoji_image);
        title = findViewById(R.id.textTitle);
        date = findViewById(R.id.record);
        content = findViewById(R.id.textWish);
        buttonOne = findViewById(R.id.resCompleted);
        buttonTwo = findViewById(R.id.deleteButton);
        mReviveRes = findViewById(R.id.revive_res);
        share = findViewById(R.id.share_linky);
        cantReviveMsg = findViewById(R.id.cantReviveMsg);
        mBlurViewResDetail = findViewById(R.id.blur_view_res_detail);
        shareThing = new ShareThing(WishDetailActivity.this);
        killColor = getResources().getColor(R.color.nexmii_red);
        achColor = getResources().getColor(R.color.nexmii_green);
        db = new NexmiiDatabaseHandler(getApplicationContext());

        AdView cAdView2 = findViewById(R.id.adView2);
        AdsManager adsManager = new AdsManager();
        adsManager.adOps(this, cAdView2, buttonTwo);

        BlurEffect.blurScreen(mBlurViewResDetail,getApplicationContext(),this);

        resolutionDetailSelector();
    }

    private void resolutionDetailSelector(){

        Bundle myExtras = getIntent().getExtras();

        if (myExtras != null){

            ImageView mAch_check_mark = findViewById(R.id.ach_check_mark);
            totalDate = "created: " + myExtras.getString("date");
            totalTitle = myExtras.getString("title");
            totalContent = " \" " + myExtras.getString("content") + " \" ";
            total_content_for_edit = myExtras.getString("content");
            emojiAddress = myExtras.getString("emojiAddress");
            emojiRevived = myExtras.getString("emojiRevived");
            if (emojiAddress == null || emojiAddress.equals(""))
                emojiAddress = "http://jbytestudios.com/wp-content/uploads/2020/04/emojinexmiicool.png";
            Log.i("blue3.2", emojiAddress);
            Log.i("purple3", emojiRevived);
            id = myExtras.getInt("id");
            resStatus = myExtras.getString("resolution_status");

            title.setText(totalTitle);
            date.setText(totalDate);
            content.setText(totalContent);

            if(resStatus != null) {
                switch (resStatus) {
                    case "resolution_ongoing":

                        Glide
                                .with(WishDetailActivity.this)
                                .load(emojiAddress)
                                .into(mRes_detail_emoji);
                        Glide
                                .with(WishDetailActivity.this)
                                .load(emojiAddress)
                                .into(mBig_emoji_image);

                        break;

                    case "resolution_achieved":

                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle("Achievement");

                        mAch_check_mark.setVisibility(View.VISIBLE);

                        Glide
                                .with(WishDetailActivity.this)
                                .load(emojiAddress)
                                .into(mRes_detail_emoji);
                        Glide
                                .with(WishDetailActivity.this)
                                .load(getResources().getDrawable(R.drawable.achievement))
                                .into(mBig_emoji_image);

                        buttonOne.setBackground(getResources().getDrawable
                                (R.drawable.btn_bg_yellow));
                        buttonOne.setTextColor(getResources().getColor
                                (R.color.nexmii_font_blue));
                        buttonOne.setText(getResources().getString(R.string.addANrewRes));
                        buttonOne.setTextSize(22f);
                        buttonTwo.setVisibility(View.INVISIBLE);
                        buttonTwo.setEnabled(false);
                        buttonTwo.setClickable(false);

                        break;

                    case "resolution_killed":

                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle("Killed");

                        killedResRevivedStatusOps();

                        Glide
                                .with(WishDetailActivity.this)
                                .load(emojiAddress)
                                .into(mRes_detail_emoji);
                        Glide
                                .with(WishDetailActivity.this)
                                .load(getResources().getDrawable(R.drawable.killed))
                                .into(mBig_emoji_image);

                        break;
                }
            }

            //BUTTON REVIVE
            buttonReviveOps();

            //BUTTON OPS
            btnsOps();

            shareThing.initialize_fb_share(share,total_content_for_edit);
            callbackManager = CallbackManager.Factory.create();
        }
    }

    private void setCantReviveMsg(){
        String messagePartOne = getResources().getString(R.string.you_already);
        String messagePartTwo = getResources().getString(R.string.killed_twice);
        String fullMsg = messagePartOne.replace(messagePartTwo, "<font color='#ff5364'>"
                +messagePartTwo+"</font>");
        cantReviveMsg.setText(Html.fromHtml(fullMsg));
    }

    private void killedResRevivedStatusOps(){
        Log.i("purple2", "is it revived? -> " + emojiRevived);
        if (emojiRevived.equals("yes")){
            mReviveRes.setVisibility(View.INVISIBLE);
            mReviveRes.setClickable(false);
            buttonOne.setVisibility(View.VISIBLE);
            buttonOne.setClickable(true);
            buttonOne.setBackground(getResources().getDrawable
                    (R.drawable.btn_bg_yellow));
            buttonOne.setTextColor(getResources().getColor
                    (R.color.nexmii_font_blue));
            buttonOne.setText(getResources().getString(R.string.addANrewRes));
            buttonOne.setTextSize(22f);
            buttonTwo.setVisibility(View.INVISIBLE);
            buttonTwo.setClickable(false);
            setCantReviveMsg();
        }else{
            mReviveRes.setVisibility(View.VISIBLE);
            buttonOne.setVisibility(View.INVISIBLE);
            buttonOne.setClickable(false);
            buttonTwo.setVisibility(View.INVISIBLE);
            buttonTwo.setClickable(false);
        }
    }

    private void btnsOps(){
        //BUTTON ONE
        buttonOne.setOnClickListener(v -> buttonOneAction());
        //BUTTON TWO
        if (emojiRevived.equals("yes"))
            buttonTwo.setText(getResources().getString(R.string.killForever));

        buttonTwo.setOnClickListener(v -> buttonTwoAction());
    }

    private void buttonReviveOps(){
        AppFlavor appFlavor = new AppFlavor(getApplicationContext());
        appFlavor.appVersionChecker(new AppFlavor.AppVersionInterface() {
            @Override
            public void onAppBeingFree() {
                mReviveRes.setOnClickListener(v -> startActivity
                        (new Intent(WishDetailActivity.this,
                                StoreActivity.class)));
            }

            @Override
            public void onAppBeingPremium() {
                Products.REVIVE_RESOLUTIONS(getApplicationContext(),WishDetailActivity.this
                        ,mBlurViewResDetail, mReviveRes, buttonTwo, totalTitle, totalContent, id);
            }
        });
    }

    private void editResolution(){
        Intent edit_intent = new Intent(WishDetailActivity.this
                , WishEditActivity.class);
        edit_intent.putExtra("title_edit", totalTitle);
        edit_intent.putExtra("content_edit", total_content_for_edit);
        edit_intent.putExtra("wish_current_id", id);
        edit_intent.putExtra("emoji_edit", emojiAddress);
        startActivity(edit_intent);
    }

    private void toolBarItemSelector(boolean editActive, boolean shareActive){
        edit_item.setVisible(editActive);
        share_item.setVisible(shareActive);
    }

    private void resState(){
        switch(resStatus){
            case "resolution_ongoing":
                toolBarItemSelector(true, true);
                break;
            case "resolution_achieved":
                toolBarItemSelector(false, true);
                break;
            case "resolution_killed":
                toolBarItemSelector(false, false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reso_detail_menu, menu);
        edit_item = menu.findItem(R.id.action_edit);
        share_item = menu.findItem(R.id.action_share);
        resState();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if(item.getItemId() == R.id.action_edit){
            //edit resolution
            editResolution();
        }else if(item.getItemId() == R.id.action_share){
            //share resolution
            View menuItemView = findViewById(R.id.action_share);
            shareThing.shareTo(menuItemView, share, content.getText().toString(), getResources()
                    .getString(R.string.share_reso_with), R.menu.share_menu
                    , R.id.share_on_fb, R.id.share_on_other_apps);
        }
//        else if (item.getItemId() == R.id.action_alarm_reso){
//            startActivity(new Intent(WishDetailActivity.this
//                    , NotificationSettingsActivity.class));
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent backIntent = new Intent(WishDetailActivity. this
//                    , BaseActivity. class);
//            backIntent.putExtra("block", "blockKey");
//            startActivity(backIntent);
            finish();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void buttonOneAction(){
        switch(resStatus) {
            case "resolution_ongoing":
                //ACHIEVED
                //ACHIEVE RESOLUTION -> GOES TO ACHIEVED LIST (ACHIEVED TABLE)
                GeneralMessageBox gmbAchRes = new GeneralMessageBox(WishDetailActivity.this
                        , achColor, achColor, killColor);

                gmbAchRes.callGeneralMessageBox(getResources()
                                .getString(R.string.resolutionAchieved),
                        getResources().getString(R.string.didYouFinish),
                        new GeneralMsgBoxInterface() {
                            @Override
                            public void onMsgBoxYesButton() {
                                //ADD WISH TO ACH TABLE BEFORE DELETING//
                                Resolution resAchieved = new Resolution();
                                resAchieved.setTitle(totalTitle.trim());
                                resAchieved.setContent(total_content_for_edit.trim());
                                resAchieved.setRecordDate(totalDate.trim());
                                resAchieved.setEmojiAddress(emojiAddress);
                                resAchieved.setEmojiRevived(ResolutionConstants.RES_REVIVED_NO);
                                resAchieved.setItemId(id);
                                db.addAchieved(resAchieved);
                                db.deleteWish(id);
                                //-------------------------------------//
                                Intent intent = new Intent(WishDetailActivity.this
                                        , BaseActivity.class);
                                intent.putExtra("res_status", "achieved");
                                ResolutionsFragment.FROM_RES_EDITING = true;
                                startActivity(intent);
                            }

                            @Override
                            public void onMsgBoxNoButton() {

                            }
                        });
            break;

            case "resolution_achieved":
                //ADD A NEW RESOLUTION
                startActivity(new Intent(WishDetailActivity.this
                        , WishEditActivity.class));
                break;

            case "resolution_killed":
                //REVIVE (2 ACTIONS)
                Log.i("orange55", "clicked");
                if (emojiRevived.equals("yes"))
                    startActivity(new Intent(this, WishEditActivity.class));
                break;
        }
    }

    private void buttonTwoAction(){
        //KILL RESOLUTION -> GOES TO THE CEMENTERY (KILLED TABLE)
        GeneralMessageBox gmbKillRes = new GeneralMessageBox(WishDetailActivity.this
                , killColor, killColor, achColor);

        Random mrRandom = new Random();
        int motiRandom = mrRandom.nextInt(localMaterial.getFreeMotivationQuotes().length);
        int msgRandom = mrRandom.nextInt(localMaterial.getWaysToKillAResolution().length);
        String motiQuote = localMaterial.getFreeMotivationQuotes()[motiRandom];
        String killMsg = localMaterial.getWaysToKillAResolution()[msgRandom];
        gmbKillRes.callGeneralMessageBox(getResources()
                        .getString(R.string.dialog_title),
                motiQuote, new GeneralMsgBoxInterface() {
                    @Override
                    public void onMsgBoxYesButton() {
                        //ADD WISH TO KILL TABLE BEFORE DELETING//
                        Resolution resKilled = new Resolution();
                        resKilled.setTitle(totalTitle.trim());
                        resKilled.setContent(total_content_for_edit.trim());
                        resKilled.setRecordDate(totalDate.trim());
                        resKilled.setEmojiAddress(emojiAddress);

                        if (emojiRevived.equals("yes")) {
                            resKilled.setEmojiRevived(ResolutionConstants.RES_REVIVED_YES);
                        }else{
                            resKilled.setEmojiRevived(ResolutionConstants.RES_REVIVED_NO);
                        }

                        resKilled.setItemId(id);
                        db.addKilled(resKilled);
                        db.deleteWish(id);
                        //-------------------------------------//
                        Intent intent = new Intent(WishDetailActivity.this
                                ,BaseActivity.class);
                        intent.putExtra("res_status","killed");
                        intent.putExtra("res_status_kill_title", killMsg);
                        ResolutionsFragment.FROM_RES_EDITING = true;
                        startActivity(intent);
                    }

                    @Override
                    public void onMsgBoxNoButton() {

                    }
                });
    }
}
