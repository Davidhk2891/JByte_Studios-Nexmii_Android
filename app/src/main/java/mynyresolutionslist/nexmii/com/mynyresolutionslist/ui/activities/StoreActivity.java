package mynyresolutionslist.nexmii.com.mynyresolutionslist.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.facebook.CallbackManager;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.InAppBillingConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.messages.ModalMessage;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

public class StoreActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor bp;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        callbackManager = CallbackManager.Factory.create();
        //3rd party billing//
        bp = BillingProcessor.newBillingProcessor(this, InAppBillingConstants.BILLING_KEY, this);
        bp.initialize();
        //-----------------//
    }

    //----3rd party library Google Play billing processor----//
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        if (productId.equals(InAppBillingConstants.PREMIUM_PRODUCT)){

            ModalMessage.standardModalMessageNoTitle(StoreActivity.this
                    ,getResources().getString(R.string.vip_equipped)
                    ,getResources().getString(R.string.cancel), () -> startActivity(new Intent(StoreActivity.this
                            , BaseActivity.class)));

            Preferences.setDefaults(InAppBillingConstants.PREMIUM_EQUIPPED
                    , InAppBillingConstants.SAVED, StoreActivity.this);

        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        //Toast.makeText(this, "Did not buy it", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }
    //------------------------------------------------------//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    //-----PURCHASE V.I.P-----//
    public void purchaseVip(View v){
        bp.purchase(StoreActivity.this, InAppBillingConstants.PREMIUM_PRODUCT);
    }
}
