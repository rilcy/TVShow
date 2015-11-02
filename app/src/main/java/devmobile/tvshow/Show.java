package devmobile.tvshow;

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

    public Show(String title, Integer showImage){
        this.showTitle = title;
        this.showImage = showImage;
    }

    public String getTitle() {
        return showTitle;
    }

    public void setImg(Integer img) {
        this.showImage = img;
    }

    public void setTitle(String title) {
        this.showTitle = title;
    }

    public Integer getImg() {
        return showImage;
    }
}
