package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.subfragments.ResAchievedFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.subfragments.ResOngoingFragment;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.fragments.subfragments.ResKilledFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PagerSubFragmentsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.res_tab_text_1, R.string.res_tab_text_2
            , R.string.res_tab_text_3};

    private Fragment fragment;

    public PagerSubFragmentsAdapter(Context context, FragmentManager fm) {
        //super(fm) is deprecated
        super(fm);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new ResOngoingFragment();
                break;
            case 1:
                fragment = new ResAchievedFragment();
                break;
            case 2:
                fragment = new ResKilledFragment();
                break;
        }
        return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}