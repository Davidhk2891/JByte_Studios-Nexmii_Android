package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.animations;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by David on 9/21/2019 for Nexmii.
 */
public class Animations {

    private Animation mAnimation;

    public void animate (View view) {
        mAnimation = new ScaleAnimation(1f,0.9f,1f,0.9f
                ,Animation.RELATIVE_TO_SELF, 0.9f, Animation.RELATIVE_TO_SELF
                , 0.9f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setAnimation(mAnimation);
    }

    public void stopAnimation(){
        mAnimation.cancel();
    }
}
