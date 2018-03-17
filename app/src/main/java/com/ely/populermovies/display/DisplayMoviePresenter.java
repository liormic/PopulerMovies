package com.ely.populermovies.display;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.network.TmdbClient;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by lior on 2/21/18.
 */

@SuppressWarnings("ALL")
interface DisplayMoviePresenter {
    void setView(DisplayMovieView displayMovieView);
    void setApiCall(java.lang.String apiCallType, java.lang.String param);
    TmdbClient setupRetrofitClient();
     void executeAPiCall( Call<MovieResults> apiCall);
    void executeAPiCallForTrailers( Call<MovieTrailers> apiCall);
    void executeAPiCallForReviews(Call<MovieReviews> callForReviews);
    void convertTrailersIntoStrings(ArrayList<MovieTrailerObject> movieTrailerObjectArrayList, ArrayList<MovieReviewObject> movieReviewObjectArrayList);
}
