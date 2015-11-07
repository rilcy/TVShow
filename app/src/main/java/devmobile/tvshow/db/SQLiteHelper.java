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
            instance.db.execSQL("PRAGMA foreign_keys = ON");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonEntry.CREATE_TABLE_PERSON);
        db.execSQL(RecordEntry.CREATE_TABLE_RECORD);
        db.execSQL(PersonRecordEntry.CREATE_TABLE_PERSON_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + PersonEntry.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + RecordEntry.TABLE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + PersonRecordEntry.TABLE_PERSON_RECORD);

        //create new tables
        onCreate(db);
    }
}
