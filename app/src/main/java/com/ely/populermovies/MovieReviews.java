package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class MovieReviews {
    @SerializedName(value = "results")
    private ArrayList<MovieReviewObject> movieReviewsList;

    public ArrayList<MovieReviewObject> getReviews(){
        return movieReviewsList;
    }
}
