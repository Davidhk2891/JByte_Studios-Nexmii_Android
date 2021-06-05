package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

public class GeneralMessageBox {

    private final Dialog dialog;
    private final TextView mGeneralMessageBoxBtnsYes;
    private final TextView mGeneralMessageBoxBtnsNo;
    private final TextView mGeneralMessageBoxTitle;
    private final TextView mGeneralMessageBoxContent;

    public GeneralMessageBox(Activity activity, int titleColor, int yesBtnColor, int noBtnColor){
        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_general_msg_box);

        mGeneralMessageBoxTitle = dialog.findViewById(R.id.general_msg_box_title);
        mGeneralMessageBoxContent = dialog.findViewById(R.id.general_msg_box_content);
        mGeneralMessageBoxBtnsYes = dialog.findViewById(R.id.general_msg_box_btns_yes);
        mGeneralMessageBoxBtnsNo = dialog.findViewById(R.id.general_msg_box_btns_no);

        //Title
        mGeneralMessageBoxTitle.setTextColor(titleColor);
        //YES button
        mGeneralMessageBoxBtnsYes.setTextColor(yesBtnColor);
        //NO Button
        mGeneralMessageBoxBtnsNo.setTextColor(noBtnColor);
    }

    public void callGeneralMessageBox(String titleText, String contentText,
                                      GeneralMsgBoxInterface generalMsgBoxInterface){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mGeneralMessageBoxTitle.setText(titleText);
        //Content
        mGeneralMessageBoxContent.setText(contentText);

        //YES button onClick
        mGeneralMessageBoxBtnsYes.setOnClickListener(view -> {
            generalMsgBoxInterface.onMsgBoxYesButton();
            dialog.hide();
        });

        //NO button onClick
        mGeneralMessageBoxBtnsNo.setOnClickListener(view -> {
            dialog.cancel();
            generalMsgBoxInterface.onMsgBoxNoButton();
        });

        //Cancelable
        dialog.setOnCancelListener(dialogInterface ->
                generalMsgBoxInterface.onMsgBoxNoButton());

        //Show dialog box
        dialog.show();
    }

}
