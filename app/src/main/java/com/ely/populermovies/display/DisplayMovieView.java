package com.ely.populermovies.display;

import android.view.View;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieTrailers;

/**
 * Created by lior on 2/21/18.
 */

@SuppressWarnings("ALL")
interface DisplayMovieView {
    void showMovies(MovieResults movieList);
    void showTrailers(MovieTrailers movieTrailers);
    void viewExecuteApiCall();
    void setProgressBar(Boolean isNetworkBusy);
    void setupRecyclerView(View rootView);
}
