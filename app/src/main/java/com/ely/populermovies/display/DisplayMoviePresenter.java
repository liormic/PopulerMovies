package com.ely.populermovies.display;

import com.ely.populermovies.network.TmdbClient;

/**
 * Created by lior on 2/21/18.
 */

public interface DisplayMoviePresenter {
    void setView(DisplayMovieView displayMovieView);
    void executeApiCall(String apiCallType);
    TmdbClient setupRetrofitClient();
}
