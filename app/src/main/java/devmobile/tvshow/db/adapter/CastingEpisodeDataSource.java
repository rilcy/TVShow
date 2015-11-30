package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.ShowContract;
import devmobile.tvshow.db.ShowContract.*;
import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;


/**
 * Created by Elsio on 07.11.2015.
 */
public class CastingEpisodeDataSource {
    private SQLiteDatabase db;

    public CastingEpisodeDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * inserts a reference between an actor and an episode
     */
    public long createActorEpisode(long actor_id, long episode_id){
        ContentValues values = new ContentValues();
        values.put(CastingEpisodeEntry.KEY_CASTING_ID, actor_id);
        values.put(CastingEpisodeEntry.KEY_EPISODE_ID, episode_id);

        return this.db.insert(CastingEpisodeEntry.TABLE_CASTING_EPISODE, null, values);
    }

    /**
     * Get all the actors by episodeId
     */
    /*
    public List<Actor> getActorsByEpisodeId(int id){
        List<Actor> actors = new ArrayList<Actor>();
        String sql = "SELECT * FROM " + CastingEpisodeEntry.TABLE_CASTING_EPISODE +
                " WHERE " + CastingEpisodeEntry.KEY_EPISODE_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {

                Actor actor = new Actor();
                actor.setIdActor(cursor.getInt(cursor.getColumnIndex(CastingEntry.KEY_ID)));
                actor.setFirstName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_FIRSTNAME)));
                actor.setLastName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_LASTNAME)));

                actors.add(actor);
            } while (cursor.moveToNext()) ;
        }
        return actors;

    }
    */

    public List<CastingEpisode> getActorsIdByEpisodeId(int id) {
        List<CastingEpisode> castingEpisodes = new ArrayList<CastingEpisode>();
        String sql = "SELECT * FROM " + CastingEpisodeEntry.TABLE_CASTING_EPISODE +
                " WHERE " + CastingEpisodeEntry.KEY_EPISODE_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                CastingEpisode castingEpisode = new CastingEpisode();
                castingEpisode.setId(cursor.getInt(cursor.getColumnIndex(CastingEpisodeEntry.KEY_ID)));
                castingEpisode.setCastingId(cursor.getInt(cursor.getColumnIndex(CastingEpisodeEntry.KEY_CASTING_ID)));
                castingEpisode.setEpisodeId(cursor.getInt(cursor.getColumnIndex(CastingEpisodeEntry.KEY_EPISODE_ID)));

                castingEpisodes.add(castingEpisode);
            } while (cursor.moveToNext()) ;
        }
        return castingEpisodes;

    }

    /**
     * delete all casting for an Episode
     */
    public void deleteAllCastingsForAnEpisode(long idEpisode){
        this.db.delete(CastingEpisodeEntry.TABLE_CASTING_EPISODE, CastingEpisodeEntry.KEY_EPISODE_ID + " = ?",
                new String[]{String.valueOf(idEpisode)});
    }

    public void deleteCastingForActor(int episode_id, int actor_id) {
        this.db.delete(CastingEpisodeEntry.TABLE_CASTING_EPISODE, CastingEpisodeEntry.KEY_EPISODE_ID + " = ?" + " AND " + CastingEpisodeEntry.KEY_CASTING_ID + " = ?",
                new String[]{String.valueOf(episode_id), String.valueOf(actor_id)});
    }
}
