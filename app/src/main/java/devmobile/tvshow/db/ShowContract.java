package devmobile.tvshow.db;

import android.provider.BaseColumns;

import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;

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

        //Table Show create statement
        public static final String CREATE_TABLE_SHOW = "CREATE TABLE "
                + TABLE_SHOW + "("
                + ShowEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + ShowEntry.KEY_TITLE + " TEXT, "
                + ShowEntry.KEY_START + " INTEGER, "
                + ShowEntry.KEY_END + " INTEGER, "
                + ShowEntry.KEY_COMPLETED + " BOOLEAN, "
                + ShowEntry.KEY_IMAGE + " TEXT "
                + ");";
    }

    public static abstract class SeasonEntry implements BaseColumns {
        public static final String TABLE_SEASON = "season";

        //Season Column names
        public static final String KEY_ID = "season_id";
        public static final String KEY_NUMBER = "season_number";
        public static final String KEY_COMPLETED = "season_completed";
        public static final String KEY_SHOW_ID = "season_show_id";

        //Table Season create statement
        public static final String CREATE_TABLE_SEASON = "CREATE TABLE "
                + TABLE_SEASON + "("
                + SeasonEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + SeasonEntry.KEY_NUMBER + " INTEGER, "
                + SeasonEntry.KEY_COMPLETED + " BOOLEAN, "
                + SeasonEntry.KEY_SHOW_ID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_SHOW_ID + ") REFERENCES " + ShowEntry.TABLE_SHOW + " (" + KEY_ID + ") "
                + ");";
    }

    public static abstract class EpisodeEntry implements BaseColumns {
        public static final String TABLE_EPISODE = "episode";

        //Episode Column names
        public static final String KEY_ID = "episode_id";
        public static final String KEY_TITLE = "episode_title";
        public static final String KEY_NUMBER = "episode_number";
        public static final String KEY_COMPLETED = "episode_completed";
        public static final String KEY_SEASON_ID = "episode_season_id";

        //Table Episode create statement
        public static final String CREATE_TABLE_EPISODE = "CREATE TABLE "
                + TABLE_EPISODE + "("
                + EpisodeEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + EpisodeEntry.KEY_TITLE + " TEXT, "
                + EpisodeEntry.KEY_NUMBER + " INTEGER, "
                + EpisodeEntry.KEY_COMPLETED + " BOOLEAN, "
                + EpisodeEntry.KEY_SEASON_ID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_SEASON_ID + ") REFERENCES " + SeasonEntry.TABLE_SEASON + " (" + KEY_ID + ") "
                + ");";
    }

    public static abstract class CastingEntry implements BaseColumns {
        public static final String TABLE_CASTING = "casting";

        //Casting Column names
        public static final String KEY_ID = "casting_id";
        public static final String KEY_FIRSTNAME = "casting_firstname";
        public static final String KEY_LASTNAME = "casting_lastname";


        //Table Casting create statement
        public static final String CREATE_TABLE_CASTING = "CREATE TABLE "
                + TABLE_CASTING + "("
                + CastingEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + CastingEntry.KEY_FIRSTNAME + " TEXT, "
                + CastingEntry.KEY_LASTNAME + " TEXT"
                + ");";
    }

    public static abstract class CastingEpisodeEntry implements BaseColumns {
        public static final String TABLE_CASTING_EPISODE = "casting_episode";

        //CastingEpisode Column names
        public static final String KEY_ID = "casting_episode_id";
        public static final String KEY_CASTING_ID = "casting_id";
        public static final String KEY_EPISODE_ID = "episode_id";


        //Table CastingEpisode create statement
        public static final String CREATE_TABLE_CASTING_EPISODE = "CREATE TABLE "
                + TABLE_CASTING_EPISODE + "("
                + CastingEpisodeEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + CastingEpisodeEntry.KEY_CASTING_ID + " INTEGER, "
                + CastingEpisodeEntry.KEY_EPISODE_ID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_CASTING_ID + ") REFERENCES " + CastingEntry.TABLE_CASTING + " (" + KEY_ID + ") "
                + "FOREIGN KEY (" + KEY_EPISODE_ID + ") REFERENCES " + EpisodeEntry.TABLE_EPISODE + " (" + KEY_ID + ") "
                + ");";
    }
}