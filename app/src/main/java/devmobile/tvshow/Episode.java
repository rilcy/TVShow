package devmobile.tvshow;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Episode {
    private String episodeNumber;
    private String episodeTitle;

    public Episode(String episodeNumber, String episodeTitle){
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
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

}
