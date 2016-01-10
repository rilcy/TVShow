package com.google.TVShow.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class CastingEpisode {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
}
