package com.ely.populermovies.display;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;
import com.ely.populermovies.utils.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lior on 3/7/2018.
 */

public class DisplayMovieDetailsFragment extends Fragment {

    private ImageView moviePoster;
    private TextView  movieTitle;
    private TextView movieDescription;
    private TextView ratingText;
    private TextView releaseDate;


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



}