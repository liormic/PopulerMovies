package com.ely.populermovies.display;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieTrailers;
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

public class DisplayMovieDetailsFragment extends Fragment implements DisplayMovieView {

    private ImageView moviePoster;
    private TextView  movieTitle;
    private TextView movieDescription;
    private TextView ratingText;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private TextView releaseDate;
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private ArrayList<MovieTrailerObject> movieTrailerObjectArrayList;
    private HashMap<String, ArrayList<MovieTrailerObject>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_page,container,false);
        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");
        ArrayList<MovieObject> movieList = bundle.getParcelableArrayList("movieList");
        moviePoster = rootView.findViewById(R.id.ImageViewExpand);
        movieTitle = rootView.findViewById(R.id.movieTitleExpand);
        movieDescription = rootView.findViewById(R.id.description);
        releaseDate = rootView.findViewById(R.id.releaseDate);
        ratingText = rootView.findViewById(R.id.ratingText);
        expandableListView = rootView.findViewById(R.id.trailers);
        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<MovieTrailerObject>>();
        displayMoviePresenterImpl.setView(this);
        displayMoviePresenterImpl.setApiCall("Trailers",movieList.get(position).getId());

        setArgumentsInView(movieList,position);
        ExpandedListAdapter listAdapter = new ExpandedListAdapter(listDataHeader,listDataChild,getActivity());
        expandableListView.setAdapter(listAdapter);

       return rootView;
    }



    private void setArgumentsInView(ArrayList<MovieObject> movieList, int position){
        Picasso.with(getActivity()).load(Api.getBaseUrlPoster()+movieList.get(position).getPosterPath()).fit().into(moviePoster);
        movieTitle.setText(movieList.get(position).getTitle());
        movieDescription.setText(movieList.get(position).getMovieOverview());
        ratingText.setText(movieList.get(position).getAverageVote());
        releaseDate.setText(movieList.get(position).getReleaseDate());
        listDataHeader.add("Trailers");
        listDataHeader.add("Reviews");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void showMovies(MovieResults movieList) {

    }

    @Override
    public void showTrailers(MovieTrailers movieTrailers) {
        movieTrailerObjectArrayList = movieTrailers.getTrailers();
        listDataChild.put(listDataHeader.get(0), movieTrailerObjectArrayList);
    }

    @Override
    public void viewExecuteApiCall() {

    }

    @Override
    public void setProgressBar(Boolean isNetworkBusy) {

    }

    @Override
    public void setupRecyclerView(View rootView) {

    }
}