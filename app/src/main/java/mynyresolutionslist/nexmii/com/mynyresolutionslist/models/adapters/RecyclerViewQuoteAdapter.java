package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Quote;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.QuoteDetailActivity;

/**
 * Created by David on 9/14/2019 for Nexmii.
 */
public class RecyclerViewQuoteAdapter extends RecyclerView.Adapter<RecyclerViewQuoteAdapter.MyViewHolder> {

    private Context mContext;
    private List<Quote> mData;

    public RecyclerViewQuoteAdapter(Context mContext, List<Quote> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_quote, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String message = mData.get(position).getQuote();
        String trimmedMsg = message.replace(message.substring(message.lastIndexOf("-"))
                , "");
        String author = message.substring(message.lastIndexOf("-"));
        String liked = mData.get(position).getLiked();

        if (trimmedMsg.length() > 37){
            String sampleTrimmedMsg = trimmedMsg.substring(0, 37) + "...";
            Log.i("ORANGE", sampleTrimmedMsg + " length " + sampleTrimmedMsg.length());
            holder.mQuote.setText(sampleTrimmedMsg);
        }else{
            holder.mQuote.setText(trimmedMsg);
        }

        holder.mCategory.setText(author);

        if (liked.equals("true"))
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.quote_bg_color_red));
        else
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.quote_bg_color));

        holder.mCardView.setOnClickListener(view -> {
            //go to the quotes detailed page
            Intent detQuoteIntent = new Intent(mContext, QuoteDetailActivity.class);
            detQuoteIntent.putExtra("quote_text", trimmedMsg);
            detQuoteIntent.putExtra("quote_category", author);
            detQuoteIntent.putExtra("quote_id", mData.get(position).getItemId());
            detQuoteIntent.putExtra("quote_liked", liked);
            mContext.startActivity(detQuoteIntent);
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mQuote;
        TextView mCategory;
        CardView mCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuote = itemView.findViewById(R.id.quote_card_text);
            mCategory = itemView.findViewById(R.id.quote_card_category);
            mCardView = itemView.findViewById(R.id.cardView);
        }
    }
}
