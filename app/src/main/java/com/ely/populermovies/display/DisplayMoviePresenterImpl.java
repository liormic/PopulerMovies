package com.ely.populermovies.display;

import android.util.Log;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.network.CallInterceptor;
import com.ely.populermovies.network.Module;
import com.ely.populermovies.network.TmdbClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMoviePresenterImpl implements DisplayMoviePresenter {

    private  DisplayMovieView view;


    public DisplayMoviePresenterImpl() {

    }

    @Override
    public void setView(DisplayMovieView view) {
        this.view = view;
        //displayMovies();
    }

    @Override
    public void setApiCall(java.lang.String apiCallType, java.lang.String movieId ) {

        Call<MovieResults> callForPopularMovies = setupRetrofitClient().getResultsPopularMovies();
        Call<MovieResults> callForTopRatedMovies = setupRetrofitClient().getResultsTopRated();
        Call<MovieTrailers> callForTrailers = setupRetrofitClient().getTrailers(movieId);
        Call<MovieReviews> callForReviews = setupRetrofitClient().getReviews(movieId);

        if (apiCallType.equals("Popular Movies")) {
            executeAPiCall(callForPopularMovies);
        }else if (apiCallType.equals("Trailers")) {
            executeAPiCallForTrailers(callForTrailers);
        } else if(apiCallType.equals("Reviews")){
            executeAPiCallForReviews(callForReviews);

        } else {
            executeAPiCall(callForTopRatedMovies);
        }


    }

    @Override
    public void executeAPiCall( Call<MovieResults> apiCall){
        view.setProgressBar(true);
            apiCall.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    MovieResults movieResults;
                    movieResults = response.body();
                    view.setProgressBar(false);
                    view.showMovies(movieResults);

                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                    Log.e(TAG, "Error fetching items");
                }
            });

    }


    @Override
    public void executeAPiCallForTrailers( Call<MovieTrailers> apiCall){
        apiCall.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                MovieTrailers movieTrailers;
                movieTrailers = response.body();
                view.setProgressBar(false);
                view.showTrailers(movieTrailers);

            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {

                Log.e(TAG, "Error fetching items");
            }
        });

    }




    @Override
        public TmdbClient setupRetrofitClient(){
        OkHttpClient okHttpClient = Module.generateOkHttpClient(new CallInterceptor());
        Retrofit retrofit  = Module.createRetrofitInstance(okHttpClient);
        TmdbClient tmdbClient = retrofit.create(TmdbClient.class);
        return tmdbClient;
    }


    @Override
    public void executeAPiCallForReviews(Call<MovieReviews> callForReviews) {


        callForReviews.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                MovieReviews movieReviews;
                movieReviews = response.body();
                view.setProgressBar(false);
                view.showReviews(movieReviews);

            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {

                Log.e(TAG, "Error fetching items");
            }
        });
    }


    @Override
    public void convertTrailersIntoStrings(ArrayList<MovieTrailerObject> movieTrailerObjectArrayList, ArrayList<MovieReviewObject> movieReviewObjectArrayList){
            ArrayList<java.lang.String> expandedTrailersArrayList = new ArrayList<>();
            ArrayList<java.lang.String> expandedReviewsArrayList = new ArrayList<>();

        for(int i=0; i < movieTrailerObjectArrayList.size(); i++){
            expandedTrailersArrayList.add(movieTrailerObjectArrayList.get(i).getMovieTrailerId());
        }

        for (int i=0; i<movieReviewObjectArrayList.size(); i++){
            expandedReviewsArrayList.add(movieReviewObjectArrayList.get(i).getMovieReview());
        }

        view.setupAdapter(expandedReviewsArrayList,expandedTrailersArrayList);

    }




}
