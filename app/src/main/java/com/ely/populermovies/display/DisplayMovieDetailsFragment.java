package com.ely.populermovies.display;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ely.populermovies.MovieObject;

import com.ely.populermovies.R;
import com.ely.populermovies.adapters.ExpandedListAdapter;
import com.ely.populermovies.utils.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lior on 3/7/2018.
 */

public class DisplayMovieDetailsFragment extends Fragment implements DisplayMovieView{

    private ImageView moviePoster;
    private TextView  movieTitle;
    private TextView movieDescription;
    private TextView ratingText;
    private TextView releaseDate;
    private ExpandableListView trailersList;
    private ExpandableListView reviewsList;
    private ArrayList<MovieObject> movieList;
    private int position;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private ExpandableListAdapter listAdapter;
    private HashMap<String, ArrayList<MovieObject>> expandableChildList;
    private  List<String> expandableParentList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_page,container,false);
        Bundle bundle = this.getArguments();
        position = bundle.getInt("position");
        movieList = bundle.getParcelableArrayList("movieList");
        moviePoster = rootView.findViewById(R.id.ImageViewExpand);
        movieTitle = rootView.findViewById(R.id.movieTitleExpand);
        movieDescription = rootView.findViewById(R.id.description);
        releaseDate = rootView.findViewById(R.id.releaseDate);
        ratingText = rootView.findViewById(R.id.ratingText);
        trailersList = rootView.findViewById(R.id.trailers);
        reviewsList = rootView.findViewById(R.id.reviews);
        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        displayMoviePresenterImpl.setView(this);


        // setting list adapter
        trailersList.setAdapter(listAdapter);
        setArgumentsInView(movieList,position);
       return rootView;
    }


    private void setArgumentsInView(ArrayList<MovieObject> movieList, int position){
        Picasso.with(getActivity()).load(Api.getBaseUrlPoster()+movieList.get(position).getPosterPath()).fit().into(moviePoster);
        movieTitle.setText(movieList.get(position).getTitle());
        movieDescription.setText(movieList.get(position).getMovieOverview());
        ratingText.setText(movieList.get(position).getAverageVote());
        releaseDate.setText(movieList.get(position).getReleaseDate());



    }



    @Override
    public void showMovies(ArrayList<MovieObject> movieList) {
        expandableParentList.add("Trailers");
        expandableParentList.add("Reviews");
        expandableChildList = new HashMap<String, ArrayList<MovieObject>>();

        expandableChildList.put(expandableParentList.get(0),movieList);
        listAdapter = new ExpandedListAdapter(expandableChildList,getActivity()) {
        };
        trailersList.setAdapter(listAdapter);
    }

    @Override
    public void viewExecuteApiCall() {

        displayMoviePresenterImpl.executeApiCall(movieList.get(position).getId(),null);
    }

    @Override
    public void setProgressBar(Boolean isNetworkBusy) {

    }

    @Override
    public void setupRecyclerView(View rootView) {

    }


}