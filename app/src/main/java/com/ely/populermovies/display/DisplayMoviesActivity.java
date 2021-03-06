package com.ely.populermovies.display;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;

import java.util.ArrayList;


public class DisplayMoviesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnTouchListener{
    private Spinner  spinner;
    private String selectedSortOption;
    private DisplayMovieFragment displayMovieFragment;
    private boolean userSelect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
           selectedSortOption = sharedPref.getString(getString(R.string.sortOption) , getString(R.string.top_rated));

            initFragment();
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_fav);
            setSupportActionBar(toolbar);
            setupActionBar();

    }

    public void initFragment(){
        displayMovieFragment = new DisplayMovieFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.display_movie_container, displayMovieFragment).commit();
    }

    public String getSelectedSortOption() {
        return selectedSortOption;
    }


    private void setupActionBar(){

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            initFragment();
            homeActivitySetupToolbar();
        }

    }
    private void homeActivitySetupToolbar() {
        if (DisplayMovieDetailsFragment.isReviewsVisible) {
            getFragmentManager().popBackStack();
            spinner.setVisibility(View.VISIBLE);
            setupActionBar();
            DisplayMovieDetailsFragment.isReviewsVisible = false;
        } else {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            homeActivitySetupToolbar();

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem item = menu.findItem(R.id.sort_spinner);
        spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener (this);
        spinner.setOnTouchListener(this);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        switch (selectedSortOption){
            case "Top Rated":
                spinner.setSelection(0);
                break;
            case "Popular Movies":
                spinner.setSelection(1);
                break;
            case "Favorites":
                spinner.setSelection(2);
                break;

        }


        return super.onCreateOptionsMenu(menu);

    }

    private void setSortOption(int position){
       if(position == 0){
           selectedSortOption = getResources().getString(com.ely.populermovies.R.string.top_rated);
       }else if(position == 1){
           selectedSortOption = getResources().getString(com.ely.populermovies.R.string.popular_movies);
       }else {
           selectedSortOption = getResources().getString(com.ely.populermovies.R.string.favs);
       }

    }


    //OnTouch method was taken from stackoverflow

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(userSelect) {
                setSortOption(position);
                displayMovieFragment.refreshFragment();
                userSelect=false;
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startNewDetailFragment(ArrayList<MovieObject> movieList, int clickedItemIndex){
        Bundle bundle = new Bundle();
        bundle.putInt("position",clickedItemIndex);
        bundle.putParcelableArrayList("movieList",movieList);

        DisplayMovieDetailsFragment displayMovieDetailsFragment = new DisplayMovieDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        displayMovieDetailsFragment.setArguments(bundle);

        transaction.replace(R.id.detail_movie_container, displayMovieDetailsFragment,getString(R.string.detail_movie_fragment));
        transaction.addToBackStack(getString(R.string.detail_movie_fragment));
        spinner.setVisibility(View.GONE);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(movieList.get(clickedItemIndex).getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setSortOption(spinner.getSelectedItemPosition());
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.sortOption), selectedSortOption);
        editor.apply();


    }
}
