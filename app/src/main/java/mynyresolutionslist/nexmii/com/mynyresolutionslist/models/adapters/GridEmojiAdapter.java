package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Emoji;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.GlidePlaceImage;

public class GridEmojiAdapter extends ArrayAdapter<Emoji> {

    private final Context context;
    private final int layoutResource;
    private final ArrayList<Emoji> mData;

    public GridEmojiAdapter(Context context, int resource, ArrayList<Emoji> data) {
        super(context, resource);

        this.context = context;
        this.layoutResource = resource;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public Emoji getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getPosition(@Nullable Emoji item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View gridItem = convertView;
        final ViewHolder holder;

        if ((gridItem == null) || (gridItem.getTag() == null)){

            LayoutInflater inflator = LayoutInflater.from(context);
            gridItem = inflator.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.emojiImage = gridItem.findViewById(R.id.emoji_thumbnail);

        } else {

            holder = (ViewHolder) gridItem.getTag();

        }

        holder.emoji = getItem(position);

        if (holder.emoji != null){
            GlidePlaceImage.PLACE(context,holder.emoji.getEmoji(),holder.emojiImage);
        }

        return gridItem;
    }

    public static class ViewHolder{
        Emoji emoji;
        private ImageView emojiImage;
    }
}
