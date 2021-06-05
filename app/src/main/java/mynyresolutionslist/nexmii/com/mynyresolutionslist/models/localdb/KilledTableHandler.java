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
final class KilledTableHandler {

    static void DELETE_KILL(int id, SQLiteDatabase db){
        db.delete(LocalDbConstants.KILL_TABLE_NAME, LocalDbConstants.KILL_KEY_ID
                + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    static void ADD_KILL(Resolution wish, SQLiteDatabase db){
        ContentValues myValues = new ContentValues();
        DateTime dateTime = new DateTime();
        myValues.put(LocalDbConstants.KILL_TITLE_NAME, wish.getTitle());
        myValues.put(LocalDbConstants.KILL_CONTENT_NAME, wish.getContent());
        myValues.put(LocalDbConstants.KILL_DATE_NAME, dateTime.rawCurrentTimeDate());
        myValues.put(LocalDbConstants.KILL_EMOJI_ADDRESS, wish.getEmojiAddress());
        myValues.put(LocalDbConstants.KILL_EMOJI_REVIVED, wish.getEmojiRevived());
        db.insert(LocalDbConstants.KILL_TABLE_NAME, null, myValues);
        Log.v("Achieved added!", "Achieved added!");
        db.close();
    }

    static ArrayList<Resolution> GET_KILL(SQLiteDatabase db, ArrayList<Resolution> theKillList){
        Cursor theCursor = db.query(LocalDbConstants.KILL_TABLE_NAME, new String[]{
                        LocalDbConstants.KILL_KEY_ID, LocalDbConstants.KILL_TITLE_NAME,
                        LocalDbConstants.KILL_CONTENT_NAME, LocalDbConstants.KILL_EMOJI_ADDRESS,
                        LocalDbConstants.KILL_EMOJI_REVIVED, LocalDbConstants.KILL_DATE_NAME
                }
                , null, null, null, null
                , LocalDbConstants.KILL_DATE_NAME + "   DESC");
        if (theCursor.moveToFirst()){
            do{
                Resolution resolution = new Resolution();
                resolution.setTitle(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_TITLE_NAME)));
                resolution.setContent(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_CONTENT_NAME)));
                resolution.setItemId(theCursor.getInt(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_KEY_ID)));
                resolution.setEmojiAddress(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_EMOJI_ADDRESS)));
                resolution.setEmojiRevived(theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_EMOJI_REVIVED)));
                String dateData = (theCursor.getString(theCursor.getColumnIndex
                        (LocalDbConstants.KILL_DATE_NAME)));
                resolution.setRecordDate(dateData);
                theKillList.add(resolution);
            } while (theCursor.moveToNext());

        }
        theCursor.close();
        db.close();
        return theKillList;
    }

}
