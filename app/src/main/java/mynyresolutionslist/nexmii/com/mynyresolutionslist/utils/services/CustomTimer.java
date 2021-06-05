package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.os.CountDownTimer;

public final class CustomTimer {

    public static void CUSTOM_TIMER(int milliseconds, CustomTimerInterface customTimerInterface){
        new CountDownTimer(milliseconds, 1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                customTimerInterface.onTimesUp();
            }
        }.start();
    }

}
