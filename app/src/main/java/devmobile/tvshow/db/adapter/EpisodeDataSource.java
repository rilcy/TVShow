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
import devmobile.tvshow.db.object.Season;

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

    // Permet d'ajouter un épisode à la saison d'une série
    public long createEpisode(int seasonId, int numEpisodes, String episodeTitle){
        long id;
        ContentValues values = new ContentValues();

        long numRows = numEpisodes + 1;

        values.put(EpisodeEntry.KEY_COMPLETED, 0); // CANNOT BE WATCHED HAS TO BE FALSE.
        values.put(EpisodeEntry.KEY_SEASON_ID, seasonId);
        values.put(EpisodeEntry.KEY_NUMBER, numRows);
        values.put(EpisodeEntry.KEY_TITLE, episodeTitle);
        id = this.db.insert(EpisodeEntry.TABLE_EPISODE, null, values);

        return id;
    }

    // Obtenir un épisode d'après son Id
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

    // Permet d'obtenir le dernier épisode
    public Episode getNextEpisode(int showID, ArrayList<Season> seasons){
        String sql = "SELECT * FROM " + EpisodeEntry.TABLE_EPISODE + " INNER JOIN " + " WHERE " + EpisodeEntry.KEY_SEASON_ID + " = " + showID
                + " ORDER BY " + EpisodeEntry.KEY_NUMBER + " ASC LIMIT 1";

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

    // Obtenir la liste de tous les épisodes liés à une saison d'après l'Id de la saison
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


    // Deux types d'updates pour cette table :

    //...met à jour le statut d'un épisode. Un épisode peut être vu ou non vu
    public int updateEpisodeIfWatched(Episode episode){
        ContentValues values = new ContentValues();
        values.put(EpisodeEntry.KEY_COMPLETED, episode.isEpisodeCompleted());


        return this.db.update(EpisodeEntry.TABLE_EPISODE, values, EpisodeEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(episode.getEpisodeID()) });
    }

    // ...met à jour les données d'un épisode existant
    public int updateEpisode(int episodeId, String episodeTitle){
        ContentValues values = new ContentValues();
        values.put(EpisodeEntry.KEY_TITLE, episodeTitle);

        return this.db.update(EpisodeEntry.TABLE_EPISODE, values, EpisodeEntry.KEY_ID + " = ? ",
                new String[] { String.valueOf(episodeId) });
    }

    // Supprime un épisode d'après son Id
    public void deleteEpisode(long id){
        this.db.delete(EpisodeEntry.TABLE_EPISODE, EpisodeEntry.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // Supprime tous les épisodes d'une saison d'après l'Id de la saison
    public void deleteAllEpisodesBySeasonId(long seasonId){
        this.db.delete(EpisodeEntry.TABLE_EPISODE, EpisodeEntry.KEY_SEASON_ID + " = ?",
                new String[]{String.valueOf(seasonId)});
    }

}
