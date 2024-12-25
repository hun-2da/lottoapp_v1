package yeong.wish.lotto.menuFragmentPage.lottoDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogDagtabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASENAME = "lottolog.db";

    public static int VERSION = 1;

    // ---------------------6/45--------------------------------
    public final static String TABLE645NAME = "table645";

    /**회차정보*/
    public final static String ID_645 = "id";
    public final static String PRIZE_645_ = "prize"; // 호출용
    public final static String PRIZE_645_1 = "prize1";
    public final static String PRIZE_645_2 = "prize2";
    public final static String PRIZE_645_3 = "prize3";
    public final static String PRIZE_645_4 = "prize4";
    public final static String PRIZE_645_5 = "prize5";
    public final static String PRIZE_645_6 = "prize6";

    public final static String BONUS_645 = "bonus";

    //-----------------------720+--------------------------------

    public final static String TABLE720NAME = "table720";
    /**회차정보*/
    public final static String ID_720 = "id";
    public final static String PRIZE_720_ = "prize";// 호출용

    public final static String PRIZE_720_1 = "prize1";
    public final static String PRIZE_720_2 = "prize2";
    public final static String PRIZE_720_3 = "prize3";
    public final static String PRIZE_720_4 = "prize4";
    public final static String PRIZE_720_5 = "prize5";


    public final static String PRIZE_720_6 = "prize6";
    public final static String PRIZE_720_7 = "prize7";

    public final static String BONUS_720 = "bonus";

    //-----------------------user log 645-----------------------------

    public final static String LOGTABLE_645 = "user_645log";
    public final static String USERID_645 = "id";
    public final static String USERDATE_ID_645 = "date_id";

    public final static String USERCHOICE_645_ = "choice";
    public final static String USERCHOICE_645_1 = "choice1";

    public final static String USERCHOICE_645_2 = "choice2";

    public final static String USERCHOICE_645_3 = "choice3";

    public final static String USERCHOICE_645_4 = "choice4";

    public final static String USERCHOICE_645_5 = "choice5";

    public final static String USERCHOICE_645_6 = "choice6";

    //-----------------------user log 720-----------------------------

    public final static String LOGTABLE_720 = "user_720log";
    public final static String USERID_720 = "id";
    public final static String USERDATE_ID_720 = "date_id";
    public final static String USERCHOICE_720_ = "choice";
    public final static String USERCHOICE_720_1 = "choice1";
    public final static String USERCHOICE_720_2 = "choice2";
    public final static String USERCHOICE_720_3 = "choice3";
    public final static String USERCHOICE_720_4 = "choice4";
    public final static String USERCHOICE_720_5 = "choice5";
    public final static String USERCHOICE_720_6 = "choice6";
    public final static String USERCHOICE_720_7 = "choice7";



    public LogDagtabaseHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //--------------------------------------------------------------------------------------------user log

        String CREATE_USERTABLE645 = "CREATE TABLE " + LOGTABLE_645 + " ("
                + USERID_645 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERDATE_ID_645 + " INTEGER, "
                + USERCHOICE_645_1 + " INTEGER, "
                + USERCHOICE_645_2 + " INTEGER, "
                + USERCHOICE_645_3 + " INTEGER, "
                + USERCHOICE_645_4 + " INTEGER, "
                + USERCHOICE_645_5 + " INTEGER, "
                + USERCHOICE_645_6 + " INTEGER);";
        sqLiteDatabase.execSQL(CREATE_USERTABLE645);

        String CREATE_USERTABLE720 = "CREATE TABLE " + LOGTABLE_720 + " ("
                + USERID_720 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERDATE_ID_720 + " INTEGER, "
                + USERCHOICE_720_1 + " TEXT, "
                + USERCHOICE_720_2 + " TEXT, "
                + USERCHOICE_720_3 + " TEXT, "
                + USERCHOICE_720_4 + " TEXT, "
                + USERCHOICE_720_5 + " TEXT, "
                + USERCHOICE_720_6 + " TEXT, "
                + USERCHOICE_720_7 + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_USERTABLE720);

        //--------------------------------------------------------------------------------------------여기서 부터는 결과정보

        String CREATE_645TABLE = "CREATE TABLE " + TABLE645NAME + "("
                + ID_645 + " INTEGER PRIMARY KEY,"
                + PRIZE_645_1 + " INTEGER,"
                + PRIZE_645_2 + " INTEGER,"
                + PRIZE_645_3 + " INTEGER,"
                + PRIZE_645_4 + " INTEGER,"
                + PRIZE_645_5 + " INTEGER,"
                + PRIZE_645_6 + " INTEGER,"
                + BONUS_645 + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(CREATE_645TABLE);

        String CREATE_720TABLE = "CREATE TABLE " + TABLE720NAME + " ("
                + ID_720 + " INTEGER PRIMARY KEY, "
                + PRIZE_720_1 + " TEXT,"
                + PRIZE_720_2 + " TEXT,"
                + PRIZE_720_3 + " TEXT,"
                + PRIZE_720_4 + " TEXT,"
                + PRIZE_720_5 + " TEXT,"

                + PRIZE_720_6 + " TEXT,"
                + PRIZE_720_7 + " TEXT,"

                + BONUS_720 + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_720TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 > VERSION)
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user_log");
    }
}
