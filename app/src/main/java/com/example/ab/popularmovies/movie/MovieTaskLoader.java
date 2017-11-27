package com.example.ab.popularmovies.movie;

import android.content.AsyncTaskLoader;
import android.os.Bundle;
import android.view.View;
import com.example.ab.popularmovies.MainActivity;
import com.example.ab.popularmovies.R;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;

/**
 * Created by ab on 16/11/17.
 */
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
    mMainActivityWeakReference.get().findViewById(R.id.pb_loading).setVisibility(View.VISIBLE);
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
    int page = mBundle.getInt("page");
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
