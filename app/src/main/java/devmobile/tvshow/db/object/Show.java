package devmobile.tvshow.db.object;

import android.app.Activity;

import java.util.ArrayList;

import devmobile.tvshow.db.adapter.ShowDataSource;

/**
 * Created by rilcy on 17.10.15.
 */
public class Show {
    private int showId;
    private String showTitle;
    private String showStart;
    private String showEnd;
    private int showCompleted;
    private String showImage;
    //TODO voir si OK
    private Season[] seasons;

    // Empty constructor
    public Show(){}


    //Setters & getters

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

    public void deleteShow(int show_Id, Activity activity){
        ShowDataSource showsds = new ShowDataSource(activity);
        showsds.deleteShow(show_Id);
    }
}