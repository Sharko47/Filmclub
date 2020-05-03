package com.sharko.filmclub.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieDetails {
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("genre_ids")
    ArrayList<Object> genre_ids = new ArrayList<>();

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private float vote_average;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieDetails(){

    }

    private String baseImageUrl = "https://image.tmdb.org/t/p/w500/";


    // Getter Methods

    public String getPoster_path() {
        return baseImageUrl+poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getTitle() {
        return title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    // Setter Methods

    public void setPoster_path( String poster_path ) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language( String original_language ) {
        this.original_language = original_language;
    }

    public void setOriginal_title( String original_title ) {
        this.original_title = original_title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setVote_average( float vote_average ) {
        this.vote_average = vote_average;
    }

    public void setOverview( String overview ) {
        this.overview = overview;
    }

    public void setRelease_date( String release_date ) {
        this.release_date = release_date;
    }
}
