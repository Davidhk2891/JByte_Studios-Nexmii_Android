package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;

/**
 * Created by David on 4/4/2020 for Nexmii.
 */
public class SliderTutorialAdapter extends PagerAdapter {

    private Context ctx;

    public SliderTutorialAdapter(Context ctx){
        this.ctx = ctx;
    }

    private int[] sliderImages = {
        R.drawable.tutemojione,
        R.drawable.tutemojitwo,
        R.drawable.tutemojithree,
        R.drawable.tutemojifour
    };

    private int[] sliderTitles = {
            R.string.tut_title_silder_1,
            R.string.tut_title_silder_2,
            R.string.tut_title_silder_3,
            R.string.tut_title_silder_4
    };

    private int[] sliderTexts = {
            R.string.tut_text_silder_1,
            R.string.tut_text_silder_2,
            R.string.tut_text_silder_3,
            R.string.tut_text_silder_4,
    };

    @Override
    public int getCount() {
        return sliderImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.slider_layout, container, false);

        ImageView mSlide_image = view.findViewById(R.id.slide_image);
        TextView mSlide_title = view.findViewById(R.id.silde_title);
        TextView mSlide_text = view.findViewById(R.id.slide_text);

        mSlide_image.setImageResource(sliderImages[position]);
        mSlide_title.setText(sliderTitles[position]);
        mSlide_text.setText(sliderTexts[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
