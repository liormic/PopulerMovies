package com.ely.populermovies.display;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.R;

import java.util.ArrayList;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMovieFragment extends Fragment implements DisplayMovieView,View.OnClickListener,DisplayMovieAdapter.ListItemClickListener{



    private ArrayList<MovieObject> movieList;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private ArrayList<MovieObject> listOfMovieObjects;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(listOfMovieObjects,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_display_movie,container,false);
        progressBar = rootView.findViewById(R.id.progressBar2);
        setProgressBar(true);
        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        displayMoviePresenterImpl.setView(this);
        setupRecyclerView(rootView);
        viewExecuteApiCall();
        return rootView;

    }

    @Override
    public void viewExecuteApiCall() {
     String selectedSortOption =((DisplayMoviesActivity)getActivity()).getSelectedSortOption();
    displayMoviePresenterImpl.setApiCall(selectedSortOption,null);
    }

    @Override
    public void setupRecyclerView(View rootView){
        recyclerView = rootView.findViewById(R.id.fragmentRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void setupAdapter(ArrayList<String> expandedReviewsArrayList, ArrayList<String> expandedTrailersArrayList) {

    }


    @Override
    public void showMovies(MovieResults requestMovieList) {
        listOfMovieObjects = requestMovieList.getMovieObjectResults();
        DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(listOfMovieObjects,this);
        recyclerView.setAdapter(displayMovieAdapter);
    }

    @Override
    public void showTrailers(MovieTrailers movieTrailers) {

    }

    @Override
    public void showReviews(MovieReviews movieReviews) {

    }


    @Override
    public void setProgressBar(Boolean isNetworkBusy){
        if(isNetworkBusy){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }


    public  void refreshFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ((DisplayMoviesActivity)getActivity()).startNewDetailFragment(listOfMovieObjects,clickedItemIndex);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

}
