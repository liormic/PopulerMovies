package com.ely.populermovies.data;

import android.net.Uri;
import android.provider.BaseColumns;



public class ContractDB {

    private ContractDB(){}


    public static class MovieData implements BaseColumns{
        public static final String CONTENT_AUTHORITY = "com.ely.populermovies.data";
        private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);



        public static final String TABLE_NAME="movies";
        public static final String COLUMN_MOVIENAME="moviename";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_MOVIEIMAGE="image";
        public static final String COLUMN_MOVIERATING="rating";
        public static final String COLUMN_MOVIEID="movieid";
        public static final String COLUMN_RELEASEDATE="releaseDate";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static Uri buildUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
    }

}
