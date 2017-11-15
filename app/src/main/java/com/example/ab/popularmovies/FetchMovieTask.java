package com.example.ab.popularmovies;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDb;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ab on 13/11/17.
 */
class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private Context context;
    private MovieAdapter movieAdapter;
    private int page;

    public FetchMovieTask(Context context, MovieAdapter mMovieAdapter, int page) {
        this.movieAdapter = mMovieAdapter;
        this.page = page;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((MainActivity) context).showProgressView();
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... filters) {
        String filter = filters[0];
        ArrayList<Movie> movies = null;
        try {
            if (filter.equals(MovieDb.FILTER_POPULAR)) {
                movies = MovieDb.getPopularMovies(page);
            }
            if (filter.equals(MovieDb.FILTER_TOPRATED)) {
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
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        ((MainActivity) context).hideProgressView();

        if (movies != null) {
            if (page == 1) {
                movieAdapter.setMovies(movies);
            } else {
                movieAdapter.addMovies(movies);
            }
        }
    }

}
