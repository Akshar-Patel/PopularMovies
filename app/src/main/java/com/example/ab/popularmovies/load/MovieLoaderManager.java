package com.example.ab.popularmovies.load;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import com.example.ab.popularmovies.MainActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.model.Movie;
import java.util.ArrayList;

public class MovieLoaderManager implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

  public static final int MOVIE_LOADER = 1;

  private final MainActivity mMainActivity;
  private final MovieAdapter mMovieAdapter;
  private int mPage;

  public MovieLoaderManager(MainActivity mainActivity, MovieAdapter movieAdapter) {
    this.mMainActivity = mainActivity;
    this.mMovieAdapter = movieAdapter;
  }

  @Override
  public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
    mMainActivity.findViewById(R.id.pb_loading).setVisibility(View.VISIBLE);
    this.mPage = bundle.getInt(MovieDb.BUNDLE_PAGE);
    return new MovieTaskLoader(mMainActivity, bundle);
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
    mMainActivity.findViewById(R.id.pb_loading).setVisibility(View.INVISIBLE);
    if (movies != null) {
      if (mPage == 1) {
        mMovieAdapter.setMovies(movies);
      } else {
        mMovieAdapter.addMovies(movies);
      }
    }
  }

  @Override
  public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
  }
}
