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

    // Permet d'ajouter une nouvelle saison à un show
    public long createSeason(int show_Id, int numSeasons){
        long id;
        ContentValues values = new ContentValues();

        int numRows = numSeasons + 1;

        values.put(SeasonEntry.KEY_NUMBER, numRows);
        values.put(SeasonEntry.KEY_COMPLETED, 0);
        values.put(SeasonEntry.KEY_SHOW_ID, show_Id);
        id = this.db.insert(SeasonEntry.TABLE_SEASON, null, values);

        return id;
    }

    // Permet d'obtenir une saison d'après son Id
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

    // Obteniri toutes les saisons d'après l'Id du show
    public List<Season> getAllSeasons(int show_Id){
        List<Season> seasons = new ArrayList<Season>();
        String sql = "SELECT * FROM " + SeasonEntry.TABLE_SEASON + " WHERE " + SeasonEntry.KEY_SHOW_ID + " = " + show_Id
                + " ORDER BY " + SeasonEntry.KEY_NUMBER;
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

    // Pour cette table, le seul update nécessaire est le statut
    // Une saison peut être complétée ou non
    public int updateSeason(Season season){
        ContentValues values = new ContentValues();
        values.put(SeasonEntry.KEY_COMPLETED, season.isSeasonCompleted());

        return this.db.update(SeasonEntry.TABLE_SEASON, values, SeasonEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(season.getSeasonId()) });
    }

    // Supprime une saison d'après son Id
    public void deleteSeason(long seasonId){
        this.db.delete(SeasonEntry.TABLE_SEASON, SeasonEntry.KEY_ID + " = ?",
                new String[]{String.valueOf(seasonId)});
    }

    //Supprime toutes les saisons d'un show d'après l'Id du show
    public void deleteAllSeasonsByShowId(long showId){
        this.db.delete(SeasonEntry.TABLE_SEASON, SeasonEntry.KEY_SHOW_ID + " = ?",
                new String[]{String.valueOf(showId)});
    }

}
