package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    // Add a new season
    public long createSeason(Season season){
        long id;
        ContentValues values = new ContentValues();
        long numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM TABLE_SEASON WHERE KEY_SHOW_ID = " + season.getShowId(), null);
        numRows += 1;
        values.put(SeasonEntry.KEY_NUMBER, numRows);
        values.put(SeasonEntry.KEY_COMPLETED, 0);
        values.put(SeasonEntry.KEY_SHOW_ID, season.getShowId());
        id = this.db.insert(SeasonEntry.TABLE_SEASON, null, values);

        return id;
    }

    /**
     * Get one season
     */
    public Season getSeasonById(int id){
        String sql = "SELECT * FROM " + SeasonEntry.TABLE_SEASON +
                " WHERE " + SeasonEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Season season = new Season();
        season.setSeasonId(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_ID)));
        season.setShowId(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_SHOW_ID)));
        season.setSeasonNumber(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_NUMBER)));
        season.setSeasonCompleted(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_COMPLETED)));

        return season;
    }

    /**
     * Get all seasons
     */
    public List<Season> getAllSeasons(){
        List<Season> seasons = new ArrayList<Season>();
        String sql = "SELECT * FROM " + SeasonEntry.TABLE_SEASON + "WHERE KEY_SHOW_ID = " + SeasonEntry.KEY_SHOW_ID  + " ORDER BY " + SeasonEntry.KEY_NUMBER;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Season season = new Season();
                season.setSeasonId(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_ID)));
                season.setShowId(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_SHOW_ID)));
                season.setSeasonNumber(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_NUMBER)));
                season.setSeasonCompleted(cursor.getInt(cursor.getColumnIndex(SeasonEntry.KEY_COMPLETED)));

                seasons.add(season);
            } while(cursor.moveToNext());
        }

        return seasons;
    }

    /**
     *  For this table we do not need to update anything apart the boolean.
     *
     *  Update the status of a season. A seson may be completed or uncompleted.
     */
    public int updatenSeason(Season season){
        ContentValues values = new ContentValues();
        values.put(SeasonEntry.KEY_COMPLETED, season.isSeasonCompleted());

        /** TODO: 07.11.15 Vérifier le "?" ci-dessous. S'assurer de la validité du return.
         *  de
         *  */
        return this.db.update(SeasonEntry.TABLE_SEASON, values, SeasonEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(season.getShowId()) });
    }


    /**
     * Delete a season
     */
    public void deleteSeason(long id){
        
        // TODO: 07.11.15 A vérifier avec Elsio.
    }

}
