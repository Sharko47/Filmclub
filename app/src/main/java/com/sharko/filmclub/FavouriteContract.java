package com.sharko.filmclub;

import android.provider.BaseColumns;

public class FavouriteContract {
    public static final class FavouriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATINGS = "average_votes";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
