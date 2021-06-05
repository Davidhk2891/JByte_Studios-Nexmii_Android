package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.ResolutionConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.BaseActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.EmojieMessages;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.GeneralMessageBox;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.GeneralMsgBoxInterface;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.ResolutionStatusMessage;

public class RevivalResolution {

    private Context context;
    private Activity activity;

    private BlurView bv;

    private int red;
    private int green;

    private EmojieMessages emojieMessages;


    public RevivalResolution(Context ctx, Activity activity, BlurView bv){
        this.context = ctx;
        this.activity = activity;
        this.bv = bv;
        red = context.getResources().getColor(R.color.nexmii_red);
        green = context.getResources().getColor(R.color.nexmii_green);
        emojieMessages = new EmojieMessages(context,this.activity);
    }

    public void reviveResolution(ImageView reviveButton, Button refButton, String resTitle
                , String resContent, int killedId){
        reviveButton.setImageResource(R.drawable.revive);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 170);
        relativeLayout.addRule(RelativeLayout.ALIGN_TOP, refButton.getId());
        relativeLayout.setMargins(40,-30,40,0);
        reviveButton.setLayoutParams(relativeLayout);
        reviveButton.setScaleType(ImageButton.ScaleType.FIT_XY);

        reviveButton.setOnClickListener(v -> {

            bv.setVisibility(View.VISIBLE);
            //TODO:FIRE UP GRID DIALOG
            emojieMessages.deadEmojiesListCustomDialog(bv, (stringAddress) ->
                    fireModal(resTitle, resContent, killedId, stringAddress));
        });
    }

    private void fireModal(String resTitle, String resContent, int killedId, String stringAddress){
        GeneralMessageBox generalMessageBox = new GeneralMessageBox(activity
                , green, green, red);
        generalMessageBox.callGeneralMessageBox(
                context.getResources().getString(R.string.revive_res_modal_title)
                , context.getResources().getString(R.string.revive_res_modal_message)
                , new GeneralMsgBoxInterface() {
                    @Override
                    public void onMsgBoxYesButton() {
                        emojieMessages.hide();
                        fireResStatusMsg(resTitle, resContent, stringAddress, killedId);
                    }

                    @Override
                    public void onMsgBoxNoButton() {

                    }
                });
    }

    //This dialog fires up after selecting the desired dead emoji
    //This is not the modal. It is the full screen message
    private void fireResStatusMsg(String resTitle, String resContent, String deadEmojiAddress
            , int killedId){
        String title = context.getResources().getString(R.string.from_now_on_you_only);
        String subTitle = context.getResources().getString(R.string.res_status_good_luck);
        ResolutionStatusMessage resolutionStatusMessage = new ResolutionStatusMessage(
                context,activity,context.getResources().getColor(R.color.nexmii_red));
        resolutionStatusMessage.callResolutionStateMessage(bv,deadEmojiAddress
                , title, subTitle, () ->
                        saveRevivedToResDB(resTitle,resContent,deadEmojiAddress,killedId));
    }

    private void saveRevivedToResDB(String resTitle, String resContent, String resEmojiAdrs
            , int killedResId){
        NexmiiDatabaseHandler dbh = new NexmiiDatabaseHandler(context);
        Resolution res = new Resolution();
        res.setTitle(resTitle);
        if(resContent.contains("\"")) {
            resContent = resContent.replace("\"", "");
        }
        res.setContent(resContent);
        res.setEmojiAddress(resEmojiAdrs);
        res.setEmojiRevived(ResolutionConstants.RES_REVIVED_YES);
        dbh.addWishes(res);
        dbh.deleteKilled(killedResId);
        Intent intent = new Intent(context, BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
