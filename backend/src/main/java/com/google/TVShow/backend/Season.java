package com.google.TVShow.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int seasonId;
    private int seasonNumber;
    private int seasonCompleted;
    private int showId;

    private String type;

    // Constructeur vide
    public Season(){}

    public Season(String type){
        this.type = type;
    }

    //Getters et setters de Season
    public int isSeasonCompleted() {
        return seasonCompleted;
    }

    public void setSeasonCompleted(int seasonCompleted) {
        this.seasonCompleted = seasonCompleted;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
