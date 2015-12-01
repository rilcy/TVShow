package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.ShowContract.*;
import devmobile.tvshow.db.object.Actor;


/**
 * Created by Elsio on 07.11.2015.
 */
public class CastingDataSource {

    private SQLiteDatabase db;
    private Context context;

    public CastingDataSource(Context context){
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /*
    // Add a new actor
    public long createCasting(Actor actor){
        long id;
        ContentValues values = new ContentValues();
        values.put(CastingEntry.KEY_LASTNAME, actor.getLastName());
        values.put(CastingEntry.KEY_FIRSTNAME, actor.getFirstName());
        id = this.db.insert(CastingEntry.TABLE_CASTING, null, values);

        return id;
    }
    */

    // Add a new actor
    public long createCasting(String firstName, String lastName){
        long id;

        ContentValues values = new ContentValues();
        values.put(CastingEntry.KEY_FIRSTNAME, firstName);
        values.put(CastingEntry.KEY_LASTNAME, lastName);
        id = this.db.insert(CastingEntry.TABLE_CASTING, null, values);

        return id;
    }


    /**
     * Get one actor
     */
    public Actor getActorById(int id){
        String sql = "SELECT * FROM " + CastingEntry.TABLE_CASTING +
                " WHERE " + CastingEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Actor actor = new Actor();
        actor.setIdActor(cursor.getInt(cursor.getColumnIndex(CastingEntry.KEY_ID)));
        actor.setLastName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_LASTNAME)));
        actor.setFirstName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_FIRSTNAME)));

        return actor;
    }


    /**
     * Get all Actors
     */
    public List<Actor> getAllActors(){
        List<Actor> actors = new ArrayList<Actor>();
        String sql = "SELECT * FROM " + CastingEntry.TABLE_CASTING + " ORDER BY " + CastingEntry.KEY_LASTNAME + "," + CastingEntry.KEY_FIRSTNAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Actor actor = new Actor();
                actor.setIdActor(cursor.getInt(cursor.getColumnIndex(CastingEntry.KEY_ID)));
                actor.setFirstName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_FIRSTNAME)));
                actor.setLastName(cursor.getString(cursor.getColumnIndex(CastingEntry.KEY_LASTNAME)));

                actors.add(actor);
            } while(cursor.moveToNext());
        }
        return actors;
    }


    /**
     *  Update the lastname and firstname of actor
     */
    public int updateActor(int actorId, String firstName, String lastName){
        ContentValues values = new ContentValues();
        values.put(CastingEntry.KEY_FIRSTNAME, firstName);
        values.put(CastingEntry.KEY_LASTNAME, lastName);

        return this.db.update(CastingEntry.TABLE_CASTING, values, CastingEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(actorId) });
    }
}
