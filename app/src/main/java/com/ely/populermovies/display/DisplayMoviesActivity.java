package com.ely.populermovies.display;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;

import java.util.ArrayList;


public class DisplayMoviesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner  spinner;
    private String selectedSortOption = "Top Rated";
    private DisplayMovieFragment displayMovieFragment;
    private static int  isFirstTime=0;
    public String getSelectedSortOption() {
        return selectedSortOption;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

         displayMovieFragment = new DisplayMovieFragment();
         getSupportFragmentManager().beginTransaction().add(R.id.display_movie_container,displayMovieFragment).commit();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        return super.onCreateOptionsMenu(menu);

    }

    public void setSortOption(int position){
       if(position ==0){
           selectedSortOption = "Top Rated";
       }else{
           selectedSortOption = "Popular Movies";
       }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(isFirstTime==0) {
            setSortOption(position);
            isFirstTime++;
        }else {
            setSortOption(position);

            displayMovieFragment.refreshFragment();
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
        // consider using Java coding conventions (upper first char class names!!!)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        displayMovieDetailsFragment.setArguments(bundle);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.display_movie_container, displayMovieDetailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
