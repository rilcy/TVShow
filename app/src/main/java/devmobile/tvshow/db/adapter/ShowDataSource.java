package devmobile.tvshow.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import devmobile.tvshow.db.ShowContract.*;
import devmobile.tvshow.db.object.*;
import devmobile.tvshow.db.SQLiteHelper;

/**
 * Created by Elsio on 07.11.2015.
 */
public class ShowDataSource {

    private SQLiteDatabase db;
    private Context context;

    public ShowDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert a new Show
     */
    public long createShow(Show show) {
        long id;
        ContentValues values = new ContentValues();
        values.put(ShowEntry.KEY_TITLE, show.getShowTitle());
        values.put(ShowEntry.KEY_START, show.getShowStart());
        values.put(ShowEntry.KEY_END, show.getShowEnd());
        values.put(ShowEntry.KEY_IMAGE, show.getShowImage());
        values.put(ShowEntry.KEY_COMPLETED, show.isShowCompleted());

        id = this.db.insert(ShowEntry.TABLE_SHOW, null, values);

        return id;
    }

    /**
     *  Find one show by ID
     */
    public Show getShowById(long id) {
        String sql = "SELECT * FROM " + ShowEntry.TABLE_SHOW +
                " WHERE " + ShowEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Show show = new Show();
        show.setShowId(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_ID)));
        show.setShowTitle(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_TITLE)));
        show.setShowStart(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_START)));
        show.setShowEnd(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_END)));
        show.setShowCompleted(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_COMPLETED)));
        show.setShowImage(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_IMAGE)));

        return show;
    }

    /**
     * Get all shows
     */
    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<Show>();
        String sql = "SELECT * FROM " + ShowEntry.TABLE_SHOW + " ORDER BY " + ShowEntry.KEY_TITLE;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Show show = new Show();
                show.setShowId(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_ID)));
                show.setShowTitle(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_TITLE)));
                show.setShowStart(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_START)));
                show.setShowEnd(cursor.getString(cursor.getColumnIndex(ShowEntry.KEY_END)));
                show.setShowCompleted(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_COMPLETED)));
                show.setShowImage(cursor.getInt(cursor.getColumnIndex(ShowEntry.KEY_IMAGE)));
                shows.add(show);
            } while(cursor.moveToNext());
        }

        return shows;
    }

    /**
     * Update one show
     */
    public int updateShow(Show show) {
        ContentValues values = new ContentValues();
        values.put(ShowEntry.KEY_TITLE, show.getShowTitle());
        values.put(ShowEntry.KEY_START, show.getShowStart());
        values.put(ShowEntry.KEY_END, show.getShowEnd());
        values.put(ShowEntry.KEY_COMPLETED, show.isShowCompleted());
        values.put(ShowEntry.KEY_IMAGE, show.getShowImage());

        return this.db.update(ShowEntry.TABLE_SHOW, values, ShowEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(show.getShowId()) });
    }

    /**
     * Delete one show
     */

    //TODO : A faire Delete one show
}