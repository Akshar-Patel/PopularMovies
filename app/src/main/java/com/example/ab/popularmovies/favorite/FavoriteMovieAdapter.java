package com.example.ab.popularmovies.favorite;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.model.Movie;

public class FavoriteMovieAdapter
    extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {

  private final Context mContext;
  private final Cursor mCursor;
  private final FavoriteMovieAdapterOnClickListener mFavoriteMovieAdapterOnClickListener;

  public FavoriteMovieAdapter(Context context, Cursor cursor,
      FavoriteMovieAdapterOnClickListener favoriteMovieAdapterOnClickListener) {
    mContext = context;
    mCursor = cursor;
    mFavoriteMovieAdapterOnClickListener = favoriteMovieAdapterOnClickListener;
  }

  @Override
  public FavoriteMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.favorite_movie_item, parent, false);
    return new FavoriteMovieHolder(view);
  }

  @Override
  public void onBindViewHolder(FavoriteMovieHolder holder, int position) {
    String title = null;
    if (mCursor.moveToPosition(position)) {
      title = mCursor.getString(mCursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE));
    }
    holder.movieTitleTv.setText(title);
  }

  @Override
  public int getItemCount() {
    return mCursor.getCount();
  }

  public interface FavoriteMovieAdapterOnClickListener {

    void onClick(Movie movie);
  }

  public class FavoriteMovieHolder extends RecyclerView.ViewHolder implements OnClickListener {

    final TextView movieTitleTv;

    public FavoriteMovieHolder(View itemView) {
      super(itemView);
      movieTitleTv = itemView.findViewById(R.id.tv_movie_title);
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
