package com.ely.populermovies.display;

import com.ely.populermovies.network.TmdbClient;

/**
 * Created by lior on 2/21/18.
 */

@SuppressWarnings("ALL")
interface DisplayMoviePresenter {
    void setView(DisplayMovieView displayMovieView);
    void executeApiCall(String apiCallType,String param);
    TmdbClient setupRetrofitClient();
}
