package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters.RecyclerViewQuoteAdapter;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.QuotesSelectorOps;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.QuoteConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Quote;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.QuotesAdditionActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities.StoreActivity;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.animations.BlurEffect;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.LockedChestMessage;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.PreferencesInterface;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotesFragment extends Fragment {

    private View v;
    private BlurView mQuotesBlurView;
    private LockedChestMessage mLockedChestMessage;

    private FloatingActionButton mfq;

    //List of earned quotes
    private ArrayList<Quote> dbQuotes;
    private NexmiiDatabaseHandler dbh;
    private QuotesSelectorOps qso;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public QuotesFragment() {
        // Required empty public constructor
    }

    private void refreshData() {
        dbQuotes.clear();
        ArrayList<Quote> quotesFromDB = dbh.getQuotes();
        for (int i = 0; i < quotesFromDB.size(); i++){
            String quote = quotesFromDB.get(i).getQuote();
            String category = quotesFromDB.get(i).getCategory();
            String likedOrNot = quotesFromDB.get(i).getLiked();
            int mid = quotesFromDB.get(i).getItemId();
            Quote myQuote = new Quote();
            myQuote.setQuote(quote);
            myQuote.setCategory(category);
            myQuote.setItemId(mid);
            myQuote.setLiked(likedOrNot);
            Log.i("BLUE QUOTE STATUS", "IS:: " + likedOrNot);
            dbQuotes.add(myQuote);
        }
        dbh.close();
        RecyclerViewQuoteAdapter rva = new RecyclerViewQuoteAdapter(getActivity(), dbQuotes);
        RecyclerView mQuotes_recyclerView = v.findViewById(R.id.quotes_recyclerView);
        mQuotes_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mQuotes_recyclerView.setAdapter(rva);
        rva.notifyDataSetChanged();
    }

    private void saveFirstThreeFreeQuotes(){
        if (getActivity() != null) {
            Preferences.savedPreferencesChecker(getActivity().getResources().getString
                    (R.string.one_time_four_quotes_key), getActivity()
                    , new PreferencesInterface() {
                @Override
                public void preferenceIsSaved() {

                }

                @Override
                public void preferenceIsNotSaved() {
                    Log.i("FirstFour", "This should only run once per app installation");
                    qso.oneTimeQuoteArraySelector();
                }
            });
        }
    }

    private static QuotesFragment newInstance(String param1, String param2) {
        QuotesFragment fragment = new QuotesFragment();
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
        v = inflater.inflate(R.layout.fragment_quotes, container, false);
        if (getActivity() != null)
            mfq = getActivity().findViewById(R.id.fab_quote);

        mQuotesBlurView = v.findViewById(R.id.quotes_blurView);
        mLockedChestMessage = new LockedChestMessage(getActivity());

        if (getActivity() != null)
            BlurEffect.blurScreen(mQuotesBlurView, getActivity(), getActivity());

        dbQuotes = new ArrayList<>();
        if (getActivity() != null) {
            dbh = new NexmiiDatabaseHandler(getActivity());
            qso = new QuotesSelectorOps(getActivity());
        }
        quotesListInitializer();
        Log.i("BLACK", "RAN");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (QuoteConstants.CHEST_USED || QuoteConstants.QUOTE_LIKED_UNLIKED_USED) {
            Log.i("BLACK2", "RAN");

            dbQuotes = new ArrayList<>();
            if (getActivity() != null) {
                dbh = new NexmiiDatabaseHandler(getActivity());
                qso = new QuotesSelectorOps(getActivity());
            }

            quotesListInitializer();

            QuoteConstants.CHEST_USED = false;
        }
    }

    private void quotesListInitializer(){
        if (getActivity() != null){
            saveFirstThreeFreeQuotes();
            refreshData();
            final RelativeLayout rl = getActivity().findViewById(R.id.relativeLayout);
            if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, getActivity())
                    != null){
                Log.i("CRYSTAL", "1.0");
                if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED, getActivity())
                        .equals(InAppBillingConstants.SAVED)){
                    Log.i("CRYSTAL", "1.1");
                    //PREMIUM - VIP
                    //if saved counter >= 10 etc...
                    restoreChestIconColors();
                    if(Preferences.getDefaults(QuoteConstants.SAVED_PREMIUM_CHEST_COUNTER
                            , getActivity()) != null) {
                        Log.i("CRYSTAL", "1.1.1");
                        if (Integer.parseInt(Preferences.getDefaults(QuoteConstants
                                        .SAVED_PREMIUM_CHEST_COUNTER
                                , getActivity())) >= 9) {
                            //LIMIT REACHED
                            Log.i("CRYSTAL", "1.2");
                            chestLockedOps();
                        } else {
                            //MORE CHESTS AVAILABLE
                            Log.i("CRYSTAL", "1.3");
                            onQuoteButtonAction(mfq, () -> startActivity(new Intent(getActivity()
                                    , QuotesAdditionActivity.class)));
                        }
                    }else{
                        onQuoteButtonAction(mfq, () -> startActivity(new Intent(getActivity()
                                , QuotesAdditionActivity.class)));
                    }
                }
            }else{
                //FREE
                Log.i("CRYSTAL", "2.0");
                if (Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY, getActivity()) != null){
                    if(Preferences.getDefaults(QuoteConstants.ALL_CHESTS_KEY, getActivity())
                            .equals(QuoteConstants.VAL_USED)) {
                        Log.i("CRYSTAL", "2.1");
                        chestLockedOps();
                    }else{
                        restoreChestIconColors();
                        onQuoteButtonAction(mfq, () -> startActivity(new Intent(getActivity()
                                , QuotesAdditionActivity.class)));
                    }
                }else{
                    //Chests available
                    restoreChestIconColors();
                    onQuoteButtonAction(mfq, () -> startActivity(new Intent(getActivity()
                            , QuotesAdditionActivity.class)));
                }
            }
        }
    }

    private void restoreChestIconColors(){
        mfq.setImageResource(R.drawable.chest);
    }

    private void chestLockedOps(){
        //Both chests already opened today
        if (getActivity() != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mfq.setBackgroundTintList(ColorStateList.valueOf(getActivity()
                        .getResources().getColor(R.color.lightGrey)));
                mfq.setImageResource(R.drawable.chestblue);
            }else{
                mfq.setBackgroundColor(getActivity().getResources()
                        .getColor(R.color.lightGrey));
                mfq.setImageResource(R.drawable.chestblue);
            }
            onQuoteButtonAction(mfq, () -> {
                if (getActivity() != null)
                    if (Preferences.getDefaults(InAppBillingConstants.PREMIUM_EQUIPPED,
                            getActivity()) != null) {
                        //CHEST LOCKED MESSAGE PREMIUM
                        customPremiumLockedChestMessage();
                    }else{
                        //CHEST LOCKED MESSAGE FREE
                        customFreeLockedChestMessage();
                    }
            });
        }
    }

    private void customPremiumLockedChestMessage(){
        //BlurView
        mQuotesBlurView.setVisibility(View.VISIBLE);
        mfq.hide();
        //init custom dialog window
        mLockedChestMessage.initPremiumChestMsgDialog(new LockedChestMessage.LockedChestInterface() {
            @Override
            public void onModalCanceled() {
                mQuotesBlurView.setVisibility(View.GONE);
                mfq.show();
            }

            @Override
            public void onModalMainBtnClicked() {
                //This one must be empty which is bad design
            }
        });
    }

    private void customFreeLockedChestMessage(){
        //BlurView
        mQuotesBlurView.setVisibility(View.VISIBLE);
        mfq.hide();
        //init custom dialog window
        mLockedChestMessage.initFreeChestMsgDialog(new LockedChestMessage.LockedChestInterface() {
            @Override
            public void onModalCanceled() {
                mQuotesBlurView.setVisibility(View.GONE);
                mfq.show();
            }

            @Override
            public void onModalMainBtnClicked() {
                if (getActivity() != null)
                getActivity().startActivity(new Intent(getActivity(), StoreActivity.class));
            }
        });
    }

    private void onQuoteButtonAction(View view, final onQuoteFabClicked onQuoteFabClicked){
        view.setOnClickListener(view1 -> onQuoteFabClicked.onclick());
    }

    private interface onQuoteFabClicked{
        void onclick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
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

    public interface OnFragmentInteractionListener {
        //void onFragmentInteraction(Uri uri);
    }
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */
}
