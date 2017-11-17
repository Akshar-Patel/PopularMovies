package com.example.ab.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.example.ab.popularmovies.movie.Movie;

import java.util.ArrayList;

/**
 * Created by ab on 16/11/17.
 */

public class MovieLoaderManager implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    static final int MOVIE_LOADER = 1;

    private MainActivity mMainActivity;
    private MovieAdapter mMovieAdapter;
    private int mPage;

    MovieLoaderManager(MainActivity mainActivity, MovieAdapter movieAdapter) {
        this.mMainActivity = mainActivity;
        this.mMovieAdapter = movieAdapter;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        this.mPage = bundle.getInt("page");
        return new MovieTaskLoader(mMainActivity, bundle);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies != null) {
            if (mPage == 1) {
                mMovieAdapter.setMovies(movies);
            } else {
                mMovieAdapter.addMovies(movies);
            }
        }
        MainActivity.hideProgressView();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }

}
