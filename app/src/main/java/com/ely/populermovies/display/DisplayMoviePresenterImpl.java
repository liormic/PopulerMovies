package com.ely.populermovies.display;

import com.ely.populermovies.MovieObject;

import java.util.List;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMoviePresenterImpl implements DisplayMoviePresenter {

    private  DisplayMovieView view;


    @Override
    public void setView(DisplayMovieView view) {
        this.view = view;
        displayMovies();
    }

    private void displayMovies(List<MovieObject> movieList) {
        view.showMovies(movieList);
    }


}
