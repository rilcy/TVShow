package devmobile.tvshow.db.object;

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
}
