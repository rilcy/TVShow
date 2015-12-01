package devmobile.tvshow.db.object;

import android.app.Activity;

import java.util.ArrayList;

import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;

/**
 * Created by Elsio on 28.10.2015.
 */
public class Episode {

    private int episodeID;
    private String episodeTitle;
    private int episodeNumber;
    private int seasonID;
    private int episodeCompleted;

    // Constructeur vide
    public Episode(){}

    //Constructeur de l'objet Episode lors de la création initiale.
    //Le boolean n'est pas ajouté car il doit être "FALSE" de base et ceci n'est pas maitrisé par le user
    public Episode(int episodeID, String episodeTitle, int episodeNumber, int seasonID){
        this.episodeID = episodeID;
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
        this.seasonID = seasonID;
        this.episodeCompleted = 0;
    }

    // Constructeur de l'objet Show avec boolean lors de la création de l'activité avec des données provenant de la DB.
    public Episode(int episodeID, String episodeTitle, int episodeNumber, int seasonID, boolean episodeCompleted){
        this.episodeID = episodeID;
        this.episodeTitle = episodeTitle;
        this.episodeNumber = episodeNumber;
        this.seasonID = seasonID;
        this.episodeCompleted = 0;
    }

    // Getters et setters d'Episode
    public int isEpisodeCompleted() {
        return episodeCompleted;
    }

    public void setEpisodeCompleted(int episodeCompleted) {
        this.episodeCompleted = episodeCompleted;
    }

    public int getEpisodeID() {
        return episodeID;
    }

    public void setEpisodeID(int episodeID) {
        this.episodeID = episodeID;
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

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    // Permet de supprimer un épisode d'après son Id
    public void deleteAnEpisode(int episode_Id, Activity activity){
        EpisodeDataSource episodeds = new EpisodeDataSource(activity);
        episodeds.deleteEpisode(episode_Id);
    }

    // Permet de supprimer tous les épisodes d'une liste entrée en paramètres
    public void deleteAllEpisodes(ArrayList<Episode> list, Activity activity){
        EpisodeDataSource episodeds = new EpisodeDataSource(activity);
        for(int i = 0; i<list.size(); i++){
            episodeds.deleteEpisode(list.get(i).getEpisodeID());
        }
    }
}
