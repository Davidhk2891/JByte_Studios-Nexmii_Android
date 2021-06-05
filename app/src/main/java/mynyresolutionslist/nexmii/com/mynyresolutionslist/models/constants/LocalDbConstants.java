package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants;

/**
 * Created by David on 12/23/2016.
 */

public final class LocalDbConstants {

    public static final String DATABASE_NAME = "nexmiidb";
    public static final int DATABASE_VERSION = 5;

    //In SQL, if I remember correctly, the whole SCHEMA should be of one data type

    //Resolutions table//
    public static final String RES_TABLE_NAME = "resolutions";
    public static final String RES_TITLE_NAME = "restitle";
    public static final String RES_CONTENT_NAME = "rescontent";
    public static final String RES_DATE_NAME = "resrecorddate";
    public static final String RES_EMOJI_ADDRESS = "resemojiaddress";
    public static final String RES_EMOJI_REVIVED = "emojirevived";
    public static final String RES_KEY_ID = "id";
    //-----------------//

    //Achieved table//
    public static final String ACH_TABLE_NAME = "achieved";
    public static final String ACH_TITLE_NAME = "achtitle";
    public static final String ACH_CONTENT_NAME = "achcontent";
    public static final String ACH_DATE_NAME = "achrecorddate";
    public static final String ACH_EMOJI_ADDRESS = "achemojiaddress";
    public static final String ACH_EMOJI_REVIVED = "emojirevived";
    public static final String ACH_KEY_ID = "id";
    //-----------------//

    //Killed table//
    public static final String KILL_TABLE_NAME = "killed";
    public static final String KILL_TITLE_NAME = "killtitle";
    public static final String KILL_CONTENT_NAME = "killcontent";
    public static final String KILL_DATE_NAME = "killrecorddate";
    public static final String KILL_EMOJI_ADDRESS = "killemojiaddress";
    public static final String KILL_EMOJI_REVIVED = "emojirevived";
    public static final String KILL_KEY_ID = "id";
    //-----------------//

    //Quotes table//
    public static final String QUOTE_TABLE_NAME = "quotes";
    public static final String QUOTE_QUOTE_MESSAGE = "message";
    public static final String QUOTE_QUOTE_CATEGORY = "category";
    public static final String QUOTE_LIKED = "false";
    public static final String QUOTE_KEY_ID = "id";
    //------------//
}
