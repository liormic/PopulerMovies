package com.ely.populermovies.network;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lior on 2/20/18.
 */

public interface TmdbClient {
    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Call <MovieResults> getResults();

}
