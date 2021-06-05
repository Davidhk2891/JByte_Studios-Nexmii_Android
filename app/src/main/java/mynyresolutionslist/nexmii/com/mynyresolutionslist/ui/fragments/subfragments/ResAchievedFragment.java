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
 * Use the {@link ResAchievedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  ResAchievedFragment extends Fragment {

    private ArrayList<Resolution> dbAchieved = new ArrayList<>();
    private ListView achListView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResAchievedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResAchievedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResAchievedFragment newInstance(String param1, String param2) {
        ResAchievedFragment fragment = new ResAchievedFragment();
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
        View v = inflater.inflate(R.layout.fragment_res_achieved, container, false);
        View empty = v.findViewById(R.id.no_ach_linear);
        achListView = v.findViewById(R.id.list_achievements);

        achListView.setEmptyView(empty);

        achListView.setAdapter(new AchievedAdapter(getActivity()
                , R.layout.fragment_res_achieved, dbAchieved));
        refreshData();
        addNewRes(v);
        customBg(v);
        return v;
    }

    private void customBg(View v){
        RelativeLayout mYes_ach_bg = v.findViewById(R.id.yes_ach_bg);
        if (dbAchieved.size() == 0)
            mYes_ach_bg.setVisibility(View.GONE);
    }

    private void addNewRes(View v){
        Button mNo_ach_button = v.findViewById(R.id.no_ach_button);
        //fix thing with add button
        //set up shiny selection on tabs
        mNo_ach_button.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), WishEditActivity.class));
        });
    }

    private void refreshData() {
        dbAchieved.clear();
        NexmiiDatabaseHandler dbh = new NexmiiDatabaseHandler(getApplicationContext());
        ArrayList<Resolution> achievedFromDB = dbh.getAchieved();
        for (int i = 0; i < achievedFromDB.size(); i++){
            String title = achievedFromDB.get(i).getTitle();
            String dateText = achievedFromDB.get(i).getRecordDate();
            String content = achievedFromDB.get(i).getContent();
            String emoji = achievedFromDB.get(i).getEmojiAddress();
            String revived = achievedFromDB.get(i).getEmojiRevived();

            int mid = achievedFromDB.get(i).getItemId();
            Resolution myWishy = new Resolution();
            myWishy.setTitle(title);
            myWishy.setContent(content);
            myWishy.setRecordDate(dateText);
            myWishy.setEmojiAddress(emoji);
            myWishy.setEmojiRevived(revived);
            myWishy.setItemId(mid);
            dbAchieved.add(myWishy);
        }
        dbh.close();
        ResAchievedFragment.AchievedAdapter AchievedAdapter = new AchievedAdapter(getActivity()
                , R.layout.row_achieved, dbAchieved);
        achListView.setAdapter(AchievedAdapter);
        AchievedAdapter.notifyDataSetChanged();
    }

    public class AchievedAdapter extends ArrayAdapter<Resolution> {

        Activity activity;
        int layoutResource;
        ArrayList<Resolution> mData;

        AchievedAdapter(Activity act, int resource, ArrayList<Resolution> data) {
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
            ResAchievedFragment.AchievedAdapter.ViewHolder holder;

            if (row == null || (row.getTag()) == null){

                LayoutInflater theInflator = LayoutInflater.from(activity);

                row = theInflator.inflate(layoutResource, null);
                holder = new ViewHolder();
                holder.mTitle = row.findViewById(R.id.name_ach);
                holder.mDate = row.findViewById(R.id.dateText_ach);
                holder.mEmoji = row.findViewById(R.id.thumbNail_ach);
                row.setTag(holder);

            } else {
                holder = (ResAchievedFragment.AchievedAdapter.ViewHolder) row.getTag();
            }

            holder.theAchieved = getItem(position);

            if (holder.theAchieved != null) {
                holder.mTitle.setText(holder.theAchieved.getTitle());

                String time = holder.theAchieved.getRecordDate();
                holder.mDate.setText(time);
            }

            if (getActivity() != null) {
                Log.i("blue", "worked: " + holder.theAchieved.getEmojiAddress());
                GlidePlaceImage.PLACE(getActivity(),holder.theAchieved
                        .getEmojiAddress(),holder.mEmoji);
            }else{
                Log.i("blue", "did not work");
            }

            //CLICK PER ROW

            final ResAchievedFragment.AchievedAdapter.ViewHolder finalHolder = holder;
            row.setOnClickListener(v -> {
                String text = finalHolder.theAchieved.getContent();
                String dateText = finalHolder.theAchieved.getRecordDate();
                String title = finalHolder.theAchieved.getTitle();
                String emoji = finalHolder.theAchieved.getEmojiAddress();
                String revived =  finalHolder.theAchieved.getEmojiRevived();
                int mid = finalHolder.theAchieved.getItemId();
                Intent i = new Intent(getActivity(), WishDetailActivity.class);
                i.putExtra("title", title);
                i.putExtra("content", text);
                i.putExtra("date", dateText);
                i.putExtra("emojiAddress", emoji);
                i.putExtra("emojiRevived", revived);
                i.putExtra("id", mid);
                i.putExtra("resolution_status", "resolution_achieved");
                startActivity(i);
            });


            return row;
        }

        class ViewHolder{
            Resolution theAchieved;

            TextView mTitle;
            TextView mDate;
            ImageView mEmoji;
        }
    }
}
