package com.ely.populermovies.display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ely.populermovies.R;



public class DisplayMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        DisplayMovieFragment displayMovieFragment = new DisplayMovieFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.display_movie_container,displayMovieFragment).commit();

    }
}
