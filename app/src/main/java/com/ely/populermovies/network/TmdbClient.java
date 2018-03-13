package com.ely.populermovies.network;

import com.ely.populermovies.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lior on 2/20/18.
 */

public interface TmdbClient {
    @GET("3/movie/popular")
    Call <MovieResults> getResultsPopularMovies();

    @GET("3/movie/top_rated")
    Call <MovieResults> getResultsTopRated();
}
