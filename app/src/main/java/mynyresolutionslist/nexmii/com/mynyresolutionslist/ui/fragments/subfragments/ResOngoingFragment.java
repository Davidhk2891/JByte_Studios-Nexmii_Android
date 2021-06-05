package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.subfragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.WishDetailActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.WishEditActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.GlidePlaceImage;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResOngoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResOngoingFragment extends Fragment {

    private ArrayList<Resolution> dbWishes = new ArrayList<>();
    private ListView myListView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResOngoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResFragment.
     */
    // TODO: Rename and change types and number of parameters
    private static ResOngoingFragment newInstance(String param1, String param2) {
        ResOngoingFragment fragment = new ResOngoingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_res, container, false);
        View empty = v.findViewById(R.id.no_res_linear);

        myListView = v.findViewById(R.id.list);

        myListView.setEmptyView(empty);
        myListView.setAdapter(new WishAdapter(getActivity()
                , R.layout.fragment_resolutions, dbWishes));
        refreshData();
        addNewRes(v);
        customBg(v);
        return v;
    }

    private void customBg(View v){
        RelativeLayout mYes_res_bg = v.findViewById(R.id.yes_res_bg);
        if (dbWishes.size() == 0)
            mYes_res_bg.setVisibility(View.GONE);
    }

    /*
     @Override
    public void onContentChanged() {
        super.onContentChanged();

        try {
            View empty = findViewById(R.id.noWishes);
            myListView = findViewById(R.id.list);
            myListView.setEmptyView(empty);
            myListView.setAdapter(new ArrayAdapter(this, R.layout.activity_base
            , new ArrayList()));
        }catch(NullPointerException ne){
            ne.printStackTrace();
        }

    }
     */

    private void addNewRes(View v){
        Button mNo_res_button = v.findViewById(R.id.no_res_button);
        //set up shiny selection on tabs
        mNo_res_button.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), WishEditActivity.class));
        });
    }

    private void refreshData() {
        dbWishes.clear();
        NexmiiDatabaseHandler dbh = new NexmiiDatabaseHandler(getApplicationContext());
        ArrayList<Resolution> wishesFromDB = dbh.getWishes();
        for (int i = 0; i < wishesFromDB.size(); i++){

            String title = wishesFromDB.get(i).getTitle();
            String dateText = wishesFromDB.get(i).getRecordDate();
            String content = wishesFromDB.get(i).getContent();
            String emoji = wishesFromDB.get(i).getEmojiAddress();
            String revived = wishesFromDB.get(i).getEmojiRevived();

            Log.i("bluetwo", "address: " + emoji + " | revived: " + revived);

            int mid = wishesFromDB.get(i).getItemId();
            Resolution myWishy = new Resolution();
            myWishy.setTitle(title);
            myWishy.setContent(content);
            myWishy.setEmojiAddress(emoji);
            myWishy.setRecordDate(dateText);
            myWishy.setEmojiRevived(revived);
            myWishy.setItemId(mid);
            dbWishes.add(myWishy);
        }
        dbh.close();
        ResOngoingFragment.WishAdapter wishyAdapter = new ResOngoingFragment.WishAdapter
                (getActivity(), R.layout.row_res, dbWishes);
        myListView.setAdapter(wishyAdapter);
        wishyAdapter.notifyDataSetChanged();
    }

    public class WishAdapter extends ArrayAdapter<Resolution> {

        Activity activity;
        int layoutResource;
        ArrayList<Resolution> mData;

        WishAdapter(Activity act, int resource, ArrayList<Resolution> data) {
            super(act, resource, data);

            activity = act;
            layoutResource = resource;
            mData = data;

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public Resolution getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(Resolution item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView,ViewGroup parent) {

            View row = convertView;
            ResOngoingFragment.WishAdapter.ViewHolder holder;

            if (row == null || (row.getTag()) == null){

                LayoutInflater theInflator = LayoutInflater.from(activity);

                row = theInflator.inflate(layoutResource, null);
                holder = new ResOngoingFragment.WishAdapter.ViewHolder();
                holder.mTitle = row.findViewById(R.id.name_res);
                holder.mDate = row.findViewById(R.id.dateText_res);
                holder.mEmoji = row.findViewById(R.id.thumbNail_res);
                row.setTag(holder);

            } else {
                holder = (ResOngoingFragment.WishAdapter.ViewHolder) row.getTag();
            }

            holder.theWish = getItem(position);

            if (holder.theWish != null) {
                holder.mTitle.setText(holder.theWish.getTitle());

                String time = holder.theWish.getRecordDate();
                holder.mDate.setText(time);

                if (getActivity() != null) {
                    Log.i("blue", "worked: " + holder.theWish.getTitle());
                    Log.i("blue", "worked: " + holder.theWish.getEmojiAddress());
                    Log.i("blue", "worked: " + holder.theWish.getEmojiRevived());
                    if (holder.theWish.getEmojiAddress() == null ||
                            holder.theWish.getEmojiAddress().equals("")) {
                        GlidePlaceImage.PLACE(getActivity(),
                                "http://jbytestudios.com/wp-content/uploads/2020/04/emojinexmiicool.png"
                                , holder.mEmoji);
                    }else {
                        GlidePlaceImage.PLACE(getActivity(), holder.theWish
                                .getEmojiAddress(), holder.mEmoji);
                    }
                }else{
                    Log.i("blue", "did not work");
                }
            }

            //CLICK PER ROW

            final ResOngoingFragment.WishAdapter.ViewHolder finalHolder = holder;
            row.setOnClickListener(v -> {
                String text = finalHolder.theWish.getContent();
                String dateText = finalHolder.theWish.getRecordDate();
                String title = finalHolder.theWish.getTitle();
                String emoji = finalHolder.theWish.getEmojiAddress();
                String revived = holder.theWish.getEmojiRevived();
                int mid = finalHolder.theWish.getItemId();
                Log.i("BLUE4", "Resolution Id is " + mid + " " + holder.theWish
                        .getEmojiRevived());
                Intent i = new Intent(getActivity(), WishDetailActivity.class);
                i.putExtra("title", title);
                i.putExtra("content", text);
                i.putExtra("date", dateText);
                i.putExtra("emojiAddress", emoji);
                i.putExtra("emojiRevived", revived);
                i.putExtra("id", mid);
                i.putExtra("resolution_status", "resolution_ongoing");
                startActivity(i);

            });
            return row;
        }

        class ViewHolder{
            Resolution theWish;

            TextView mTitle;
            TextView mDate;
            ImageView mEmoji;
        }
    }
}
