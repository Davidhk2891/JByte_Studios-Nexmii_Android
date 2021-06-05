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
final class AchievedTableHandler {

    static void DELETE_ACH(int id, SQLiteDatabase db){
        db.delete(LocalDbConstants.ACH_TABLE_NAME, LocalDbConstants.ACH_KEY_ID
                + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    static void ADD_ACH(Resolution wish, SQLiteDatabase db){
        ContentValues myValues = new ContentValues();
        DateTime dateTime = new DateTime();
        myValues.put(LocalDbConstants.ACH_TITLE_NAME, wish.getTitle());
        myValues.put(LocalDbConstants.ACH_CONTENT_NAME, wish.getContent());
        myValues.put(LocalDbConstants.ACH_DATE_NAME, dateTime.rawCurrentTimeDate());
        myValues.put(LocalDbConstants.ACH_EMOJI_ADDRESS, wish.getEmojiAddress());
        myValues.put(LocalDbConstants.ACH_EMOJI_REVIVED, wish.getEmojiRevived());
        db.insert(LocalDbConstants.ACH_TABLE_NAME, null, myValues);
        Log.v("Achieved added!", "Achieved added!");
        db.close();
    }

    static ArrayList<Resolution> GET_ACH(SQLiteDatabase db, ArrayList<Resolution> theAchievedList){
        Cursor theCursor = db.query(LocalDbConstants.ACH_TABLE_NAME, new String[]{
                        LocalDbConstants.ACH_KEY_ID, LocalDbConstants.ACH_TITLE_NAME,
                        LocalDbConstants.ACH_CONTENT_NAME, LocalDbConstants.ACH_EMOJI_ADDRESS,
                        LocalDbConstants.ACH_EMOJI_REVIVED, LocalDbConstants.ACH_DATE_NAME
                }
                , null, null, null, null
                , LocalDbConstants.ACH_DATE_NAME + "   DESC");
        if (theCursor.moveToFirst()){
            do{
                Resolution resolution = new Resolution();
                resolution.setTitle(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_TITLE_NAME)));
                resolution.setContent(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_CONTENT_NAME)));
                resolution.setItemId(theCursor.getInt(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_KEY_ID)));
                resolution.setEmojiAddress(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_EMOJI_ADDRESS)));
                resolution.setEmojiRevived(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_EMOJI_REVIVED)));
                String dateData = (theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.ACH_DATE_NAME)));
                resolution.setRecordDate(dateData);
                theAchievedList.add(resolution);
            } while (theCursor.moveToNext());

        }
        theCursor.close();
        db.close();
        return theAchievedList;
    }
}
