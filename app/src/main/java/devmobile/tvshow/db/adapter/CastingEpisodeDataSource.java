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
     * Get actors by episodeId
     */
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

    /**
     * deletes reference between an actor and an episode
     */
    public void deleteEpisode(long id){

        // TODO: 07.11.15 A vérifier avec Elsio.
    }

}
