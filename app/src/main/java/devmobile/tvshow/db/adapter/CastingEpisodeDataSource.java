package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.ShowContract;
import devmobile.tvshow.db.ShowContract.*;
import devmobile.tvshow.db.object.Episode;


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
     * deletes reference between an actor and an episode
     */
    public void deleteEpisode(long id){

        // TODO: 07.11.15 A vérifier avec Elsio.
    }

}
