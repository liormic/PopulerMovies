package com.ely.populermovies.network;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lior on 2/20/18.
 */

public interface TmdbClient {
    @GET("3/movie/popular")
    Call <MovieResults> getResultsPopularMovies();

    @GET("3/movie/top_rated")
    Call <MovieResults> getResultsTopRated();

    @GET("3/movie/{movie_id}/reviews")
    Call <MovieReviews> getReviews(@Path("movie_id") String movie_id);

    @GET("3/movie/{movie_id}/videos")
    Call <MovieTrailers> getTrailers(@Path("movie_id") String movie_id);

}

