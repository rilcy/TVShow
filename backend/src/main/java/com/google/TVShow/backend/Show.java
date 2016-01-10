package com.google.TVShow.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Show {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int showId;
    private String showTitle;
    private String showStart;
    private String showEnd;
    private int showCompleted;
    private String showImage;
    private Season[] seasons;

    // Constructeur vide
    public Show(){}


    // Getters et setters de Show
    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowEnd() {
        return showEnd;
    }

    public void setShowEnd(String showEnd) {
        this.showEnd = showEnd;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getShowStart() {
        return showStart;
    }

    public void setShowStart(String showStart) {
        this.showStart = showStart;
    }

    public int isShowCompleted() {
        return showCompleted;
    }

    public void setShowCompleted(int showCompleted) {
        this.showCompleted = showCompleted;
    }

}