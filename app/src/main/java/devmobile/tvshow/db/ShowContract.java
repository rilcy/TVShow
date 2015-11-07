package devmobile.tvshow.db;

import android.provider.BaseColumns;

/**
 * Created by Elsio on 07.11.2015.
 */
public final class ShowContract {

    public ShowContract() {

    }

    public static abstract class ShowEntry implements BaseColumns {
        public static final String TABLE_SHOW = "show";

        //Show Column names
        public static final String KEY_ID = "show_id";
        public static final String KEY_TITLE = "show_title";
        public static final String KEY_START = "show_start";
        public static final String KEY_END = "show_end";
        public static final String KEY_COMPLETED = "show_completed";
        public static final String KEY_IMAGE = "show_image";

        //Table person create statement
        public static final String CREATE_TABLE_SHOW = "CREATE TABLE "
                + TABLE_SHOW + "("
                + ShowEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + ShowEntry.KEY_TITLE + " TEXT, "
                + ShowEntry.KEY_START + " INTEGER, "
                + ShowEntry.KEY_END + " INTEGER, "
                + ShowEntry.KEY_COMPLETED + " BOOLEAN"
                + ShowEntry.KEY_IMAGE + " TEXT "
                + ");";
        
    }
}
