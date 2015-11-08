package devmobile.tvshow.db.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.ShowContract;

/**
 * Created by Elsio on 07.11.2015.
 */
public class CastingEpisodeDataSource {

    private SQLiteDatabase db;
    private Context context;

    public CastingEpisodeDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }
}
