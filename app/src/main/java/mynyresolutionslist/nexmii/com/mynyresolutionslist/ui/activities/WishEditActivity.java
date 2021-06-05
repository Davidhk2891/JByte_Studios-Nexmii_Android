package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdView;

import eightbitlab.com.blurview.BlurView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.ads.AdsManager;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.Emojies;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.ResolutionConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.ResolutionsFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.animations.BlurEffect;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.EmojieMessages;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.MessagesInterface;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.GlidePlaceImage;

public class WishEditActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private ImageView mChoosen_emoji;
    private String emoji_address;
    private Button saveButton;
    private LinearLayout mChoose_emoji_linear;
    private BlurView mBlurView;
    private EmojieMessages emojieMessages;

    private NexmiiDatabaseHandler dbh;
    private AlertDialog.Builder inDialog;
    private int currentID_If_applicable;
    private boolean editing_existing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Navigate back to parent activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable
                    .top_panel_bg_gradient));
        }
        //--------------------------------

        dbh = new NexmiiDatabaseHandler(WishEditActivity.this);
        title = findViewById(R.id.titleEditText);
        content = findViewById(R.id.wishEditText);
        saveButton = findViewById(R.id.saveBtn);
        mChoose_emoji_linear = findViewById(R.id.choose_emoji_linear);
        mChoosen_emoji = findViewById(R.id.choosen_emoji);
        emojieMessages = new EmojieMessages(WishEditActivity.this
                , WishEditActivity.this, mChoosen_emoji, Emojies.FREE_VERSION_EMOJIES
                , Emojies.PREMIUM_VERSION_EMOJIES);
        mBlurView = findViewById(R.id.blurView);
        BlurEffect.blurScreen(mBlurView, getApplicationContext(), this);

        //Default emoji
        emoji_address = Emojies.GET_DEFAULT_EMOJI();
        GlidePlaceImage.PLACE(WishEditActivity.this,emoji_address,mChoosen_emoji);

        AdView cAdView = findViewById(R.id.adView3);
        AdsManager adsManager = new AdsManager();
        adsManager.adOps(this, cAdView, saveButton);

        editWishChecker();

        title.requestFocus();

        saveButton.setOnClickListener(v -> {
            String editTitle = title.getText().toString();
            String editContent = content.getText().toString();
            if (editTitle.isEmpty() || editContent.isEmpty()) {
                inDialog = new AlertDialog.Builder(WishEditActivity.this);
                inDialog.setTitle(getResources().getString(R.string.dTitle));
                inDialog.setMessage(getResources().getString(R.string.dMessage));
                inDialog.setNegativeButton(getResources().getString(R.string.cancel),
                        (dialog, which) -> {
                            //cancel dialog Box
                            inDialog.setCancelable(true);
                        });
                AlertDialog alertD = inDialog.create();
                alertD.show();
            } else if(editing_existing){
                Log.i("edited", "ran");
                killCurrentIfEdited();
                saveToDB();
            } else {
                Log.i("1st time", "ran");
                saveToDB();
            }
        });
        chooseEmojiOps();
    }

    private void chooseEmojiOps(){
        mChoose_emoji_linear.setOnClickListener(v -> {
            //Blurr effect
            mBlurView.setVisibility(View.VISIBLE);

            btnController(saveButton,false,View.INVISIBLE);

            //init custom dialog window
            emojieMessages.emojiesListCustomDialog(new MessagesInterface() {
                @Override
                public void onModalCanceled() {
                    btnController(saveButton,true,View.VISIBLE);
                    mBlurView.setVisibility(View.GONE);
                }

                @Override
                public void getModalData(String emojiAddress) {
                    emoji_address = emojiAddress;
                }
            });
        });
    }

    private void btnController(Button btn, boolean enabled, int visible){
        btn.setVisibility(visible);
        btn.setClickable(enabled);
        btn.setEnabled(enabled);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(WishEditActivity. this, BaseActivity.class);
            i.putExtra("block", "blockKey");
            startActivity(i);
        }
        return false;
    }

    private void saveToDB(){
        Resolution wish = new Resolution();
        wish.setTitle(title.getText().toString().trim());
        wish.setContent(content.getText().toString().trim());
        Log.i("SAVED_EMOJI", "IS: " + emoji_address);
        wish.setEmojiAddress(emoji_address);
        Log.i("SAVED_EMOJI", "IS: " + emoji_address);
        wish.setEmojiRevived(ResolutionConstants.RES_REVIVED_NO);

        dbh.addWishes(wish);
        dbh.close();

        title.setText("");
        content.setText("");

        afterSave();
    }

    private void afterSave(){
        //go to details Activity upon adding new entry and clearing out the textEdit(s)
        Intent i = new Intent(WishEditActivity.this, BaseActivity.class);
        i.putExtra("block","blockKey");
        i.putExtra("res_status", "ongoing");
        if (emoji_address == null || emoji_address.equals(""))
            emoji_address = "http://jbytestudios.com/wp-content/uploads/2020/04/emojinexmiicool.png";
        i.putExtra("res_status_ongoing_img", emoji_address);
        i.putExtra("res_status_ongoing_title", getResources()
                .getString(R.string.res_status_good_luck));
        ResolutionsFragment.FROM_RES_EDITING = true;
        startActivity(i);
    }

    private void editWishChecker(){
        Bundle editionChecker = getIntent().getExtras();
        if (editionChecker != null) {
            String keyToOpen = editionChecker.getString("title_edit");
            if (keyToOpen != null && !keyToOpen.equals("")){
                //good to proceed
                title.setText(editionChecker.getString("title_edit"));
                content.setText(editionChecker.getString("content_edit"));
                GlidePlaceImage.PLACE(WishEditActivity.this
                        ,editionChecker.getString("emoji_edit"),mChoosen_emoji);

                currentID_If_applicable = editionChecker.getInt("wish_current_id");
                editing_existing = true;
            }
        }
    }
    private void killCurrentIfEdited(){
        NexmiiDatabaseHandler dbhDeletion = new NexmiiDatabaseHandler
                (getApplicationContext());
        dbhDeletion.deleteWish(currentID_If_applicable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
