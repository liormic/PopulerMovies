package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lior on 3/16/18.
 */

public class MovieReviewObject {


    @SerializedName(value = "content")
    private String movieReview;

    public String getMovieReview() {
        return movieReview;
    }
}
