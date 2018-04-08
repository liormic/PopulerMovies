package com.ely.populermovies.display;

import android.view.View;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailers;

import java.util.ArrayList;

/**
 * Created by lior on 2/21/18.
 */

@SuppressWarnings("ALL")
interface DisplayMovieView {
    void showMovies(MovieResults movieList);
    void showTrailers(MovieTrailers movieTrailers);
    void showReviews(MovieReviews movieReviews);
    void viewExecuteOption();
    void setProgressBar(Boolean isNetworkBusy);
    void setupRecyclerView(View rootView);

    void setupAdapter(ArrayList<String> expandedReviewsArrayList, ArrayList<String> expandedTrailersArrayList);


    void throwError();
}
