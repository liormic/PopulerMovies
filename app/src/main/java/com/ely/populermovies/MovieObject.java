package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lior on 2/20/18.
 */

public class MovieObject {

    private String title;
    private String id;
    @SerializedName(value = "poster_path")
    private String posterPath;
    @SerializedName(value = "release_date")
    private String releaseDate;
    @SerializedName(value = "vote_average")
    private double averageVote;


    public double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(double averageVote) {
        this.averageVote = averageVote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    private String backdrop_path;
    private String vote_average;
}
