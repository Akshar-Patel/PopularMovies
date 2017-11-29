package com.example.ab.popularmovies.favorite;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class FavoriteMovieAdapter
    extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {

  private final Context mContext;
  private final Cursor mCursor;
  private final FavoriteMovieAdapterOnClickListener mFavoriteMovieAdapterOnClickListener;

  FavoriteMovieAdapter(Context context, Cursor cursor,
      FavoriteMovieAdapterOnClickListener favoriteMovieAdapterOnClickListener) {
    mContext = context;
    mCursor = cursor;
    mFavoriteMovieAdapterOnClickListener = favoriteMovieAdapterOnClickListener;
  }

  @Override
  public FavoriteMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
    return new FavoriteMovieHolder(view);
  }

  @Override
  public void onBindViewHolder(FavoriteMovieHolder holder, int position) {
    String poster = null;
    if (mCursor.moveToPosition(position)) {
      poster = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER));
    }
    ImageView imageView = holder.mMoviePosterImageView;
    Picasso.with(mContext)
        .load(MovieDb.IMAGE_BASE_URL + poster)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(imageView);
  }

  @Override
  public int getItemCount() {
    return mCursor.getCount();
  }

  public interface FavoriteMovieAdapterOnClickListener {

    void onClick(Movie movie);
  }

  public class FavoriteMovieHolder extends RecyclerView.ViewHolder implements OnClickListener {

    final ImageView mMoviePosterImageView;

    FavoriteMovieHolder(View itemView) {
      super(itemView);
      mMoviePosterImageView = itemView.findViewById(R.id.iv_movie_poster);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Movie movie = new Movie();
      if (mCursor.moveToPosition(getAdapterPosition())) {
        String movieId = mCursor
            .getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_MOVIE_ID));
        String title = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE));
        String poster = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER));
        String votesAverage = mCursor
            .getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTES_AVERAGE));
        String releaseDate = mCursor
            .getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE));
        String overview = mCursor
            .getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_OVERVIEW));
        movie.setId(Integer.parseInt(movieId));
        movie.setTitle(title);
        movie.setPoster(poster);
        movie.setVoteAverage(Float.parseFloat(votesAverage));
        movie.setReleaseDate(releaseDate);
        movie.setOverview(overview);
      }
      mFavoriteMovieAdapterOnClickListener.onClick(movie);
    }
  }
}
