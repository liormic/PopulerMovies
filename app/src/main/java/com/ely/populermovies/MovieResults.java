package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lior on 2/20/18.
 */

public class MovieResults {

        @SerializedName(value = "results")
        private List<MovieObject> results;

        public List<MovieObject> getResults() {
            return results;

        }


        public void setMovieResults(List<MovieObject> movieList) {
            this.results = movieList;
        }

}
