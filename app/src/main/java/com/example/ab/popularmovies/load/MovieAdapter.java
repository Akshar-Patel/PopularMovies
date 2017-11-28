package com.example.ab.popularmovies.load;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

  private final MovieAdapterOnClickListener mMovieAdapterOnClickListener;
  private ArrayList<Movie> mMovies;
  private Context mContext;

  public MovieAdapter(MovieAdapterOnClickListener onClickListener) {
    this.mMovieAdapterOnClickListener = onClickListener;
  }

  public ArrayList<Movie> getMovies() {
    return mMovies;
  }

  public void setMovies(ArrayList<Movie> movies) {
    this.mMovies = movies;
    notifyDataSetChanged();
  }

  public void addMovies(ArrayList<Movie> movies) {
    this.mMovies.addAll(movies);
    notifyDataSetChanged();
  }

  @Override
  public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
    return new MovieAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
    Movie movie = mMovies.get(position);
    ImageView imageView = holder.mMoviePosterImageView;
    Picasso.with(mContext)
        .load(MovieDb.IMAGE_BASE_URL + movie.getPoster())
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(imageView);
  }

  @Override
  public int getItemCount() {
    if (mMovies == null) {
      return 0;
    }
    return mMovies.size();
  }

  public interface MovieAdapterOnClickListener {

    void onClick(Movie movie);
  }

  public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

    final ImageView mMoviePosterImageView;

    MovieAdapterViewHolder(View itemView) {
      super(itemView);
      mMoviePosterImageView = itemView.findViewById(R.id.iv_movie_poster);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      mMovieAdapterOnClickListener.onClick(mMovies.get(getAdapterPosition()));
    }
  }
}
