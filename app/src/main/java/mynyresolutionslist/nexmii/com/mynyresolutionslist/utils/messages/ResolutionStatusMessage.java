package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.animations.BlurEffect;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.CustomTimer;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.GlidePlaceImage;

public class ResolutionStatusMessage {

    private Dialog dialog;
    private TextView mResStatTitle;
    private TextView mResStatContent;
    private ImageView mResStatImage;
    private Context context;
    private Activity activity;

    public ResolutionStatusMessage(Context ctx, Activity activity, int titleColor){
        this.context = ctx;
        this.activity = activity;
        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_res_status_msg);

        mResStatImage = dialog.findViewById(R.id.res_stat_image);
        mResStatTitle = dialog.findViewById(R.id.res_stat_title);
        mResStatContent = dialog.findViewById(R.id.res_stat_content);

        mResStatTitle.setTextColor(titleColor);
    }

    public void callResolutionStateMessage(BlurView bv, Drawable drawable, String title
            , String content){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        BlurEffect.blurScreen(bv, context, activity);
        bv.setVisibility(View.VISIBLE);

        GlidePlaceImage.PLACE(context,drawable,mResStatImage);
        mResStatTitle.setText(title);
        mResStatContent.setText(content);
        dialog.show();
        CustomTimer.CUSTOM_TIMER(2500, () -> {
            dialog.cancel();
            bv.setVisibility(View.GONE);
        });
    }

    public void callResolutionStateMessage(BlurView bv, String imageUrl, String title
            , String content, StatusMsgInterface statusMsgInterface){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        BlurEffect.blurScreen(bv, context, activity);
        bv.setVisibility(View.VISIBLE);

        GlidePlaceImage.PLACE(context,imageUrl,mResStatImage);
        mResStatTitle.setText(title);
        mResStatContent.setText(content);
        dialog.show();
        CustomTimer.CUSTOM_TIMER(2500, () -> {
            dialog.cancel();
            bv.setVisibility(View.GONE);
            if (statusMsgInterface != null)
                statusMsgInterface.onStatusMsgFinished();
        });
    }

    public interface StatusMsgInterface{
        void onStatusMsgFinished();
    }

}
