package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lior on 2/20/18.
 */

public class MovieResults {

        @SerializedName(value = "results")
        private ArrayList<MovieObject> results;

        public ArrayList<MovieObject> getResults() {
            return results;

        }


        public void setMovieResults(ArrayList<MovieObject> movieList) {
            this.results = movieList;
        }

}
