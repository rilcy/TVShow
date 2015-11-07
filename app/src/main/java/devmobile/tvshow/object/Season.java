package devmobile.tvshow.object;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Season {

    private int seasonId;
    private String seasonNumber;
    private boolean seasonCompleted;
    private int showId;


    //Constructeur de l'objet Season lors de la création initiale.
    //Le boolean n'est pas ajouté car il doit être "FALSE" de base et ceci n'est pas maitrisé par le user
    public Season(int seasonId, String seasonNumber, int showId){
        this.seasonId = seasonId;
        this.seasonNumber = seasonNumber;
        this.seasonCompleted = false;
        this.showId = showId;
    }

    // Constructeur de l'objet Season
    public Season(int seasonId, String seasonNumber, boolean seasonCompleted, int showId){
        this.seasonId = seasonId;
        this.seasonNumber = seasonNumber;
        this.seasonCompleted = seasonCompleted;
        this.showId = showId;
    }

    //Getters & setters

    public boolean isSeasonCompleted() {
        return seasonCompleted;
    }

    public void setSeasonCompleted(boolean seasonCompleted) {
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

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
