package com.ely.populermovies.display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;


import com.ely.populermovies.R;



public class DisplayMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        DisplayMovieFragment displayMovieFragment = new DisplayMovieFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.display_movie_container,displayMovieFragment).commit();
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mainmenu);



        return super.onCreateOptionsMenu(menu);

    }



}
