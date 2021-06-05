package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.services;

import android.content.Context;
import android.util.Log;

//Download emojies to phone cache for faster loading
public class MyAsyncTask extends android.os.AsyncTask<String, Void, Void> {

    private Context context;

    public MyAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... urls) {
        Log.i("DOWNLOADED IMAGES", "STARTED");
        GlidePlaceImage.PRELOAD(context,urls);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Log.i("DOWNLOADED IMAGES", "FINISHED");
    }
}
