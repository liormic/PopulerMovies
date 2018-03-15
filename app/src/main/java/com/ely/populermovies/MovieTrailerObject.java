package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lior on 3/15/2018.
 */

public class MovieTrailerObject {

    @SerializedName(value = "id")
    private String movieTrailerId;
    @SerializedName(value="site")
    private String movieTrailerSite;

    public String getMovieTrailerId() {
        return movieTrailerId;
    }

    public String getMovieTrailerSite() {
        return movieTrailerSite;
    }
}
