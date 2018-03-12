package com.ely.populermovies.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;

import java.util.ArrayList;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMovieFragment extends Fragment implements DisplayMovieView,View.OnClickListener,DisplayMovieAdapter.ListItemClickListener{



    ArrayList<MovieObject> movieList;
    DisplayMoviePresenterImpl displayMoviePresenterImpl;
    ArrayList<MovieObject> listOfMovieObjects;
    RecyclerView recyclerView;
    ProgressBar progressBar;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void viewExecuteApiCall() {
     String selectedSortOption =((DisplayMoviesActivity)getActivity()).getSelectedSortOption();
    displayMoviePresenterImpl.executeApiCall(selectedSortOption);
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
    public void showMovies(ArrayList<MovieObject> requestMovieList) {
        movieList = requestMovieList;
        DisplayMovieAdapter displayMovieAdapter = new DisplayMovieAdapter(movieList,this);
        recyclerView.setAdapter(displayMovieAdapter);
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
        ((DisplayMoviesActivity)getActivity()).startNewDetailFragment(movieList,clickedItemIndex);
    }

}
