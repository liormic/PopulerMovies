package com.ely.populermovies.display;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieTrailerObject;
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

public class DisplayMovieDetailsFragment extends Fragment implements DisplayMovieView ,ExpandableListView.OnChildClickListener{

    private ImageView moviePoster;
    private TextView  movieTitle;
    private TextView movieDescription;
    private TextView ratingText;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private TextView releaseDate;
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private ArrayList<MovieTrailerObject> movieTrailerObjectArrayList;
    private HashMap<String, ArrayList<String>> listDataChild;
    private ArrayList<MovieObject> movieList;
    private ArrayList<MovieReviewObject> movieReviewObjectArrayList;


    private int position;
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
        expandableListView = rootView.findViewById(R.id.trailers);

        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        LinearLayout listFooterView = (LinearLayout)inflater1.inflate(
                R.layout.movie_footer, null);

        expandableListView.addFooterView(listFooterView);


        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        displayMoviePresenterImpl.setView(this);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        setArgumentsInView(movieList,position);
        viewExecuteApiCall();


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
        if(movieReviewObjectArrayList!=null&&movieTrailerObjectArrayList!=null){
            displayMoviePresenterImpl.convertTrailersIntoStrings(movieTrailerObjectArrayList,movieReviewObjectArrayList);
        }
    }

    @Override
    public void showReviews(MovieReviews movieReviews) {
        movieReviewObjectArrayList  = movieReviews.getReviews();
        if(movieReviewObjectArrayList!=null&&movieTrailerObjectArrayList!=null){
            displayMoviePresenterImpl.convertTrailersIntoStrings(movieTrailerObjectArrayList,movieReviewObjectArrayList);
        }
    }

    @Override
    public void viewExecuteApiCall() {
        displayMoviePresenterImpl.setApiCall("Trailers",movieList.get(position).getId());
        displayMoviePresenterImpl.setApiCall("Reviews",movieList.get(position).getId());
        //
    }

    @Override
    public void setProgressBar(Boolean isNetworkBusy) {

    }

    @Override
    public void setupRecyclerView(View rootView) {

    }

    @Override
    public void setupAdapter(ArrayList<String> expandedReviewsArrayList, ArrayList<String> expandedTrailersArrayList) {
        listDataChild.put(listDataHeader.get(0), expandedTrailersArrayList);
        listDataChild.put(listDataHeader.get(1), expandedReviewsArrayList);
        ExpandedListAdapter listAdapter = new ExpandedListAdapter(listDataHeader,listDataChild,getActivity());
        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        if(groupPosition==0) {
            String id = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
            playYoutubeTrailer(id);
        }else{

        }

        return true;
    }

//Used stack overflow to find out implicit intent of youtube
    public void playYoutubeTrailer(String id){

            Intent nativeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webViewIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                getActivity().startActivity(nativeAppIntent);
            } catch (ActivityNotFoundException ex) {
                getActivity().startActivity(webViewIntent);
            }
        }



}