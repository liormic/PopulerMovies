package com.ely.populermovies.display;

import android.view.View;

import com.ely.populermovies.MovieObject;

import java.util.List;

/**
 * Created by lior on 2/21/18.
 */

public interface DisplayMovieView {
    void showMovies(List<MovieObject> movieList);
    void viewExecuteApiCall();
    void setupRecyclerView(View rootView);
}
