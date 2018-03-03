package com.ely.populermovies.display;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.R;
import com.ely.populermovies.network.CallInterceptor;
import com.ely.populermovies.network.Module;
import com.ely.populermovies.network.TmdbClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMovieFragment extends Fragment implements DisplayMovieView {

    DisplayMoviePresenter displayMoviePresenter;
    @Override
    public void showMovies(List<MovieObject> movieList) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_display_movie,container,false);

        OkHttpClient okHttpClient = Module.generateOkHttpClient(new CallInterceptor());
        Retrofit retrofit = Module.createRetrofitInstance(okHttpClient);
        TmdbClient tmdbClient = retrofit.create(TmdbClient.class);
        Call<MovieResults> call = tmdbClient.getResults();
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                MovieResults movieResults =  new MovieResults();
                movieResults = response.body();
                List<MovieObject> list = movieResults.getResults();
                int i=3;
               // List<MovieObject> list;

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                 int i = 0;
            }
        });


        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  displayMoviePresenter.setView(this);
    }
}
