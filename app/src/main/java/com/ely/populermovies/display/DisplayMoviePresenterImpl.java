package com.ely.populermovies.display;

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
    public void executeApiCall() {

        OkHttpClient okHttpClient = Module.generateOkHttpClient(new CallInterceptor());
        Retrofit retrofit  = Module.createRetrofitInstance(okHttpClient);
        TmdbClient tmdbClient = retrofit.create(TmdbClient.class);
        Call<MovieResults> call = tmdbClient.getResults();
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                List<MovieObject> listOfMovieObjects;
                MovieResults movieResults;
                movieResults = response.body();
                listOfMovieObjects = movieResults.getResults();
                view.showMovies(listOfMovieObjects);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                int i = 0;
            }
        });



    }


    private void displayMovies(List<MovieObject> movieList) {

        view.showMovies(movieList);

    }


}
