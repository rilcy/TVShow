package devmobile.tvshow;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Season {

    private int seasonId;
    private String seasonNumber;
    private boolean seasonCompleted;
    private int showId;

    public Season(String seasonNumber){
        this.seasonNumber = seasonNumber;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

}
