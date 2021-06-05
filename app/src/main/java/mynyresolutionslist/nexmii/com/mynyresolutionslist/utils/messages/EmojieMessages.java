package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters.GridEmojiAdapter;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.Emojies;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.AppFlavor;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.inapppurchase.Products;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Emoji;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.StoreActivity;

public class EmojieMessages {

    /*
    Emojies.FREE_VERSION_EMOJIES
    Emojies.PREMIUM_VERSION_EMOJIES

    Emojies.REVIVE_EMOJIES
     */

    private Dialog dialog;
    private String emojiAddress;
    private final Context context;
    private final Activity activity;
    private GridView gridView;

    //Empty ctor:
    public EmojieMessages(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    //ctor:
    public EmojieMessages(Context context, Activity activity, ImageView emojiHolder,
                          String[] freeEmojiesArr, String[] vipEmojiesArr){

        this.context = context;
        this.activity = activity;

        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_emojies);

        gridView = dialog.findViewById(R.id.emojiGrid);
        ArrayList<Emoji> emojiesList = new ArrayList<>();

        //VIP FEATURE -> UNLOCK ALL EMOJIES
        //Show emojie
        emojiesListSelection(new EmojiesListInterface() {
            @Override
            public void onFreeEmojies() {
                for (String s : freeEmojiesArr) {
                    emojiesList.add(new Emoji(s));
                }
            }

            @Override
            public void onPremiumEmojies() {
                Products.VIP_EMOJIES(() -> {
                    for (String s : vipEmojiesArr) {
                        emojiesList.add(new Emoji(s));
                    }
                });
            }
        });

        GridEmojiAdapter gridAliveEmojiAdapter = new GridEmojiAdapter(context
                ,R.layout.emoji_grid_item, emojiesList);
        gridView.setAdapter(gridAliveEmojiAdapter);
        //Click emojie
        gridView.setOnItemClickListener((adapterView, view, i, l) ->
                emojiesListSelection(new EmojiesListInterface() {
            @Override
            public void onFreeEmojies() {
                if (emojiHolder != null) {
                    Log.i("SILVER", "CLICKED");
                    if (i > 14) {
                        context.startActivity(new Intent(context, StoreActivity.class));
                    } else {
                        emojiAddress = String.valueOf(Array.get(freeEmojiesArr, i));
                        Glide
                                .with(context)
                                .load(Array.get(freeEmojiesArr, i))
                                .into(emojiHolder);
                        dialog.cancel();
                    }
                }
            }

            @Override
            public void onPremiumEmojies() {
                if(emojiHolder != null) {
                    Log.i("GOLD", "CLICKED");
                    emojiAddress = String.valueOf(Array.get(vipEmojiesArr, i));
                    Glide
                            .with(context)
                            .load(Array.get(vipEmojiesArr, i))
                            .into(emojiHolder);
                    dialog.cancel();
                }
            }
        }));
    }

    //VIP FEATURE -> UNLOCK ALL EMOJIES
    public void emojiesListSelection(EmojiesListInterface emojiesListInterface){
        AppFlavor appFlavor = new AppFlavor(context);
        appFlavor.appVersionChecker(new AppFlavor.AppVersionInterface() {
            @Override
            public void onAppBeingFree() {
                emojiesListInterface.onFreeEmojies();
            }

            @Override
            public void onAppBeingPremium() {
                emojiesListInterface.onPremiumEmojies();
            }
        });
    }

    public void emojiesListCustomDialog(MessagesInterface messagesInterface){
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //TODO:Here is the bug
        //THIS BELOW DOES NOT FIRE UP THE EMOJIE MODAL
        dialog.setOnCancelListener(dialogInterface -> {
            Log.i("purple", "CANCELED");
            messagesInterface.onModalCanceled();
            messagesInterface.getModalData(emojiAddress);
        });
        dialog.show();
    }

    public void deadEmojiesListCustomDialog(BlurView blurView, SelectedEmojieInterface sei){

        dialog = new Dialog(activity);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_emojies);

        gridView = dialog.findViewById(R.id.emojiGrid);
        ArrayList<Emoji> emojiesList = new ArrayList<>();

        //VIP FEATURE -> UNLOCK ALL EMOJIES
        //Show emojie
        emojiesListSelection(new EmojiesListInterface() {
            @Override
            public void onFreeEmojies() {
                for (String s : Emojies.REVIVE_EMOJIES) {
                    emojiesList.add(new Emoji(s));
                }
            }

            @Override
            public void onPremiumEmojies() {
                Products.VIP_EMOJIES(() -> {
                    for (String s : Emojies.REVIVE_EMOJIES) {
                        emojiesList.add(new Emoji(s));
                    }
                });
            }
        });

        GridEmojiAdapter gridAliveEmojiAdapter = new GridEmojiAdapter(context
                ,R.layout.emoji_grid_item, emojiesList);
        gridView.setAdapter(gridAliveEmojiAdapter);

        dialog.show();

        gridView.setOnItemClickListener((adapterView, view, i, l) ->
                emojiesListSelection(new EmojiesListInterface() {
                    @Override
                    public void onFreeEmojies() {
                        //In this case this is always empty
                    }

                    @Override
                    public void onPremiumEmojies() {
                        //Get the emoji address//DONE
                        //fire up modal//DONE
                        //on accept, place resolution in onGoing and delete from killed
                        sei.onSelectedEmojie(emojiesList.get(i).getEmoji());
                    }
                }));

        dialog.setOnCancelListener(dialogInterface -> blurView.setVisibility(View.INVISIBLE));
    }

    public void hide(){
        dialog.hide();
    }

    interface EmojiesListInterface {
        void onFreeEmojies();
        void onPremiumEmojies();
    }

    public interface SelectedEmojieInterface{
        void onSelectedEmojie(String emojiAddress);
    }
}
