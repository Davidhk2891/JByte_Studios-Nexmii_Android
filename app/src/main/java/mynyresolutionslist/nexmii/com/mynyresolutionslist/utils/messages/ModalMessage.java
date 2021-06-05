package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by David on 9/10/2019 for Nexmii.
 */
public class ModalMessage {

    public static void standardModalMessageNoTitle(Context context, String msg, String positiveBtn
            ,ModalButtonClick modalButtonClick){
        AlertDialog.Builder elDialogo = new AlertDialog.Builder(context
                , AlertDialog.THEME_HOLO_DARK);
        elDialogo.setMessage(msg);
        elDialogo.setPositiveButton(positiveBtn, (dialogInterface, i) ->
                modalButtonClick.onModalPositiveButtonClicked());
        AlertDialog alertD = elDialogo.create();
        alertD.setCancelable(false);
        alertD.show();
    }

    public interface ModalButtonClick{
        void onModalPositiveButtonClicked();
    }

}
