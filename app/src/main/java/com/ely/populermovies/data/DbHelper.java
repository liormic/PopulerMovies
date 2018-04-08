package com.ely.populermovies.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION =2;
    private static final String TAG = DbHelper.class.getSimpleName();


    public DbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + ContractDB.MovieData.TABLE_NAME + " (" +
                    ContractDB.MovieData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContractDB.MovieData.COLUMN_MOVIENAME + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_MOVIEIMAGE + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_MOVIEID + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_RELEASEDATE + " TEXT NOT NULL, " +
                    ContractDB.MovieData.COLUMN_MOVIERATING + " TEXT NOT NULL" +
                    "); ";

            sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        }catch (SQLException e){

        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContractDB.MovieData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
