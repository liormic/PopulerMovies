package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lior on 3/15/2018.
 */

public class MovieTrailerObject {

    @SerializedName(value = "key")
    private java.lang.String movieTrailerKey;
    @SerializedName(value="site")
    private java.lang.String movieTrailerSite;

    public java.lang.String getMovieTrailerId() {
        return movieTrailerKey;
    }

    public java.lang.String getMovieTrailerSite() {
        return movieTrailerSite;
    }
}
