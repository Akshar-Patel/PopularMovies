package com.example.ab.popularmovies.movie.favorite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.data.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDetailActivity;
import com.example.ab.popularmovies.movie.favorite.FavoriteMovieAdapter.FavoriteMovieAdapterOnClickListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoriteMovieActivityFragment extends Fragment implements
    FavoriteMovieAdapterOnClickListener {

  FavoriteMovieAdapter favoriteMovieAdapter;
  Cursor cursor;
  private RecyclerView mMovieListRecyclerView;
  private View mFragmentView;

  public FavoriteMovieActivityFragment() {
  }

  @Override
  public void onResume() {
    super.onResume();
    if (cursor != null) {
      cursor = getContext().getContentResolver().query(FavoriteMovieEntry.CONTENT_URI,
          null,
          null,
          null,
          null);
      favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity(), cursor, this);
      mMovieListRecyclerView.setAdapter(favoriteMovieAdapter);
    }
    Toast.makeText(getActivity(), cursor.getCount() + "", Toast.LENGTH_SHORT).show();
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mFragmentView = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    cursor = getContext().getContentResolver().query(FavoriteMovieEntry.CONTENT_URI,
        null,
        null,
        null,
        null);
    setUpRecyclerView();
    favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity(), cursor, this);
    mMovieListRecyclerView.setAdapter(favoriteMovieAdapter);
    return mFragmentView;
  }

  private void setUpRecyclerView() {
    mMovieListRecyclerView = mFragmentView.findViewById(R.id.rv_favorite_movie_grid);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mMovieListRecyclerView.setLayoutManager(linearLayoutManager);
    mMovieListRecyclerView.setHasFixedSize(true);
  }

  @Override
  public void onClick(Movie movie) {
    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
    intent.putExtra("movie_detail", movie);
    startActivity(intent);
  }

}
