package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;


public class MovieReviewObject {


    @SerializedName(value = "content")
    private String movieReview;

    public String getMovieReview() {
        return movieReview;
    }
}
