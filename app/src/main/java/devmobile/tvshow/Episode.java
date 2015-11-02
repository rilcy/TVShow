package devmobile.tvshow;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Episode {

    private int episodeId;
    private String episodeTitle;
    private String episodeNumber;
    private int seasonId;
    private boolean episodeCompleted;
    private String episodeInfo;
    private Integer img;

    public Episode(String episodeNumber, String episodeTitle){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
    }

    public Episode(String episodeNumber, String episodeTitle, String episodeInfo, Integer img){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.episodeInfo = episodeInfo;
        this.img = img;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeTitle() { return episodeTitle; }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getEpisodeInfo() {
        return episodeInfo;
    }

    public void setEpisodeInfo(String episodeInfo) {
        this.episodeInfo = episodeInfo;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
