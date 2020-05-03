package com.sharko.filmclub;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharko.filmclub.Model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "favorite.db";

    private static final int DB_VERSION = 1;

    private static final String LOGTAG = "FAVOURITE";

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public FavouriteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                FavouriteContract.FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_RATINGS + " REAL NOT NULL, " +
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "+
                FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" + ");";
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME + ";");
        onCreate(db);

    }
    public void open(){
        sqLiteDatabase  = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        sqLiteOpenHelper.close();
    }
    public void addToFavourite(MovieDetails movieDetails){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID,movieDetails.getId());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_TITLE,movieDetails.getTitle());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_RATINGS,movieDetails.getVote_average());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH,movieDetails.getPoster_path());
        values.put(FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW,movieDetails.getOverview());

        db.insert(FavouriteContract.FavouriteEntry.TABLE_NAME,null,values);
        db.close();
    }

    public void deleteFavourites(int id){
        SQLiteDatabase deleteEntry = this.getWritableDatabase();

        deleteEntry.delete(FavouriteContract.FavouriteEntry.TABLE_NAME,FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID +
        "=" + id,null
        );
    }
    public List<MovieDetails> getAllFavourites(){
        String[] columns = {
                FavouriteContract.FavouriteEntry._ID,
                FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID,
                FavouriteContract.FavouriteEntry.COLUMN_TITLE,
                FavouriteContract.FavouriteEntry.COLUMN_RATINGS,
                FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH,
                FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW

        };
        String sortOrder = FavouriteContract.FavouriteEntry._ID + " ASC";
        List<MovieDetails> favouriteList = new ArrayList<>();

        SQLiteDatabase liteDatabase = this.getWritableDatabase();

        Cursor cursor = liteDatabase.query(FavouriteContract.FavouriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if(cursor.moveToFirst()){
            do{
                MovieDetails movieDetails = new MovieDetails();
                movieDetails.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID))));
                movieDetails.setTitle(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_TITLE)));
                movieDetails.setVote_average(Float.parseFloat(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_RATINGS))));
                movieDetails.setPoster_path(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_POSTER_PATH)));
                movieDetails.setOverview(cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_OVERVIEW)));

                favouriteList.add(movieDetails);



            }while (cursor.moveToNext());
        }
        cursor.close();
        liteDatabase.close();

        return favouriteList;
    }
}
