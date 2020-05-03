package com.sharko.filmclub.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sharko.filmclub.Api.RetrofitClient;
import com.sharko.filmclub.Api.RetrofitService;
import com.sharko.filmclub.BuildConfig;
import com.sharko.filmclub.Model.MovieDbResponse;
import com.sharko.filmclub.Model.MovieDetails;
import com.sharko.filmclub.MoviesAdapter;
import com.sharko.filmclub.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlaying extends Fragment {
    private RecyclerView movieRecyclerView;
    private MoviesAdapter moviesAdapter;
    private List<MovieDetails> movieDetailsList;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static final String LOG_TAG = MoviesAdapter.class.getName();

    private static String API_KEY = "e16f9ec421f01f05db45a6d069d84d56";

    public NowPlaying() {
        // Required empty public constructor
    }


    public static NowPlaying newInstance() {
        return new NowPlaying();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);


        movieRecyclerView = rootView.findViewById(R.id.nowPlayingRecyclerView);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeToRefreshNowPlaying);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                Toast.makeText(getContext(), "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        initView();


        return rootView;
    }

    private void initView() {

        movieDetailsList = new ArrayList<>();

        moviesAdapter = new MoviesAdapter(getContext(),movieDetailsList);

        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else{
            movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        }

        movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        movieRecyclerView.setAdapter(moviesAdapter);

        moviesAdapter.notifyDataSetChanged();

        loadJson();
    }

    private void loadJson() {
        try {
            if(API_KEY.isEmpty()){
                Toast.makeText(getContext(), "Unable to fetch Movies. Request an API Key from the owner", Toast.LENGTH_SHORT).show();
            }

            RetrofitClient retrofitClient = new RetrofitClient();

            RetrofitService retrofitService = retrofitClient.getRetrofitClient().create(RetrofitService.class);


            Call<MovieDbResponse> movieDbResponseCall = retrofitService.getNowPlayingMovies(API_KEY);

            movieDbResponseCall.enqueue(new Callback<MovieDbResponse>() {
                @Override
                public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                    List<MovieDetails> movieDetails = response.body().getResults();
                    movieRecyclerView.setAdapter(new MoviesAdapter(getContext(),movieDetails));
                    movieRecyclerView.smoothScrollToPosition(0);
                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<MovieDbResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed due to unknown reasons.", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
