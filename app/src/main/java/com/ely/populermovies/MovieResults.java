package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class MovieResults {

        @SerializedName(value = "results")
        private ArrayList<MovieObject> movieObjectResults;


        public ArrayList<MovieObject> getMovieObjectResults() {
            return movieObjectResults;

        }


    /**
     * Created by lior on 3/16/18.
     */

    public static class MovieReview {
    }
}
