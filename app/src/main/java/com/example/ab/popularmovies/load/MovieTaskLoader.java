package com.example.ab.popularmovies.load;

import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import com.example.ab.popularmovies.MainActivity;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.model.Movie;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;

class MovieTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

  private final Bundle mBundle;
  private final WeakReference<MainActivity> mMainActivityWeakReference;
  private ArrayList<Movie> mMovies;

  MovieTaskLoader(MainActivity mainActivity, Bundle bundle) {
    super(mainActivity);
    mBundle = bundle;
    mMainActivityWeakReference = new WeakReference<>(mainActivity);
  }

  @Override
  protected void onStartLoading() {
    super.onStartLoading();
    if (mMovies != null) {
      deliverResult(mMovies);

    } else {
      forceLoad();
    }
  }

  @Override
  public ArrayList<Movie> loadInBackground() {

    int filter =
        mMainActivityWeakReference
            .get()
            .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
            .getInt(MovieDb.SHARED_PREF_SORT_FILTER, 0);
    int page = mBundle.getInt(MovieDb.BUNDLE_PAGE);

    ArrayList<Movie> movies = null;
    try {
      if (filter == MovieDb.CHOICE_POPULAR) {
        movies = MovieDb.getPopularMovies(page);
      }
      if (filter == MovieDb.CHOICE_TOP_RATED) {
        movies = MovieDb.getTopRatedMovies(page);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return movies;
  }

  @Override
  public void deliverResult(ArrayList<Movie> data) {
    super.deliverResult(data);
    mMovies = data;
  }
}
