package com.ely.populermovies.display;

import android.util.Log;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
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
    public void executeApiCall(String apiCallType,String movieId ){

        Call<MovieResults> apiCall;
        Call<MovieResults> callForPopularMovies = setupRetrofitClient().getResultsPopularMovies();
        Call<MovieResults> callForTopRatedMovies = setupRetrofitClient().getResultsTopRated();
        Call<MovieResults> callForTrailers = setupRetrofitClient().getResultsTrailers(movieId);

        if(apiCallType.equals("Popular Movies")){
            apiCall = callForPopularMovies;
        }else if(apiCallType.equals(null)){
            apiCall = callForTrailers;
        }else{
            apiCall = callForTopRatedMovies;
        }
        apiCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<MovieObject> listOfMovieObjects ;
                MovieResults movieResults;
                movieResults = response.body();
                listOfMovieObjects = movieResults.getResults();
                view.setProgressBar(false);
                view.showMovies(listOfMovieObjects);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

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


}
