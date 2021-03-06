package com.example.ab.popularmovies.favorite;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ab.popularmovies.MovieDetailActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.favorite.FavoriteMovieAdapter.FavoriteMovieAdapterOnClickListener;
import com.example.ab.popularmovies.model.Movie;

public class FavoriteMovieActivityFragment extends Fragment
    implements FavoriteMovieAdapterOnClickListener {

  private Cursor mCursor;
  private RecyclerView mMovieListRecyclerView;
  private View mFragmentView;

  public FavoriteMovieActivityFragment() {
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mCursor != null) {
      setupCursor();
      setupAdapter();
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mFragmentView = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    setupCursor();
    setupRecyclerview();
    setupAdapter();
    return mFragmentView;
  }

  private void setupCursor() {
    mCursor =
        getContext()
            .getContentResolver()
            .query(FavoriteMovieEntry.CONTENT_URI, null, null, null, null);
  }

  private void setupAdapter() {
    FavoriteMovieAdapter favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity(), mCursor,
        this);
    mMovieListRecyclerView.setAdapter(favoriteMovieAdapter);
  }

  private void setupRecyclerview() {
    mMovieListRecyclerView = mFragmentView.findViewById(R.id.rv_favorite_movie_grid);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      gridLayoutManager.setSpanCount(3);
    }
    mMovieListRecyclerView.setLayoutManager(gridLayoutManager);
    mMovieListRecyclerView.setHasFixedSize(true);
  }

  @Override
  public void onClick(Movie movie) {
    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
    intent.putExtra(MovieDb.PARCEL_MOVIE_DETAIL, movie);
    startActivity(intent);
  }
}
