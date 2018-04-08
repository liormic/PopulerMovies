package com.ely.populermovies.display;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.data.ContractDB;
import com.ely.populermovies.network.CallInterceptor;
import com.ely.populermovies.network.Module;
import com.ely.populermovies.network.TmdbClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;


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
    public void setApiCall(java.lang.String apiCallType, java.lang.String movieId ) {

        Call<MovieResults> callForPopularMovies = setupRetrofitClient().getResultsPopularMovies();
        Call<MovieResults> callForTopRatedMovies = setupRetrofitClient().getResultsTopRated();
        Call<MovieTrailers> callForTrailers = setupRetrofitClient().getTrailers(movieId);
        Call<MovieReviews> callForReviews = setupRetrofitClient().getReviews(movieId);

        switch (apiCallType) {
            case "Popular Movies":
                executeAPiCall(callForPopularMovies);
                break;
            case "Trailers":
                executeAPiCallForTrailers(callForTrailers);
                break;
            case "Reviews":
                executeAPiCallForReviews(callForReviews);

                break;
            default:
                executeAPiCall(callForTopRatedMovies);
                break;
        }


    }

    @Override
    public void executeAPiCall( Call<MovieResults> apiCall){
        view.setProgressBar(true);
            apiCall.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    MovieResults movieResults;
                    movieResults = response.body();

                    view.setProgressBar(false);
                    try {
                        view.showMovies(movieResults);
                    }catch (Exception e){
                       view.throwError();
                    }
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                    Log.e(TAG, "Error fetching items");
                }
            });

    }


    @Override
    public void executeAPiCallForTrailers( Call<MovieTrailers> apiCall){
        apiCall.enqueue(new Callback<MovieTrailers>() {
            @Override
            public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                MovieTrailers movieTrailers;
                movieTrailers = response.body();
                view.setProgressBar(false);
                view.showTrailers(movieTrailers);

            }

            @Override
            public void onFailure(Call<MovieTrailers> call, Throwable t) {

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


    @Override
    public void executeAPiCallForReviews(Call<MovieReviews> callForReviews) {


        callForReviews.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                MovieReviews movieReviews;
                movieReviews = response.body();
                view.setProgressBar(false);
                view.showReviews(movieReviews);

            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {

                Log.e(TAG, "Error fetching items");
            }
        });
    }


    @Override
    public void convertTrailersIntoStrings(ArrayList<MovieTrailerObject> movieTrailerObjectArrayList, ArrayList<MovieReviewObject> movieReviewObjectArrayList){
            ArrayList<java.lang.String> expandedTrailersArrayList = new ArrayList<>();
            ArrayList<java.lang.String> expandedReviewsArrayList = new ArrayList<>();

        for(int i=0; i < movieTrailerObjectArrayList.size(); i++){
            expandedTrailersArrayList.add(movieTrailerObjectArrayList.get(i).getMovieTrailerId());
        }

        for (int i=0; i<movieReviewObjectArrayList.size(); i++){
            expandedReviewsArrayList.add(movieReviewObjectArrayList.get(i).getMovieReview());
        }

        view.setupAdapter(expandedReviewsArrayList,expandedTrailersArrayList);

    }



    public ArrayList<MovieObject>  getMovieObjectFromCursor(Cursor cursor){
        ArrayList<MovieObject> movieObjectsArray = new ArrayList<>();

        int movieNameCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIENAME);
        int moviePosterCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIEIMAGE);
        int movieRatingCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIERATING);
        int movieDescCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_DESCRIPTION);
        int movieMovieIdsCol = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_MOVIEID);
        int movieReleaseDate = cursor.getColumnIndex(ContractDB.MovieData.COLUMN_RELEASEDATE);



        while (cursor.moveToNext()){

            MovieObject movieObject = new MovieObject(cursor.getString(movieNameCol)
                    ,cursor.getString(movieMovieIdsCol)
                    ,cursor.getString(moviePosterCol)
                    ,cursor.getString(movieReleaseDate)
                    ,cursor.getString(movieRatingCol)
                    ,cursor.getString(movieDescCol)
                    ,null);



            movieObjectsArray.add(movieObject);


        }

        cursor.close();
        return movieObjectsArray;
    }




}
