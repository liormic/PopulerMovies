package com.ely.populermovies.display;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieResults;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieReviews;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.MovieTrailers;
import com.ely.populermovies.R;
import com.ely.populermovies.adapters.ExpandedListAdapter;
import com.ely.populermovies.data.DbOperations;
import com.ely.populermovies.utils.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DisplayMovieDetailsFragment extends Fragment implements DisplayMovieView ,ExpandableListView.OnChildClickListener {

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieDescription;
    private TextView ratingText;
    private DisplayMoviePresenterImpl displayMoviePresenterImpl;
    private TextView releaseDate;
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private ArrayList<MovieTrailerObject> movieTrailerObjectArrayList;
    private HashMap<String, ArrayList<String>> listDataChild;
    private ArrayList<MovieObject> movieList;
    private ScrollView scrollView;
    private ArrayList<MovieReviewObject> movieReviewObjectArrayList;
    private ProgressBar progressBar;
    private ImageButton imageButtonFav;
    private Context context;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_page, container, false);
        Bundle bundle = this.getArguments();

        position = bundle.getInt("position");
        movieList = bundle.getParcelableArrayList("movieList");
        moviePoster = rootView.findViewById(R.id.ImageViewExpand);
        movieTitle = rootView.findViewById(R.id.movieTitleExpand);
        movieDescription = rootView.findViewById(R.id.description);
        releaseDate = rootView.findViewById(R.id.releaseDate);
        ratingText = rootView.findViewById(R.id.ratingText);
        scrollView = rootView.findViewById(R.id.scrollViewMovie);
        imageButtonFav = rootView.findViewById(R.id.imageButtonFav);

     //   progressBar = rootView.findViewById(R.id.progressBarMovie);

        expandableListView = rootView.findViewById(R.id.trailers);
        displayMoviePresenterImpl = new DisplayMoviePresenterImpl();
        displayMoviePresenterImpl.setView(this);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        setArgumentsInView(movieList, position);

        viewExecuteApiCall();


        return rootView;
    }


    private void setArgumentsInView(ArrayList<MovieObject> movieList, int position) {

        Picasso.with(getActivity()).load(Api.getBaseUrlPoster() + movieList.get(position).getPosterPath()).fit().into(moviePoster);
        movieTitle.setText(movieList.get(position).getTitle());
        movieDescription.setText(movieList.get(position).getMovieOverview());
        ratingText.setText(movieList.get(position).getAverageVote());
        releaseDate.setText(movieList.get(position).getReleaseDate());
        listDataHeader.add("Trailers");
        listDataHeader.add("Reviews");
        imageButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favbuttonListener();
            }
        });
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
        if (movieReviewObjectArrayList != null && movieTrailerObjectArrayList != null) {
            displayMoviePresenterImpl.convertTrailersIntoStrings(movieTrailerObjectArrayList, movieReviewObjectArrayList);
        }
    }

    @Override
    public void showReviews(MovieReviews movieReviews) {
        movieReviewObjectArrayList = movieReviews.getReviews();
        if (movieReviewObjectArrayList != null && movieTrailerObjectArrayList != null) {
            displayMoviePresenterImpl.convertTrailersIntoStrings(movieTrailerObjectArrayList, movieReviewObjectArrayList);
        }
    }

    @Override
    public void viewExecuteApiCall() {
        displayMoviePresenterImpl.setApiCall("Trailers", movieList.get(position).getId());
        displayMoviePresenterImpl.setApiCall("Reviews", movieList.get(position).getId());
        //
    }

    @Override
    public void setProgressBar(Boolean isNetworkBusy) {

    }

    @Override
    public void setupRecyclerView(View rootView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setupAdapter(ArrayList<String> expandedReviewsArrayList, ArrayList<String> expandedTrailersArrayList) {
        listDataChild.put(listDataHeader.get(0), expandedTrailersArrayList);
        listDataChild.put(listDataHeader.get(1), expandedReviewsArrayList);
        ExpandedListAdapter listAdapter = new ExpandedListAdapter(listDataHeader, listDataChild, getActivity());
        expandableListView.setAdapter(listAdapter);
        setListViewHeight(expandableListView);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        expandableListView.setOnChildClickListener(this);
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        if (groupPosition == 0) {
            String id = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
            playYoutubeTrailer(id);
        } else {

        }

        return true;
    }

    //Used stack overflow to find out implicit intent of youtube
    public void playYoutubeTrailer(String id) {

        Intent nativeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webViewIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            getActivity().startActivity(nativeAppIntent);
        } catch (ActivityNotFoundException ex) {
            getActivity().startActivity(webViewIntent);
        }
    }

    private void setExpandableListViewHeight(ExpandableListView listView) {
        try {
            ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                View listItem = listAdapter.getGroupView(i, false, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
            if (height < 10) height = 200;
            params.height = height;
            listView.setLayoutParams(params);
            listView.requestLayout();
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void favbuttonListener() {
        DbOperations dbOperations = new DbOperations(getActivity());
        if(!dbOperations.checkIfMovieExsits(movieList.get(position).getTitle())){
        dbOperations.insertMovieObjectToDb(movieList.get(position),movieReviewObjectArrayList,movieTrailerObjectArrayList);
            Toast.makeText(getActivity(),"Movie Added!",Toast.LENGTH_SHORT).show();

    }else{

        }
}
}