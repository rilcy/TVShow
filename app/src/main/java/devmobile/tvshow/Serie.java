package devmobile.tvshow;

/**
 * Created by rilcy on 17.10.15.
 */
public class Serie {
    private String title;
    private Integer img;

    public Serie(String title, Integer img){
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImg() {
        return img;
    }
}
