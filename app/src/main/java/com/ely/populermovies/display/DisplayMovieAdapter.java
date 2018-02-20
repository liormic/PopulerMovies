package com.ely.populermovies.display;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ely.populermovies.MovieObject;

import java.util.List;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMovieAdapter extends RecyclerView.Adapter{
    private List<MovieObject> listMovies;
    private DisplayMovieView view;


    private class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
