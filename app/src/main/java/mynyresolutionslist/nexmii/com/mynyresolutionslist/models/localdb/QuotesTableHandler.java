package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.LocalDbConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Quote;

/**
 * Created by David on 4/11/2020 for Nexmii.
 */
final class QuotesTableHandler {

    static void DELETE_QUOTE(int id, SQLiteDatabase db){
        db.delete(LocalDbConstants.QUOTE_TABLE_NAME, LocalDbConstants.QUOTE_KEY_ID + " = ? ",
                new String[]{String.valueOf(id)});
        db.close();
    }

    static void ADD_QUOTE(Quote quote, SQLiteDatabase db){
        ContentValues myQuoteValues = new ContentValues();
        myQuoteValues.put(LocalDbConstants.QUOTE_QUOTE_MESSAGE, quote.getQuote());
        myQuoteValues.put(LocalDbConstants.QUOTE_QUOTE_CATEGORY, quote.getCategory());
        myQuoteValues.put(LocalDbConstants.QUOTE_LIKED, quote.getLiked());
        db.insert(LocalDbConstants.QUOTE_TABLE_NAME, null, myQuoteValues);
        Log.v("Quote added!", "fuck yeah!");
        db.close();
    }

    //LIKE DISLIKE QUOTE FUNCTION
    static void LIKE_QUOTE(int id, String status, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        if (status.equals("false"))
            contentValues.put(LocalDbConstants.QUOTE_LIKED, "true");
        else
            contentValues.put(LocalDbConstants.QUOTE_LIKED, "false");

        db.update(LocalDbConstants.QUOTE_TABLE_NAME, contentValues
                , LocalDbConstants.QUOTE_KEY_ID + " = ?"
                , new String[]{String.valueOf(id)});
    }

    static ArrayList<Quote> GET_QUOTES(SQLiteDatabase db, ArrayList<Quote> theQuoteList){
        String selectQuery = "SELECT * FROM" + LocalDbConstants.QUOTE_TABLE_NAME;
        Cursor cursor = db.query(LocalDbConstants.QUOTE_TABLE_NAME, new String[]{
                LocalDbConstants.QUOTE_KEY_ID, LocalDbConstants.QUOTE_QUOTE_MESSAGE
                , LocalDbConstants.QUOTE_QUOTE_CATEGORY, LocalDbConstants.QUOTE_LIKED
        }, null, null, null, null, LocalDbConstants.QUOTE_KEY_ID + " DESC");
        if (cursor.moveToFirst()){
            do {
                Quote quote = new Quote();
                quote.setQuote(cursor.getString(cursor.getColumnIndex(LocalDbConstants.QUOTE_QUOTE_MESSAGE)));
                quote.setCategory(cursor.getString(cursor.getColumnIndex(LocalDbConstants.QUOTE_QUOTE_CATEGORY)));
                quote.setLiked(cursor.getString(cursor.getColumnIndex(LocalDbConstants.QUOTE_LIKED)));
                quote.setItemId(cursor.getInt(cursor.getColumnIndex(LocalDbConstants.QUOTE_KEY_ID)));

                Log.i("BLUE Quote Id?", "is " + cursor.getInt(cursor.getColumnIndex(LocalDbConstants.QUOTE_KEY_ID)));
                Log.i("BLUE Quote Liked?", "is " + cursor.getString(cursor.getColumnIndex(LocalDbConstants.QUOTE_LIKED)));
                theQuoteList.add(quote);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return theQuoteList;
    }
}
