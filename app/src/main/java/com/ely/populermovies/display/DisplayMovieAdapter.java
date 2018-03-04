package com.ely.populermovies.display;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;
import com.ely.populermovies.utils.Api;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lior on 2/21/18.
 */

public class DisplayMovieAdapter extends RecyclerView.Adapter<DisplayMovieAdapter.ViewHolder>{
    private List<MovieObject> listMovieObjects;
    private DisplayMovieView view;
    private Context context;

    public DisplayMovieAdapter( List<MovieObject> listMovieObjects,DisplayMovieView view){
        this.view = view;
        this.listMovieObjects = listMovieObjects;

    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageView imageView;
        TextView  sugText;



        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movieImage);
            sugText= itemView.findViewById(R.id.sugText);

        }

        @Override
        public void onClick(View view) {

        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.sugText.setText(listMovieObjects.get(position).getTitle());
        Picasso.with(context).load(Api.getBaseUrlPoster()+listMovieObjects.get(position).getPosterPath()).fit().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return  listMovieObjects.size();
    }
}
