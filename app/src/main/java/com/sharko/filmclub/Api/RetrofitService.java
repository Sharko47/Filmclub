package com.sharko.filmclub.Api;

import com.sharko.filmclub.Model.MovieDbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("movie/now_playing")
    Call<MovieDbResponse> getNowPlayingMovies(@Query("api_key") String apikey);


    @GET("movie/upcoming")
    Call<MovieDbResponse> getUpcomingMovies(@Query("api_key") String apikey);
}
