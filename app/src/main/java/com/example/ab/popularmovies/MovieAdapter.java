package com.example.ab.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ab.popularmovies.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ab on 14/11/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    ArrayList<Movie> movies;
    private Context context;


    public void addMovies(ArrayList<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.movie_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        ImageView imageView = holder.mMoviePosterImageView;
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185" + movie.getPoster())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mMoviePosterImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = itemView.findViewById(R.id.iv_movie_poster);
        }
    }


}
