package com.ely.populermovies.data;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.MovieReviewObject;
import com.ely.populermovies.MovieTrailerObject;

import java.util.ArrayList;


public class DbOperations extends ContentProvider {

    private  Context context;
    private static final int CODE_MOVIEID = 100;
    private static final int CODE_MOVIETITLE = 200;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ContractDB.MovieData.CONTENT_AUTHORITY;
        matcher.addURI(authority, ContractDB.MovieData.TABLE_NAME, CODE_MOVIETITLE);
        matcher.addURI(authority, ContractDB.MovieData.TABLE_NAME + "/#", CODE_MOVIEID);

        return matcher;
    }


    public DbOperations(){

    }


    public DbOperations(Context context){
     this.context = context;
    }

    private SQLiteDatabase createReadableDB(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        return readableDatabase;
    }

    private SQLiteDatabase createWritableDB(){
        DbHelper dbHelper = new DbHelper(getContext());
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

        if(movieName.contains("'")){
            movieName= movieName.replace("'","");
        }
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



    public void insertMovieObjectToDb(MovieObject movieObject, ArrayList<MovieReviewObject> movieReviewObjects, ArrayList<MovieTrailerObject> movieTrailerObjects, Activity activity){
     //   SQLiteDatabase db = createWritableDB();
        ContentValues contentValues = new ContentValues();
        String movieTitle = movieObject.getTitle();
        if(movieTitle.contains("'")){
           movieTitle= movieTitle.replace("'","");
        }
        String movieOverview = movieObject.getMovieOverview();
        String moviePoster = movieObject.getPosterPath();
        String movieRating = movieObject.getAverageVote();
        String movieReleaseDate = movieObject.getReleaseDate();
        String movieId= movieObject.getId();

        contentValues.put(ContractDB.MovieData.COLUMN_MOVIENAME, movieTitle);
        contentValues.put(ContractDB.MovieData.COLUMN_DESCRIPTION, movieOverview);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEIMAGE, moviePoster);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIERATING, movieRating);
        contentValues.put(ContractDB.MovieData.COLUMN_MOVIEID, movieId);
        contentValues.put(ContractDB.MovieData.COLUMN_RELEASEDATE, movieReleaseDate);

        context = activity;
        context.getContentResolver().
                insert(ContractDB.MovieData.CONTENT_URI, contentValues);
    }


    public void removeMovieObjectFromDB(String name, Activity activity){


        context = activity;
        context.getContentResolver().
                delete(ContractDB.MovieData.CONTENT_URI, name, null);

    }

    public Boolean removeAllMovieObjectsFromDB() {
        SQLiteDatabase db = createWritableDB();

        try {
            db.delete(ContractDB.MovieData.TABLE_NAME, null, null);
            db.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        context = getContext();
        SQLiteDatabase db = createReadableDB();
            Cursor cursor;
            switch (sUriMatcher.match(uri)) {

                case CODE_MOVIEID: {

                    String _ID = uri.getLastPathSegment();
                    String[] selectionArguments = new String[]{_ID};

                    cursor = db.query(
                            ContractDB.MovieData.TABLE_NAME,
                            projection,
                            ContractDB.MovieData._ID + " = ? ",
                            selectionArguments,
                            null,
                            null,
                            sortOrder);
                    break;
                }


                case CODE_MOVIETITLE: {
                    cursor = db.query(
                            ContractDB.MovieData.TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);

                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

        //noinspection ConstantConditions
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = createWritableDB();

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIETITLE:

                long _id = db.insert(ContractDB.MovieData.TABLE_NAME, null, values);


                if (_id != -1) {

                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return ContractDB.MovieData.buildUriWithId(_id);

            default:
                return null;
        }



    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = createWritableDB();

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIETITLE:
                //noinspection ConstantConditions
                if(selection.contains("'")){
                 selection = selection.replace("'","");
                }
                Boolean b = null;
                try {

                    b = db.delete(ContractDB.MovieData.TABLE_NAME,
                            ContractDB.MovieData.COLUMN_MOVIENAME + "='" + selection+"'", null) > 0;

                } catch (SQLException e) {
                    e.printStackTrace();

                }
                return 1;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
