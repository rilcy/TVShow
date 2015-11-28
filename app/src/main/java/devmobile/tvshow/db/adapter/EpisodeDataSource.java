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
import devmobile.tvshow.db.object.Episode;

/**
 * Created by Elsio on 07.11.2015.
 */
public class EpisodeDataSource {

    private SQLiteDatabase db;
    private Context context;

    public EpisodeDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }


    /**
     * Add a new episode
     */
    public long createEpisode(Episode episode){
        long id;
        ContentValues values = new ContentValues();
        long numRows = (int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM TABLE_EPISODE WHERE KEY_SEASON_ID = " + episode.getSeasonID(), null);
        numRows += 1;
        values.put(EpisodeEntry.KEY_NUMBER, numRows);
        values.put(EpisodeEntry.KEY_COMPLETED, 0); // CANNOT BE WATCHED HAS TO BE FALSE.
        values.put(EpisodeEntry.KEY_SEASON_ID, episode.getSeasonID());
        values.put(EpisodeEntry.KEY_NUMBER, episode.getEpisodeNumber());
        values.put(EpisodeEntry.KEY_TITLE, episode.getEpisodeTitle());
        id = this.db.insert(EpisodeEntry.TABLE_EPISODE, null, values);

        return id;
    }

    /**
     * Find one episode by Id
     */
    public Episode getEpisodeById(long id){
        String sql = "SELECT * FROM " + EpisodeEntry.TABLE_EPISODE +
                " WHERE " + EpisodeEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Episode episode = new Episode();
        episode.setEpisodeID(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_ID)));
        episode.setEpisodeTitle(cursor.getString(cursor.getColumnIndex(EpisodeEntry.KEY_TITLE)));
        episode.setEpisodeCompleted(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_COMPLETED)));
        episode.setEpisodeNumber(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_NUMBER)));
        episode.setSeasonID(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_SEASON_ID)));


        return episode;
    }


    /**
     * Get all Episode
     */
    public List<Episode> getAllEpisodes(int seasonID){
        List<Episode> episodes = new ArrayList<Episode>();
        String sql = "SELECT * FROM " + EpisodeEntry.TABLE_EPISODE + " WHERE " + EpisodeEntry.KEY_SEASON_ID + " = " + seasonID
                + " ORDER BY " + EpisodeEntry.KEY_NUMBER;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Episode episode = new Episode();
                episode.setEpisodeID(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_ID)));
                episode.setEpisodeTitle(cursor.getString(cursor.getColumnIndex(EpisodeEntry.KEY_TITLE)));
                episode.setEpisodeCompleted(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_COMPLETED)));
                episode.setEpisodeNumber(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_NUMBER)));
                episode.setSeasonID(cursor.getInt(cursor.getColumnIndex(EpisodeEntry.KEY_SEASON_ID)));

                episodes.add(episode);
            } while(cursor.moveToNext());
        }

        return episodes;
    }


    /**
     *  For this table we have two kinds of update :
     *   - if an episode has (not) been watched.
     *   - if another data was modified
     */

    /**
     *  Update the status of an episode. An episode may be watched or not be watched.
     */
    public int updateEpisodeIfWatched(Episode episode){
        ContentValues values = new ContentValues();
        values.put(EpisodeEntry.KEY_COMPLETED, episode.isEpisodeCompleted());

        /** TODO: 07.11.15 Vérifier le "?" ci-dessous. S'assurer de la validité du return. Retiré le ? après le =, vérifier que tout est OK
         *
         *  */
        return this.db.update(EpisodeEntry.TABLE_EPISODE, values, EpisodeEntry.KEY_ID + " = ",
                new String[] { String.valueOf(episode.getEpisodeID()) });
    }

    /**
     *  Update datas of an episode.
     */
    public int updateEpisode(Episode episode){
        ContentValues values = new ContentValues();
        values.put(EpisodeEntry.KEY_TITLE, episode.getEpisodeTitle());
        values.put(EpisodeEntry.KEY_NUMBER, episode.getEpisodeNumber());

        /** TODO: 07.11.15 Vérifier le "?" ci-dessous. S'assurer de la validité du return. Retiré le ? après le =, vérifier que tout est OK
         *
         *  */
        return this.db.update(EpisodeEntry.TABLE_EPISODE, values, EpisodeEntry.KEY_ID + " = ",
                new String[] { String.valueOf(episode.getEpisodeID()) });
    }

    /**
     * Delete a episode
     */
    public void deleteEpisode(long id){

        // TODO: 07.11.15 A vérifier avec Elsio.
        this.db.delete(EpisodeEntry.TABLE_EPISODE, EpisodeEntry.KEY_ID + " = ", new String[] {String.valueOf(id)});
    }

}
