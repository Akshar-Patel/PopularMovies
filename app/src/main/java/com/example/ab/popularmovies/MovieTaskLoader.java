package com.example.ab.popularmovies;

import android.content.AsyncTaskLoader;
import android.os.Bundle;

import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDb;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ab on 16/11/17.
 */
class MovieTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    Bundle mBundle;
    ArrayList<Movie> mMovies;

    MovieTaskLoader(MainActivity mainActivity, Bundle bundle) {
        super(mainActivity);
        mBundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        MainActivity.showProgressView();
        if (mMovies != null) {
            deliverResult(mMovies);
        } else {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        String filter = mBundle.getString("sort_filter");
        int page = mBundle.getInt("page");
        ArrayList<Movie> movies = null;
        try {
            if (filter != null && filter.equals(MovieDb.FILTER_POPULAR)) {
                movies = MovieDb.getPopularMovies(page);
            }
            if (filter != null && filter.equals(MovieDb.FILTER_TOPRATED)) {
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
