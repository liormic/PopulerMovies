package com.ely.populermovies.data;

import android.provider.BaseColumns;



public class ContractDB {

    private ContractDB(){}


    public class MovieData implements BaseColumns{

        public static final String TABLE_NAME="movies";
        public static final String COLUMN_MOVIENAME="moviename";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_MOVIEIMAGE="image";
        public static final String COLUMN_MOVIERATING="rating";
        public static final String COLUMN_MOVIEREVIEWS="reveiews";
        public static final String COLUMN_MOVIETRAILERS="trailers";


    }

}
