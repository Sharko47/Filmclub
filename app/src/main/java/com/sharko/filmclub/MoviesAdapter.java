package com.sharko.filmclub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharko.filmclub.Model.MovieDetails;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.NowPlayingViewHolder> {

    private Context context;
    private List<MovieDetails> movieDetailsList;

    public MoviesAdapter(Context context, List<MovieDetails> movieDetailsList){
        this.context = context;
        this.movieDetailsList = movieDetailsList;
    }


    @NonNull
    @Override
    public MoviesAdapter.NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card_container,parent,false);

        return new NowPlayingViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.NowPlayingViewHolder holder, int position) {
        float rate = movieDetailsList.get(position).getVote_average();

        holder.averageVoting.setText(Float.toString(rate));

        holder.date.setText(movieDetailsList.get(position).getRelease_date());

        Glide.with(context)
                .load(movieDetailsList.get(position).getPoster_path())
                .placeholder(R.drawable.wolverine)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder{
        public TextView averageVoting, date;
        public ImageView poster;

        public NowPlayingViewHolder(@NonNull View itemView) {
            super(itemView);


            poster = itemView.findViewById(R.id.moviePoster);
            averageVoting = itemView.findViewById(R.id.ratings);
            date = itemView.findViewById(R.id.releaseDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent openMovieDetailsActivity = new Intent(context,MovieDetailsActivity.class);
                        openMovieDetailsActivity.putExtra("id",movieDetailsList.get(pos).getId());
                        openMovieDetailsActivity.putExtra("poster_path",movieDetailsList.get(pos).getPoster_path());
                        openMovieDetailsActivity.putExtra("title",movieDetailsList.get(pos).getTitle());
                        openMovieDetailsActivity.putExtra("release_date",movieDetailsList.get(pos).getRelease_date());
                        openMovieDetailsActivity.putExtra("movie_overview",movieDetailsList.get(pos).getOverview());
                        openMovieDetailsActivity.putExtra("ratings",movieDetailsList.get(pos).getVote_average());
                        openMovieDetailsActivity.putExtra("original_lan",movieDetailsList.get(pos).getOriginal_language());
                        openMovieDetailsActivity.putExtra("original_title",movieDetailsList.get(pos).getOriginal_title());

                        openMovieDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(openMovieDetailsActivity);
                    }

                }
            });

        }
    }
}
