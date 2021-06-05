package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

/**
 * Created by David on 10/1/2019 for Nexmii.
 */
public class ShareThing {

    private static final String SHARE_APP = "https://play.google.com/store/apps/details?id=mywishlist.nexmii.com.mywishlist";

    private Context context;

    public ShareThing(Context ctx){
        this.context = ctx;
    }

    public void shareTo(View menuItemView, ShareButton shareFb
            , String content, String shareWhatWith, int shareMenuId, int fbShareId, int othersId){
        PopupMenu popupMenu = new PopupMenu(context, menuItemView);
        popupMenu.inflate(shareMenuId);
        popupMenu.show();
        runPopupMenuItemclick(popupMenu, shareFb, content, shareWhatWith, fbShareId, othersId);
        //Resolutions(resolutions page), quotes(quote page), app itself(home page)
    }

    private void runPopupMenuItemclick(PopupMenu popupMenu, final ShareButton share
            , final String content, final String shareWhatWith, final int fbShareId
            , final int othersId){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == fbShareId) {
                    shareToFacebook(share);
                    return true;
                }else if (menuItem.getItemId() == othersId){
                    shareToOthers(content, shareWhatWith);
                    return true;
                }
                return false;
            }
        });
    }

    private void shareToFacebook(ShareButton share){
        share.performClick();
    }

    private void shareToOthers(String content, String shareWhatWith){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (shareWhatWith.equals(context.getResources().getString(R.string.share_app_with))){
            shareIntent.putExtra(Intent.EXTRA_TEXT, content + " - " + SHARE_APP);
        }else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        }
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, shareWhatWith));
    }

    public void initialize_fb_share(ShareButton share, String mContent){
        //Facebook Sharing the resolution-----------------------------------------------//
        //----Sharing the link-------------------------------------------------
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote(mContent)
                .setContentUrl(Uri.parse
                        (ShareThing.SHARE_APP))
                .build();

        share.setShareContent(content);
        //---------------------------------------------------------------------
        //------------------------------------------------------------------------------//
    }

}
