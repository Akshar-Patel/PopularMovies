package com.example.ab.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ab on 14/11/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMoiveData;
    private Context context;

    public void setMovieData(String[] movieData) {
        mMoiveData = movieData;
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
        String movieData = mMoiveData[position];
        ImageView imageView = holder.mMoviePosterImageView;
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185" + movieData)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        if (mMoiveData == null) return 0;
        return mMoiveData.length;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mMoviePosterImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
