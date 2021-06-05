package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters.SliderTutorialAdapter;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata.Emojies;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services.MyAsyncTask;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout mDots_linear_layout;

    private TextView mSlide_text_previous, mSlide_text_next;
    private int mCurrentPage;

    private final ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            mCurrentPage = position;
            sliderButtonsState();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //Downloading all emojies to save to cache
        MyAsyncTask myAsyncTask = new MyAsyncTask(TutorialActivity.this);
        myAsyncTask.execute(Emojies.ALL_EMOJIES);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        mDots_linear_layout = findViewById(R.id.dots_linear_layout);
        mSlide_text_previous = findViewById(R.id.slide_text_previous);
        mSlide_text_next = findViewById(R.id.slide_text_next);
        vp = findViewById(R.id.tut_view_pager);
        SliderTutorialAdapter sa = new SliderTutorialAdapter(this);

        vp.setAdapter(sa);
        addDotsIndicator(0);
        vp.addOnPageChangeListener(viewListener);
        elementEnabled(mSlide_text_previous, false, View.INVISIBLE);
        textActions();
    }

    private void addDotsIndicator(int position){
        TextView[] dots = new TextView[4];
        mDots_linear_layout.removeAllViews();

        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.nexmii_yellow_opac));

            mDots_linear_layout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.nexmii_yellow));
    }

    private void sliderButtonsState(){
        if (mCurrentPage == 0){
            elementEnabled(mSlide_text_previous, false, View.INVISIBLE);
            elementEnabled(mSlide_text_next, true, View.VISIBLE);
            mSlide_text_next.setText(getResources().getString(R.string.tut_btn_next));
        }else if(mCurrentPage == 3){
            elementEnabled(mSlide_text_previous, true, View.VISIBLE);
            mSlide_text_next.setText(getResources().getString(R.string.tut_btn_finish));
        }else{
            elementEnabled(mSlide_text_previous, true, View.VISIBLE);
            elementEnabled(mSlide_text_next, true, View.VISIBLE);
            mSlide_text_next.setText(getResources().getString(R.string.tut_btn_next));
        }
    }

    private void textActions(){
        mSlide_text_previous.setOnClickListener(v -> vp.setCurrentItem(mCurrentPage - 1));
        mSlide_text_next.setOnClickListener(v -> {
            if (mSlide_text_next.getText().equals(getResources().getString
                    (R.string.tut_btn_finish))){
                startActivity(new Intent(TutorialActivity.this
                        , BaseActivity.class));
                Preferences.setDefaults("tutran", "yes"
                        , TutorialActivity.this);
            }else{
                vp.setCurrentItem(mCurrentPage + 1);
            }
        });
    }

    private void elementEnabled(View view, boolean enabled, int visibility){
        view.setEnabled(enabled);
        view.setClickable(enabled);
        view.setVisibility(visibility);
    }
}
