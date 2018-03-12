package com.ely.populermovies.display;

import android.view.View;

import com.ely.populermovies.MovieObject;

import java.util.ArrayList;

/**
 * Created by lior on 2/21/18.
 */

@SuppressWarnings("ALL")
interface DisplayMovieView {
    void showMovies(ArrayList<MovieObject> movieList);
    void viewExecuteApiCall();
    void setProgressBar(Boolean isNetworkBusy);
    void setupRecyclerView(View rootView);
}
