package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Splash screen activity
 * This class is not connected to an XML layout
 * This class redirects to BaseActivity.class after 3 seconds
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redirect();
        //lazyRedirect();
    }

    private void lazyRedirect(){
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                //Leave empty
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, BaseActivity.class));
            }
        }.start();
    }

    private void redirect(){
        startActivity(new Intent(SplashActivity.this, BaseActivity.class));
    }
}
