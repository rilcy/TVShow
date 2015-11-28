package devmobile.tvshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Elsio on 07.11.2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TVShow.db";
    private static SQLiteHelper instance;


    //Singelton
    private SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static SQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new SQLiteHelper(context.getApplicationContext());
            //instance.db.execSQL("PRAGMA foreign_keys = ON;");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShowContract.ShowEntry.CREATE_TABLE_SHOW);
        db.execSQL(ShowContract.SeasonEntry.CREATE_TABLE_SEASON);
        db.execSQL(ShowContract.EpisodeEntry.CREATE_TABLE_EPISODE);
        db.execSQL(ShowContract.CastingEntry.CREATE_TABLE_CASTING);
        db.execSQL(ShowContract.CastingEpisodeEntry.CREATE_TABLE_CASTING_EPISODE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.ShowEntry.TABLE_SHOW);
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.SeasonEntry.TABLE_SEASON);
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.EpisodeEntry.TABLE_EPISODE);
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.CastingEntry.TABLE_CASTING);
        db.execSQL("DROP TABLE IF EXISTS " + ShowContract.CastingEpisodeEntry.TABLE_CASTING_EPISODE);

        //create new tables
        onCreate(db);
    }
}
