package devmobile.tvshow.object;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Episode {

    private int episodeID;
    private String episodeTitle;
    private int episodeNumber;
    private int seasonID;
    private boolean episodeCompleted;

    // Supprimer ces deux attributs
    private String episodeInfo;
    private Integer img;


    // CONSTRUCTEUR A SUPPRIMER
    public Episode(int episodeNumber, String episodeTitle){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
    }

    // CONSTRUCTEUR A SUPPRIMER
    public Episode(int episodeNumber, String episodeTitle, String episodeInfo, Integer img){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.episodeInfo = episodeInfo;
        this.img = img;
    }

    //Constructeur de l'objet Episode lors de la création initiale.
    //Le boolean n'est pas ajouté car il doit être "FALSE" de base et ceci n'est pas maitrisé par le user
    public Episode(int episodeID, String episodeTitle, int episodeNumber, int seasonID){
        this.episodeID = episodeID;
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
        this.seasonID = seasonID;
        this.episodeCompleted = false;
    }

    // Constructeur de l'objet Show avec boolean lors de la création de l'activité avec des données provenant de la DB.
    public Episode(int episodeID, String episodeTitle, int episodeNumber, int seasonID, boolean episodeCompleted){
        this.episodeID = episodeID;
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
        this.seasonID = seasonID;
        this.episodeCompleted = episodeCompleted;
    }

    //Getters & setters


    public boolean isEpisodeCompleted() {
        return episodeCompleted;
    }

    public void setEpisodeCompleted(boolean episodeCompleted) {
        this.episodeCompleted = episodeCompleted;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
    }

    public String getEpisodeInfo() {
        return episodeInfo;
    }

    public void setEpisodeInfo(String episodeInfo) {
        this.episodeInfo = episodeInfo;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }
}
