package com.ely.populermovies.utils;

/**
 * Created by lior on 2/20/18.
 */

@SuppressWarnings("ALL")
public class Api {
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185";

    public static String getBaseUrlPoster() {
        return BASE_URL_IMAGE;
    }
}
