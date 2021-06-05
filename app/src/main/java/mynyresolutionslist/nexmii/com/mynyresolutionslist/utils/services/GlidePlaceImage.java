package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

public class GlidePlaceImage {

    public static void PLACE(Context context, String url, ImageView imageView){
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.ic_more_horiz_yellow_24dp)
                .into(imageView);
    }

    public static void PLACE(Context context, Drawable drawable, ImageView imageView){
        Glide
                .with(context)
                .load(drawable)
                .placeholder(R.drawable.ic_more_horiz_yellow_24dp)
                .into(imageView);
    }

    static void PRELOAD(Context context, String[] urls){
        for(String url : urls){
            Glide
                    .with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .preload();
        }
    }
}
