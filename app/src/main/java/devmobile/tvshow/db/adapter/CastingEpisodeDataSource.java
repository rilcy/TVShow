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

    // Ajoute un acteur depuis la liste générale à un épisode
    public long createActorEpisode(long actor_id, long episode_id){
        ContentValues values = new ContentValues();
        values.put(CastingEpisodeEntry.KEY_CASTING_ID, actor_id);
        values.put(CastingEpisodeEntry.KEY_EPISODE_ID, episode_id);

        return this.db.insert(CastingEpisodeEntry.TABLE_CASTING_EPISODE, null, values);
    }

    // Obtenir la liste de tous les acteurs liés au casting d'un épisode
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

    // Retire tous les acteurs liés à un épisode d'après l'Id de l'épisode (en cas de suppression d'un épisode)
    // L'acteur reste tout de même dans la liste générale d'acteurs
    public void deleteAllCastingsForAnEpisode(long idEpisode){
        this.db.delete(CastingEpisodeEntry.TABLE_CASTING_EPISODE, CastingEpisodeEntry.KEY_EPISODE_ID + " = ?",
                new String[]{String.valueOf(idEpisode)});
    }

    // Retire un acteur lié à un épisode
    // L'acteur reste tout de même dans la liste générale d'acteurs
    public void deleteCastingForActor(int episode_id, int actor_id) {
        this.db.delete(CastingEpisodeEntry.TABLE_CASTING_EPISODE, CastingEpisodeEntry.KEY_EPISODE_ID + " = ?" + " AND " + CastingEpisodeEntry.KEY_CASTING_ID + " = ?",
                new String[]{String.valueOf(episode_id), String.valueOf(actor_id)});
    }
}
