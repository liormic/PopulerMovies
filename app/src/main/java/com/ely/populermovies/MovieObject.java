package com.ely.populermovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MovieObject implements Parcelable {

    private String title;
    private String id;
    @SerializedName(value = "poster_path")
    private String posterPath;
    @SerializedName(value = "release_date")
    private String releaseDate;
    @SerializedName(value = "vote_average")
    private String averageVote;
    @SerializedName(value = "overview")
    private String movieOverview;
    private String backdrop_path;


    public MovieObject(String title, String id, String posterPath, String releaseDate, String averageVote, String movieOverview, String backdrop_path) {
        this.title = title;
        this.id = id;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.averageVote = averageVote;
        this.movieOverview = movieOverview;
        this.backdrop_path = backdrop_path;
    }


    public String getMovieOverview() {
        return movieOverview;
    }

    public String getId() {
        return id;
    }

    public String getAverageVote() {
        return averageVote;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    private MovieObject(Parcel in) {
        title = in.readString();
        id = in.readString();
        backdrop_path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(backdrop_path);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieObject> CREATOR = new Parcelable.Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };
}