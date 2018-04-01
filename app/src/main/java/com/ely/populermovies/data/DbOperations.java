package com.ely.populermovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieTrailerObject;
import com.ely.populermovies.utils.Api;

import java.util.ArrayList;


public class DbOperations {

    private  Context context;

    public DbOperations(Context context){
       this.context = context;
    }

    private SQLiteDatabase createReadableDB(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        return readableDatabase;
    }

    private SQLiteDatabase createWritableDaDB(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase writableDatabased = dbHelper.getWritableDatabase();
        return writableDatabased;
    }


    public Cursor getAllitems() {

        return createReadableDB().query(ContractDB.MovieData.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
    }

    public boolean checkIfMovieExsits(String movieName) {


        String table = ContractDB.MovieData.TABLE_NAME;
        String[] columns = {ContractDB.MovieData.COLUMN_MOVIENAME};
        String selection = ContractDB.MovieData.COLUMN_MOVIENAME + "=?";
        String[] selectionArgs = {movieName};

        Cursor cursor  = createReadableDB().query(table,columns,selection,selectionArgs,null, null, null, null);
        if(cursor.getCount()<1){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

    public boolean insertMovieObjectToDb(MovieObject movieObject, ArrayList<MovieReviewObject>  movieReviewObjects, ArrayList<MovieTrailerObject> movieTrailerObjects){
        SQLiteDatabase db = createWritableDaDB();
        ContentValues contentValues = new ContentValues();
        String movieTitle = movieObject.getTitle();
        String movieOverview = movieObject.getMovieOverview();
        String moviePoster = Api.getBaseUrlPoster()+movieObject.getPosterPath();
        String movieRating = movieObject.getAverageVote();
        String trailersString=null;
        String reviewsString=null;
        if(movieTrailerObjects!=null) {
            trailersString = movieTrailerObjects.toString();
        }
        if(movieReviewObjects!=null) {
            reviewsString = movieReviewObjects.toString();
        }

        contentValues.put(ContractDB.MovieData.COLUMN_MOVIENAME, movieTitle);
        contentValues.put(ContractDB.MovieData.COLUMN_DESCRIPTION, movieOverview);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEIMAGE, moviePoster);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIETRAILERS, trailersString);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIERATING, movieRating);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEREVIEWS, reviewsString);
        db.insert(ContractDB.MovieData.TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }


    public Boolean removeMovieObjectFromDB(String name){
        SQLiteDatabase db = createWritableDaDB();
        Boolean b = null;
        try {
            b= db.delete(ContractDB.MovieData.TABLE_NAME,
                    ContractDB.MovieData.COLUMN_MOVIENAME + "='" + name+"'", null) > 0;

        } catch (SQLException e) {

        }
        return b;

}

    public Boolean removeAllMovieObjectsFromDB() {
        SQLiteDatabase db = createWritableDaDB();

        try {
            db.delete(ContractDB.MovieData.TABLE_NAME, null, null);
            db.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }
    }
