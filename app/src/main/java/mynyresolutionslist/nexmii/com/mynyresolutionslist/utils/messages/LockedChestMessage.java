package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

public class LockedChestMessage {

    private final Context context;
    private final Dialog dialog;
    private final ImageView mLockedChestDialogExit;
    private final ImageView mLockedChestDialogBecomeVipBtn;
    private final TextView mLockedChestDialogTextOne;
    private final TextView getmLockedChestDialogTextPremiumMessage;

    public LockedChestMessage(Context context){
        this.context = context;
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_locked_chest);

        //instantiate views
        //FREE
        mLockedChestDialogExit = dialog.findViewById(R.id.locked_chest_dialog_exit);
        mLockedChestDialogBecomeVipBtn = dialog.findViewById
                (R.id.locked_chest_dialog_become_vip_btn);

        //PREMIUM
        mLockedChestDialogTextOne = dialog.findViewById(R.id.locked_chest_dialog_text_one);
        getmLockedChestDialogTextPremiumMessage = dialog
                .findViewById(R.id.locked_chest_dialog_text_premium_msg);

    }

    public void initFreeChestMsgDialog(LockedChestInterface lockedChestInterface){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getmLockedChestDialogTextPremiumMessage.setVisibility(View.GONE);
        dialog.setOnCancelListener(dialogInterface -> lockedChestInterface.onModalCanceled());
        mLockedChestDialogExit.setOnClickListener(v ->{
            dialog.cancel();
            lockedChestInterface.onModalCanceled();
        });
        mLockedChestDialogBecomeVipBtn.setOnClickListener(v ->
                lockedChestInterface.onModalMainBtnClicked());
        dialog.show();
    }

    public void initPremiumChestMsgDialog(LockedChestInterface lockedChestInterface){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mLockedChestDialogBecomeVipBtn.setVisibility(View.GONE);
        mLockedChestDialogTextOne.setText(context.getResources().getString(R.string.
                you_have_already_opened_your));
        getmLockedChestDialogTextPremiumMessage.setVisibility(View.VISIBLE);
        dialog.setOnCancelListener(dialogInterface -> lockedChestInterface.onModalCanceled());
        mLockedChestDialogExit.setOnClickListener(v ->{
            dialog.cancel();
            lockedChestInterface.onModalCanceled();
        });
        dialog.show();
    }

    public interface LockedChestInterface{
        void onModalCanceled();
        void onModalMainBtnClicked();
    }

}
