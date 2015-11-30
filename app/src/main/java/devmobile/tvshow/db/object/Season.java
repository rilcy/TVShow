package devmobile.tvshow.db.object;

import android.app.Activity;

import java.util.ArrayList;

import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Season {

    private int seasonId;
    private int seasonNumber;
    private int seasonCompleted;
    private int showId;

    private String type;

    // Empty constructor
    public Season(){}


    // *******************
    public Season(String type){
        this.type = type;
    }

    // *******************


    //Getters & setters
    public int isSeasonCompleted() {
        return seasonCompleted;
    }

    public void setSeasonCompleted(int seasonCompleted) {
        this.seasonCompleted = seasonCompleted;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void deleteASeason(int season_Id, Activity activity){
        SeasonDataSource seasonds = new SeasonDataSource(activity);
        seasonds.deleteSeason(season_Id);
    }

    public void deleteAllSeasons(ArrayList<Season> list, Activity activity){
        ShowDataSource showsds = new ShowDataSource(activity);
        for(int i = 0; i<list.size(); i++){
            showsds.deleteShow(list.get(i).getSeasonId());
        }
    }
}
