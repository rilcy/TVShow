package devmobile.tvshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Elsio on 07.11.2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TVShow.db";
    public static final String TABLE_NAME = "show_table";
    public static final String COL_1 = "SHOW_ID";
    public static final String COL_2 = "SHOW_TITLE";
    public static final String COL_3 = "SHOW_START";
    public static final String COL_4 = "SHOW_END";
    public static final String COL_5 = "SHOW_COMPLETED";
    public static final String COL_6 = "SHOW_IMAGE";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
