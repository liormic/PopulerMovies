package com.ely.populermovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Lior on 3/15/2018.
 */

public class MovieTrailers {
    @SerializedName(value = "results")
    private ArrayList<MovieTrailerObject> movieTrailersList;


    public ArrayList<MovieTrailerObject> getTrailers(){
        return movieTrailersList;
    }


}
