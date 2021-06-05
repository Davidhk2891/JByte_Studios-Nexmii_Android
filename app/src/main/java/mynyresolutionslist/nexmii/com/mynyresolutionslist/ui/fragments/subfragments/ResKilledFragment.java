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
 * Use the {@link ResKilledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResKilledFragment extends Fragment {

    private ArrayList<Resolution> dbKilled = new ArrayList<>();
    private ListView killListView;

    private String blue_emoji;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResKilledFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResKilledFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResKilledFragment newInstance(String param1, String param2) {
        ResKilledFragment fragment = new ResKilledFragment();
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
        View v = inflater.inflate(R.layout.fragment_res_killed, container, false);
        View empty = v.findViewById(R.id.no_kill_linear);
        killListView = v.findViewById(R.id.list_killed);

        killListView.setEmptyView(empty);

        killListView.setAdapter(new KilledAdapter(getActivity()
                , R.layout.fragment_res_killed, dbKilled));
        refreshData();
        addNewRes(v);
        customBg(v);
        return v;
    }

    private void customBg(View v){
        RelativeLayout mYes_kill_bg = v.findViewById(R.id.yes_kill_bg);
        if (dbKilled.size() == 0)
            mYes_kill_bg.setVisibility(View.GONE);
    }

    private void addNewRes(View v){
        Button mNo_kill_button = v.findViewById(R.id.no_kill_button);
        //create custom button bg
        //set up foreground click
        //do rest of empties
        //set up shiny selection on tabs
        mNo_kill_button.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), WishEditActivity.class));
        });
    }

    private void refreshData() {
        dbKilled.clear();
        NexmiiDatabaseHandler dbh = new NexmiiDatabaseHandler(getApplicationContext());
        ArrayList<Resolution> killedFromDB = dbh.getKilled();
        for (int i = 0; i < killedFromDB.size(); i++){

            String title = killedFromDB.get(i).getTitle();
            String dateText = killedFromDB.get(i).getRecordDate();
            String content = killedFromDB.get(i).getContent();
            String emoji = killedFromDB.get(i).getEmojiAddress();
            String revived = killedFromDB.get(i).getEmojiRevived();

            int mid = killedFromDB.get(i).getItemId();
            Resolution myWishy = new Resolution();
            myWishy.setTitle(title);
            myWishy.setContent(content);
            myWishy.setRecordDate(dateText);
            myWishy.setItemId(mid);
            myWishy.setEmojiAddress(emoji);
            myWishy.setEmojiRevived(revived);
            dbKilled.add(myWishy);
        }
        dbh.close();
        ResKilledFragment.KilledAdapter KilledAdapter = new KilledAdapter(getActivity()
                , R.layout.row_killed, dbKilled);
        killListView.setAdapter(KilledAdapter);
        KilledAdapter.notifyDataSetChanged();
    }

    public class KilledAdapter extends ArrayAdapter<Resolution> {

        Activity activity;
        int layoutResource;
        ArrayList<Resolution> mData;

        KilledAdapter(Activity act, int resource, ArrayList<Resolution> data) {
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
            ResKilledFragment.KilledAdapter.ViewHolder holder;

            if (row == null || (row.getTag()) == null){

                LayoutInflater theInflator = LayoutInflater.from(activity);

                row = theInflator.inflate(layoutResource, null);
                holder = new ViewHolder();
                holder.mTitle = row.findViewById(R.id.name_kill);
                holder.mDate = row.findViewById(R.id.dateText_kill);
                holder.mEmoji = row.findViewById(R.id.thumbNail_kill);
                row.setTag(holder);

            } else {
                holder = (ResKilledFragment.KilledAdapter.ViewHolder) row.getTag();
            }

            holder.theKilled = getItem(position);

            if (holder.theKilled != null) {
                holder.mTitle.setText(holder.theKilled.getTitle());

                String time = holder.theKilled.getRecordDate();
                holder.mDate.setText(time);
            }

            if (getActivity() != null) {
                String png = ".png";
                String og_emoji = holder.theKilled.getEmojiAddress();
                String cut_og_emoji = og_emoji.replaceAll(png, "");
                if (cut_og_emoji.contains("05"))
                    cut_og_emoji = cut_og_emoji.replace("05","04");
                blue_emoji = cut_og_emoji + getActivity().getResources()
                        .getString(R.string.emojies_blue_png);
                Log.i("blue", "worked: " + og_emoji);
                Log.i("blue2", "worked: " + cut_og_emoji);
                Log.i("blue3", "worked: " + blue_emoji);
                GlidePlaceImage.PLACE(getActivity(),blue_emoji,holder.mEmoji);
            }else{
                Log.i("blue", "did not work");
            }

            //CLICK PER ROW

            //final ResKilledFragment.KilledAdapter.ViewHolder holder = holder;

            row.setOnClickListener(v -> {
                String text = holder.theKilled.getContent();
                String dateText = holder.theKilled.getRecordDate();
                String title = holder.theKilled.getTitle();

                String png = ".png";

                //IMPORTANT
                String og_emoji = holder.theKilled.getEmojiAddress();

                String cut_og_emoji = og_emoji.replaceAll(png, "");
                if (cut_og_emoji.contains("05"))
                    cut_og_emoji = cut_og_emoji.replace("05","04");
                String local_blue_emoji = cut_og_emoji + getActivity().getResources()
                        .getString(R.string.emojies_blue_png);


                Log.i("blue3.1.1", local_blue_emoji);
                String revived = holder.theKilled.getEmojiRevived();
                //TODO:
                //problem here. Is not yes/no. is the address
                Log.i("purple5", "is " + holder.theKilled.getEmojiRevived());
                int mid = holder.theKilled.getItemId();
                Intent i = new Intent(getActivity(), WishDetailActivity.class);
                i.putExtra("title", title);
                i.putExtra("content", text);
                i.putExtra("date", dateText);
                i.putExtra("emojiAddress", local_blue_emoji);
                Log.i("blue3.1", local_blue_emoji);
                i.putExtra("emojiRevived", revived);
                i.putExtra("id", mid);
                i.putExtra("resolution_status", "resolution_killed");
                startActivity(i);
            });
            return row;
        }

        class ViewHolder{
            Resolution theKilled;

            TextView mTitle;
            TextView mDate;
            ImageView mEmoji;
        }
    }
}
