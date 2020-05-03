package com.sharko.filmclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sharko.filmclub.Model.MovieDetails;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title, overview;
    ImageView moviePoster;
    Toolbar toolbar;

    private FavouriteDbHelper favouriteDbHelper;
    private MovieDetails favMovieDetails;

    private  final  AppCompatActivity activity = MovieDetailsActivity.this;



    FloatingActionButton share, addToFavourites;
    private boolean isAddedToFav = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        title = findViewById(R.id.movieDetailsTitle);
        overview = findViewById(R.id.movieDetailsOverview);
        moviePoster = findViewById(R.id.movieDetailsPoster);


        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("title")){
            String thumbnail = getIntent().getExtras().getString("poster_path");
            String movieTitle = getIntent().getExtras().getString("title");
            String movieOverview = getIntent().getExtras().getString("movie_overview");


            Glide.with(this)
                    .load(thumbnail)
                    .placeholder(R.drawable.wolverine)
                    .into(moviePoster);

            title.setText(movieTitle);
            overview.setText(movieOverview);
        }else {
            Toast.makeText(this, "Something went wrong! Please Try Again..", Toast.LENGTH_SHORT).show();
        }

        share = findViewById(R.id.shareFab);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailsActivity.this, "Share this App", Toast.LENGTH_SHORT).show();
            }
        });

        addToFavourites = findViewById(R.id.favouritesFab);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddedToFav) {
                    int movie_id = getIntent().getExtras().getInt("id");
                    favouriteDbHelper = new FavouriteDbHelper(MovieDetailsActivity.this);
                    favouriteDbHelper.deleteFavourites(movie_id);

                    SharedPreferences.Editor editor = getSharedPreferences("com.sharko.filmclub.MovieDetailsActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("Favourite Removed", false);
                    editor.apply();
                    Snackbar.make(findViewById(R.id.detailsParent), "Removed from Favourites", Snackbar.LENGTH_LONG).show();
                    isAddedToFav = false;
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.sharko.filmclub.MovieDetailsActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("Favourite Added", true);
                    editor.apply();
                    saveFavourites();
                    Snackbar.make(findViewById(R.id.detailsParent), "Added to Favourites", Snackbar.LENGTH_LONG).show();
                }

            }
        });



    }

    private void saveFavourites() {

        favouriteDbHelper = new FavouriteDbHelper(activity);
        favMovieDetails = new MovieDetails();

        int movie_id = getIntent().getExtras().getInt("id");

        String rate = getIntent().getExtras().getString("ratings");
        String poster = getIntent().getExtras().getString("poster_path");

        favMovieDetails.setId(movie_id);
        favMovieDetails.setTitle(getIntent().getExtras().getString("title"));
        favMovieDetails.setPoster_path(poster);
        favMovieDetails.setVote_average(Float.parseFloat(rate));
        favMovieDetails.setOverview(getIntent().getExtras().getString("movie_overview"));

        favouriteDbHelper.addToFavourite(favMovieDetails);

        isAddedToFav = true;
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_layout);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;

            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getIntent().getExtras().getString("title"));
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
                    title.setVisibility(View.INVISIBLE);
                    isShow = true;
                }
                else if(isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    title.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }
}
