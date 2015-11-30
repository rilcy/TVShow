package devmobile.tvshow.db.object;

/**
 * Created by Elsio on 30.11.2015.
 */
public class CastingEpisode {

    private int id;
    private int castingId;
    private int episodeId;

    // empty constructor
    public CastingEpisode(){}

    public CastingEpisode(int castingId, int episodeId) {
        this.castingId = castingId;
        this.episodeId = episodeId;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getCastingId() {return castingId;}

    public void setCastingId(int castingId) {this.castingId = castingId;}

    public int getEpisodeId() {return episodeId;}

    public void setEpisodeId(int episodeId) {this.episodeId = episodeId;}
}
