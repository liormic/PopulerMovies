package com.ely.populermovies.display;

import android.util.Log;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.network.CallInterceptor;
import com.ely.populermovies.network.Module;
import com.ely.populermovies.network.TmdbClient;

import java.util.List;

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
    public void executeApiCall(String apiCallType) {

        Call<MovieResults> apiCall = null;
        Call<MovieResults> callForPopularMovies = setupRetrofitClient().getResultsPopularMovies();
        Call<MovieResults> callForTopRatedMovies = setupRetrofitClient().getResultsTopRated();

        if(apiCallType.equals("callForPopularMovies")){
            apiCall = callForPopularMovies;
        }else {
            apiCall = callForTopRatedMovies;
        }
        apiCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                List<MovieObject> listOfMovieObjects;
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


    private void displayMovies(List<MovieObject> movieList) {

        view.showMovies(movieList);

    }


}
