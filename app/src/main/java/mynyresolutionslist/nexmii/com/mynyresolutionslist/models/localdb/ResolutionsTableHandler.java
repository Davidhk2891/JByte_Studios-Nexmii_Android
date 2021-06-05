package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.LocalDbConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.customclockops.DateTime;

/**
 * Created by David on 4/11/2020 for Nexmii.
 */
final class ResolutionsTableHandler {

    static void DELETE_WISH(int id, SQLiteDatabase db){
        db.delete(LocalDbConstants.RES_TABLE_NAME, LocalDbConstants.RES_KEY_ID
                        + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    static void ADD_WISH(Resolution wish, SQLiteDatabase db){
        ContentValues myValues = new ContentValues();
        DateTime dateTime = new DateTime();
        myValues.put(LocalDbConstants.RES_TITLE_NAME, wish.getTitle());
        myValues.put(LocalDbConstants.RES_CONTENT_NAME, wish.getContent());
        myValues.put(LocalDbConstants.RES_DATE_NAME, dateTime.rawCurrentTimeDate());
        myValues.put(LocalDbConstants.RES_EMOJI_ADDRESS, wish.getEmojiAddress());
        myValues.put(LocalDbConstants.RES_EMOJI_REVIVED, wish.getEmojiRevived());
        db.insert(LocalDbConstants.RES_TABLE_NAME, null, myValues);

        Log.i("Wishdets:", "is: " + wish.getTitle() + " | "
        + wish.getContent() + " | " + wish.getEmojiAddress() + " | "
        + wish.getEmojiRevived());

        Log.i("Wish added!", "Wish added!");

        db.close();
    }

    static ArrayList<Resolution> GET_WISHES(SQLiteDatabase db, ArrayList<Resolution> theWishList){
        Cursor theCursor = db.query(LocalDbConstants.RES_TABLE_NAME, new String[]{
                        LocalDbConstants.RES_KEY_ID, LocalDbConstants.RES_TITLE_NAME,
                        LocalDbConstants.RES_CONTENT_NAME, LocalDbConstants.RES_EMOJI_ADDRESS,
                        LocalDbConstants.RES_EMOJI_REVIVED, LocalDbConstants.RES_DATE_NAME
                }
                , null, null, null, null
                , LocalDbConstants.RES_DATE_NAME + "   DESC");

        if (theCursor.moveToFirst()){
            do{
                Resolution resolution = new Resolution();
                resolution.setTitle(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.RES_TITLE_NAME)));
                resolution.setContent(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.RES_CONTENT_NAME)));
                resolution.setItemId(theCursor.getInt(theCursor.getColumnIndex
                        (LocalDbConstants.RES_KEY_ID)));
                resolution.setEmojiAddress(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.RES_EMOJI_ADDRESS)));
                resolution.setEmojiRevived(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.RES_EMOJI_REVIVED)));
                String dateData = (theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.RES_DATE_NAME)));
                resolution.setRecordDate(dateData);
                theWishList.add(resolution);
            } while (theCursor.moveToNext());

        }
        theCursor.close();
        db.close();
        return theWishList;
    }

}
