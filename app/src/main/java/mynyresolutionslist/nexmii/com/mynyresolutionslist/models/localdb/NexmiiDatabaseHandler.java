package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Quote;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Resolution;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.LocalDbConstants;

public class NexmiiDatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<Resolution> theWishList = new ArrayList<>();
    private final ArrayList<Resolution> theAchievedList = new ArrayList<>();
    private final ArrayList<Resolution> theKilledList = new ArrayList<>();
    private final ArrayList<Quote> theQuoteList = new ArrayList<>();

    public NexmiiDatabaseHandler(Context context) {
        super(context, LocalDbConstants.DATABASE_NAME, null, LocalDbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WISHES_TABLE = "CREATE TABLE " + LocalDbConstants.RES_TABLE_NAME +
                "(" + LocalDbConstants.RES_KEY_ID + " INTEGER PRIMARY KEY, " +
                LocalDbConstants.RES_TITLE_NAME + " TEXT, " + LocalDbConstants.RES_CONTENT_NAME + " TEXT, " +
                LocalDbConstants.RES_EMOJI_ADDRESS + " TEXT, " + LocalDbConstants.RES_EMOJI_REVIVED + " TEXT, " +
                LocalDbConstants.RES_DATE_NAME + " TEXT);";

        String CREATE_ACHIEVED_TABLE = "CREATE TABLE " + LocalDbConstants.ACH_TABLE_NAME +
                "(" + LocalDbConstants.ACH_KEY_ID + " INTEGER PRIMARY KEY, " +
                LocalDbConstants.ACH_TITLE_NAME + " TEXT, " + LocalDbConstants.ACH_CONTENT_NAME + " TEXT, " +
                LocalDbConstants.ACH_EMOJI_ADDRESS + " TEXT, " + LocalDbConstants.ACH_EMOJI_REVIVED + " TEXT, "
                + LocalDbConstants.ACH_DATE_NAME + " TEXT);";

        String CREATE_KILLED_TABLE = "CREATE TABLE " + LocalDbConstants.KILL_TABLE_NAME +
                "(" + LocalDbConstants.RES_KEY_ID + " INTEGER PRIMARY KEY, " +
                LocalDbConstants.KILL_TITLE_NAME + " TEXT, " + LocalDbConstants.KILL_CONTENT_NAME + " TEXT, " +
                LocalDbConstants.KILL_EMOJI_ADDRESS + " TEXT, " + LocalDbConstants.KILL_EMOJI_REVIVED + " TEXT, "
                + LocalDbConstants.KILL_DATE_NAME + " TEXT);";

        String CREATE_QUOTES_TABLE = "CREATE TABLE " + LocalDbConstants.QUOTE_TABLE_NAME +
                "(" + LocalDbConstants.QUOTE_KEY_ID + " INTEGER PRIMARY KEY, " +
                LocalDbConstants.QUOTE_QUOTE_MESSAGE + " TEXT, " + LocalDbConstants.QUOTE_QUOTE_CATEGORY
                + " TEXT, " + LocalDbConstants.QUOTE_LIKED + " TEXT);";

        db.execSQL(CREATE_WISHES_TABLE);
        db.execSQL(CREATE_ACHIEVED_TABLE);
        db.execSQL(CREATE_KILLED_TABLE);
        db.execSQL(CREATE_QUOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("onUpgrade", "ran");
        db.execSQL("DROP TABLE IF EXISTS " + LocalDbConstants.RES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocalDbConstants.ACH_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocalDbConstants.KILL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocalDbConstants.QUOTE_TABLE_NAME);
        //create a new one
        onCreate(db);
    }

    //####RESOLUTIONS####//

    //DELETE RESOLUTION
    public void deleteWish(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ResolutionsTableHandler.DELETE_WISH(id,db);
    }

    //ADD RESOLUTION
    public void addWishes(Resolution wish){
        SQLiteDatabase db = this.getWritableDatabase();
        ResolutionsTableHandler.ADD_WISH(wish, db);
    }

    //GET ALL RESOLUTIONS
    public ArrayList<Resolution> getWishes(){
        SQLiteDatabase db = this.getReadableDatabase();
        return ResolutionsTableHandler.GET_WISHES(db, theWishList);
    }

    //GET AMOUNT OF RESOLUTIONS
    public int getNumOfRes(){
        SQLiteDatabase db = this.getReadableDatabase();
        return ResolutionsTableHandler.GET_WISHES(db, theWishList).size();
    }

    //####ACHIEVED####//

    //DELETE ACHIEVED
    public void deleteAchieved(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        AchievedTableHandler.DELETE_ACH(id, db);
    }

    //ADD ACHIEVED
    public void addAchieved(Resolution achieved){
        SQLiteDatabase db = this.getWritableDatabase();
        AchievedTableHandler.ADD_ACH(achieved, db);
    }

    //GET ALL ACHIEVED
    public ArrayList<Resolution> getAchieved(){
        SQLiteDatabase db = this.getReadableDatabase();
        return AchievedTableHandler.GET_ACH(db, theAchievedList);
    }

    //GET AMOUNT OF RES_ACHIEVED
    public int getNumOfResAch(){
        SQLiteDatabase db = this.getReadableDatabase();
        return AchievedTableHandler.GET_ACH(db, theAchievedList).size();
    }

    //####KILLED####//

    //DELETE RESOLUTION
    public void deleteKilled(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        KilledTableHandler.DELETE_KILL(id,db);
    }

    //ADD RESOLUTION
    public void addKilled(Resolution kill){
        SQLiteDatabase db = this.getWritableDatabase();
        KilledTableHandler.ADD_KILL(kill, db);
    }

    //GET ALL RESOLUTIONS
    public ArrayList<Resolution> getKilled(){
        SQLiteDatabase db = this.getReadableDatabase();
        return KilledTableHandler.GET_KILL(db, theKilledList);
    }

    //GET AMOUNT OF RES_KILLED
    public int getNumOfResKill(){
        SQLiteDatabase db = this.getReadableDatabase();
        return KilledTableHandler.GET_KILL(db, theKilledList).size();
    }

    //####QUOTES####//

    public void deleteQuote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        QuotesTableHandler.DELETE_QUOTE(id, db);
    }

    public void addQuote(Quote quote){
        SQLiteDatabase db = this.getWritableDatabase();
        QuotesTableHandler.ADD_QUOTE(quote, db);
    }

    //Like quote and save that parameter.
    //Basically, switch setLiked to true and save to quote table in db
    public void likeDislikeQuote(int id, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        QuotesTableHandler.LIKE_QUOTE(id, status, db);
    }

    public ArrayList<Quote> getQuotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        return QuotesTableHandler.GET_QUOTES(db, theQuoteList);
    }
}
