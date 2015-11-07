package devmobile.tvshow.object;

/**
 * Created by rilcy on 17.10.15.
 */
public class Show {
    private int showId;
    private String showTitle;
    private int showStart;
    private int showEnd;
    private boolean showCompleted;
    private Integer showImage;

    // CONSTRUCTEUR A SUPPRIMER
    public Show(String title, Integer showImage){
        this.showTitle = title;
        this.showImage = showImage;
    }


        //Constructeur de l'objet Show lors de la création initiale.
        //Le boolean n'est pas ajouté car il doit être "FALSE" de base et ceci n'est pas maitrisé par le user
    public Show(int showId, String showTitle, int showStart, int showEnd, Integer showImage){
        this.showId = showId;
        this.showTitle = showTitle;
        this.showStart = showStart;
        this.showEnd = showEnd;
        this.showCompleted = false;
        this.showImage = showImage;
    }

    // Constructeur de l'objet Show avec boolean
    public Show(int showId, String showTitle, int showStart, int showEnd, boolean showCompleted, Integer showImage){
        this.showId = showId;
        this.showTitle = showTitle;
        this.showStart = showStart;
        this.showEnd = showEnd;
        this.showCompleted = showCompleted;
        this.showImage = showImage;
    }



    //Setters & getters

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getShowEnd() {
        return showEnd;
    }

    public void setShowEnd(int showEnd) {
        this.showEnd = showEnd;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public Integer getShowImage() {
        return showImage;
    }

    public void setShowImage(Integer showImage) {
        this.showImage = showImage;
    }

    public int getShowStart() {
        return showStart;
    }

    public void setShowStart(int showStart) {
        this.showStart = showStart;
    }

    public boolean isShowCompleted() {
        return showCompleted;
    }

    public void setShowCompleted(boolean showCompleted) {
        this.showCompleted = showCompleted;
    }
}
