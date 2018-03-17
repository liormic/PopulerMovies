package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lior on 3/16/18.
 */

public class MovieReviews {
    @SerializedName(value = "results")
    private ArrayList<MovieReviewObject> movieReviewsList;

    public ArrayList<MovieReviewObject> getReviews(){
        return movieReviewsList;
    }
}
