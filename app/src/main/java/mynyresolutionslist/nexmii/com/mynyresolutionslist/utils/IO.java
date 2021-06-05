package mynyresolutionslist.nexmii.com.mynyresolutionslist.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class IO {

    //Write to File (OUTPUT)
    public static void writeImageUrlToFile(Context context, String fileName, String http){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName
                    , Context.MODE_PRIVATE);

            URL url = new URL(http);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
