package com.ely.populermovies.display;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.R;
import com.ely.populermovies.data.ContractDB;

import java.util.ArrayList;



public class DisplayMovieFragment extends Fragment implements DisplayMovieView, View.OnClickListener, DisplayMovieAdapter.ListItemClickListener {


    private static final String KEY_STATE_POSITION = "KEY_STATE";
    private ArrayList<MovieObject> movieListFromDb;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private ArrayList<MovieObject> listOfMovieObjects;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private   GridLayoutManager gridLayoutManager;
    private   Parcelable savedRecyclerLayoutState;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(listOfMovieObjects, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_display_movie, container, false);
        progressBar = rootView.findViewById(R.id.progressBar2);
        setProgressBar(true);
        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        displayMoviePresenterImpl.setView(this);

        setupRecyclerView(rootView);

        viewExecuteOption();
        return rootView;

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(KEY_STATE_POSITION);
        }
    }

    @Override
    public void viewExecuteOption() {
        String selectedSortOption = ((DisplayMoviesActivity) getActivity()).getSelectedSortOption();
        if(selectedSortOption.equals(getString(R.string.favs))){
            Cursor cursor = getActivity().getContentResolver()
                    .query(ContractDB.MovieData.CONTENT_URI,null,null,null,null);

            movieListFromDb =  displayMoviePresenterImpl.getMovieObjectFromCursor(cursor);

            DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(movieListFromDb, this);
            recyclerView.setAdapter(displayMovieAdapter);
            setProgressBar(false);
        }else {
            displayMoviePresenterImpl.setApiCall(selectedSortOption, null);
        }
        }

    @Override
    public void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.fragmentRecyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity()));
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupAdapter(ArrayList<String> expandedReviewsArrayList, ArrayList<String> expandedTrailersArrayList) {

    }

    @Override
    public void throwError() {
        Toast.makeText(getActivity(),"Please enter Api KEY @CallInterceptor",Toast.LENGTH_LONG).show();
    }


    @Override
    public void showMovies(MovieResults requestMovieList) {
        if(savedRecyclerLayoutState!=null){
            listOfMovieObjects = requestMovieList.getMovieObjectResults();
            DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(listOfMovieObjects, this);

            recyclerView.setAdapter(displayMovieAdapter);
            gridLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);

        }else {
            listOfMovieObjects = requestMovieList.getMovieObjectResults();
            DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(listOfMovieObjects, this);

            recyclerView.setAdapter(displayMovieAdapter);
        }
    }

    @Override
    public void showTrailers(MovieTrailers movieTrailers) {

    }

    @Override
    public void showReviews(MovieReviews movieReviews) {

    }


    @Override
    public void setProgressBar(Boolean isNetworkBusy) {
        if (isNetworkBusy) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    public  void refreshFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(((DisplayMoviesActivity) getActivity()).getSelectedSortOption().equals(getString(R.string.favs))) {
            ((DisplayMoviesActivity) getActivity()).startNewDetailFragment(movieListFromDb, clickedItemIndex);
        }else {
            ((DisplayMoviesActivity) getActivity()).startNewDetailFragment(listOfMovieObjects, clickedItemIndex);
        }
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATE_POSITION,
                gridLayoutManager.onSaveInstanceState());

    }

    @Override
    public void onPause() {
        super.onPause();
        savedRecyclerLayoutState = gridLayoutManager.onSaveInstanceState();
    }



    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

}



