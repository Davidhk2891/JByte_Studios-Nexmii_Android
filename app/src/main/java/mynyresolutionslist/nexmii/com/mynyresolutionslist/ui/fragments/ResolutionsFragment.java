package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters.PagerSubFragmentsAdapter;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.BaseActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.subfragments.ResOngoingFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.ResolutionStatusMessage;

public class ResolutionsFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private ViewPager viewPager;
    private PagerSubFragmentsAdapter sectionsPagerSubFragmentsAdapter;
    private RelativeLayout mRes_resolutions_relative;
    private RelativeLayout mRes_achieved_relative;
    private RelativeLayout mRes_killed_relative;
    private TextView mRes_resolutions_number;
    private TextView mRes_achieved_number;
    private TextView mRes_killed_number;
    private BlurView mResBlurView;

    private int currentPage;
    public static boolean FROM_RES_EDITING = false;

    public ResolutionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            //String mParam1 = getArguments().getString(ARG_PARAM1);
            //String mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resolutions, container, false);

        viewPager = v.findViewById(R.id.view_pager);
        //--Tabs--//
        //LinearLayout mBtns_tabs_linear = v.findViewById(R.id.btn_tabs_linear);
        mResBlurView = v.findViewById(R.id.resBlurView);

        mRes_resolutions_number = v.findViewById(R.id.res_resolutions_number);
        mRes_achieved_number = v.findViewById(R.id.res_achieved_number);
        mRes_killed_number = v.findViewById(R.id.res_killed_number);

        //View Pager Adapter
        if (getActivity() != null) {
            sectionsPagerSubFragmentsAdapter = new PagerSubFragmentsAdapter(getActivity()
                    , getActivity().getSupportFragmentManager());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id
                            .view_pager, new ResOngoingFragment()).commit();
        }

        //Custom Tabs
        viewPager.setAdapter(sectionsPagerSubFragmentsAdapter);
        customTabsController(v);
        fabInTabsController(0);

        if (FROM_RES_EDITING)
            getRecentlyMovedResolution();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateCounters();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateCounters(){
        NexmiiDatabaseHandler db = new NexmiiDatabaseHandler(getActivity());

        mRes_resolutions_number.setText(String.valueOf(db.getNumOfRes()));
        mRes_achieved_number.setText(String.valueOf(db.getNumOfResAch()));
        mRes_killed_number.setText(String.valueOf(db.getNumOfResKill()));
    }

    private void customTabsController(View view){
        //All operations on custom tabs control (ongoing, achieved, killed)
        mRes_resolutions_relative = view.findViewById(R.id.res_resolutions_relative);
        mRes_achieved_relative = view.findViewById(R.id.res_achieved_relative);
        mRes_killed_relative = view.findViewById(R.id.res_killed_relative);
        currentPage = 0;
        tabsBtnStatesSwitcher(currentPage,true,false,false);
        RelativeLayout.OnClickListener navListener = v -> {
            switch(v.getId()){
                case R.id.res_resolutions_relative:
                    currentPage = 0;
                    tabsBtnStatesSwitcher(0,true,false,false);
                    break;
                case R.id.res_achieved_relative:
                    currentPage = 1;
                    tabsBtnStatesSwitcher(1,false,true,false);
                    break;
                case R.id.res_killed_relative:
                    currentPage = 2;
                    tabsBtnStatesSwitcher(2,false,false,true);
                    break;
            }
            fabInTabsController(currentPage);
        };

        mRes_resolutions_relative.setOnClickListener(navListener);
        mRes_achieved_relative.setOnClickListener(navListener);
        mRes_killed_relative.setOnClickListener(navListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset
                    , int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tabsBtnStatesSwitcher(0,true,false
                                ,false);
                        break;
                    case 1:
                        tabsBtnStatesSwitcher(1,false,true
                                ,false);
                        break;
                    case 2:
                        tabsBtnStatesSwitcher(2,false,false
                                ,true);
                        break;
                }
                fabInTabsController(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void fabInTabsController(int position){
        BaseActivity baseActivity = (BaseActivity)getActivity();
        NexmiiDatabaseHandler db = new NexmiiDatabaseHandler(getActivity());
        if (baseActivity != null) {
            switch (position) {
                case 0:
                    if (db.getNumOfRes() == 0)
                        baseActivity.getFab().hide();
                    else
                        baseActivity.getFab().show();
                    break;
                case 1:
                    if (db.getNumOfResAch() == 0)
                        baseActivity.getFab().hide();
                    else
                        baseActivity.getFab().show();
                    break;
                case 2:
                    if (db.getNumOfResKill() == 0)
                        baseActivity.getFab().hide();
                    else
                        baseActivity.getFab().show();
                    break;
            }
        }
    }

    private void tabsBtnStatesSwitcher(int currPage, boolean resActive, boolean achActive
            , boolean killActive){
        viewPager.setCurrentItem(currPage);
        mRes_resolutions_relative.setActivated(resActive);
        mRes_achieved_relative.setActivated(achActive);
        mRes_killed_relative.setActivated(killActive);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getRecentlyMovedResolution(){
        if (getActivity() != null) {
            Bundle editionChecker = getActivity().getIntent().getExtras();
            if (editionChecker != null) {
                Log.i("PINK","RAN");
                String res_status = editionChecker.getString("res_status");
                String emojiAddress;
                String title;
                String content;
                if (res_status != null && !res_status.equals("")) {
                    Log.i("PINK2","RAN");
                    //good to proceed
                    switch (res_status) {
                        case "ongoing":
                            tabsBtnStatesSwitcher(0, true, false
                                    , false);
                            emojiAddress = editionChecker
                                    .getString("res_status_ongoing_img");
                            title = getResources().getString(R.string.res_status_good_luck);
                            displayResStatusMessage(getResources().getColor(R.color.nexmii_yellow)
                                    ,emojiAddress,title);
                            FROM_RES_EDITING = false;
                            break;
                        case "achieved":
                            //go to resAchievedFragment
                            tabsBtnStatesSwitcher(1, false, true
                                    , false);
                            title = getResources().getString(R.string.res_status_congratulations);
                            content = getResources().getString(R.string.res_status_you_made_it_happen);
                            displayResStatusMessage(getResources().getColor(R.color.nexmii_green)
                            ,getResources().getDrawable(R.drawable.achievement),title,content);
                            FROM_RES_EDITING = false;
                            break;
                        case "killed":
                            //go to resKilledFragment
                            tabsBtnStatesSwitcher(2, false, false
                                    , true);
                            title = editionChecker.getString("res_status_kill_title");
                            content = getResources().getString(R.string.res_status_dont_giveup_next_time);
                            displayResStatusMessage(getResources().getColor(R.color.nexmii_red)
                                    ,getResources().getDrawable(R.drawable.killed),title,content);
                            FROM_RES_EDITING = false;
                            break;
                    }
                }
            }
        }
    }

    private void displayResStatusMessage(int titleColor, Drawable imgDrawable
            , String resStatusTitle, String resStatusContent){
        ResolutionStatusMessage rsm = new ResolutionStatusMessage(getActivity(),getActivity()
                ,titleColor);
        rsm.callResolutionStateMessage(mResBlurView, imgDrawable, resStatusTitle, resStatusContent);
    }

    private void displayResStatusMessage(int titleColor, String imgUrl
            , String resStatusTitle){
        ResolutionStatusMessage rsm = new ResolutionStatusMessage(getActivity(),getActivity()
                ,titleColor);
        rsm.callResolutionStateMessage(mResBlurView, imgUrl, resStatusTitle, ""
                , null);
    }
}

