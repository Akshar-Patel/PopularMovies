package com.example.ab.popularmovies;

import android.os.AsyncTask;

import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDb;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final MovieAdapter mMovieAdapter;
    private final int mPage;
    private final WeakReference<MainActivity> mMainActivityWeakReference;

    FetchMovieTask(MainActivity mainActivity, MovieAdapter movieAdapter, int page) {
        this.mMovieAdapter = movieAdapter;
        this.mPage = page;
        this.mMainActivityWeakReference = new WeakReference<>(mainActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mMainActivityWeakReference.get().showProgressView();
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... filters) {
        String filter = filters[0];
        ArrayList<Movie> movies = null;
        try {
            if (filter.equals(MovieDb.FILTER_POPULAR)) {
                movies = MovieDb.getPopularMovies(mPage);
            }
            if (filter.equals(MovieDb.FILTER_TOPRATED)) {
                movies = MovieDb.getTopRatedMovies(mPage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        mMainActivityWeakReference.get().hideProgressView();

        if (movies != null) {
            if (mPage == 1) {
                mMovieAdapter.setMovies(movies);
            } else {
                mMovieAdapter.addMovies(movies);
            }
        }
    }
}
