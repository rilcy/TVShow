package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.ShowContract.*;
import devmobile.tvshow.db.object.Season;

/**
 * Created by Elsio on 07.11.2015.
 */
public class SeasonDataSource {

    private SQLiteDatabase db;
    private Context context;

    public SeasonDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    // Insert a new season
    public long createSeason(Season season){
        long id;
        ContentValues values = new ContentValues();
        int numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM TABLE_SEASON WHERE KEY_SHOW_ID = " + season.getShowId(), null);
        numRows += 1;
        values.put(SeasonEntry.KEY_NUMBER, numRows);
        values.put(SeasonEntry.KEY_COMPLETED, season.isSeasonCompleted());
        values.put(SeasonEntry.KEY_SHOW_ID, season.getShowId());
        id = this.db.insert(SeasonEntry.TABLE_SEASON, null, values);

        return id;
    }
}
