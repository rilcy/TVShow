package devmobile.tvshow.db.object;

import android.app.Activity;

import java.util.ArrayList;

import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;

/**
 * Created by Elsio on 30.11.2015.
 */
public class CastingEpisode {

    private int id;
    private int castingId;
    private int episodeId;

    // Constructeur vide
    public CastingEpisode(){}

    // Constructeur prenant comme paramètres l'Id du casting et l'Id de l'épisode
    public CastingEpisode(int castingId, int episodeId) {
        this.castingId = castingId;
        this.episodeId = episodeId;
    }

    // Getters et setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getCastingId() {return castingId;}

    public void setCastingId(int castingId) {this.castingId = castingId;}

    public int getEpisodeId() {return episodeId;}

    public void setEpisodeId(int episodeId) {this.episodeId = episodeId;}

    // Retire les acteurs liés à un épisode d'après l'Id de l'épisode
    public void deleteACasting(int episode_Id, Activity activity){
        CastingEpisodeDataSource castingds = new CastingEpisodeDataSource(activity);
        castingds.deleteAllCastingsForAnEpisode(episode_Id);
    }

    // Retire les acteurs liés à un épisode selon la liste en paramètres
    public void deleteAllCastings(ArrayList<CastingEpisode> list, Activity activity){
        CastingEpisodeDataSource castingds = new CastingEpisodeDataSource(activity);
        for(int i = 0; i<list.size(); i++){
            castingds.deleteAllCastingsForAnEpisode(list.get(i).getEpisodeId());
        }
    }
}
