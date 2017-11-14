package com.example.ab.popularmovies;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by ab on 13/11/17.
 */
class FetchMovieTask extends AsyncTask<String, Void, String[]> {

    @Override
    protected String[] doInBackground(String... filters) {
        String filter = filters[0];
        String[] movies = null;
        try {
            if (filter.equals(Util.FILTER_PARAM_POPULAR)) {
                movies = Util.getPopularMovies();
            }
            if (filter.equals(Util.FILTER_PARAM_TOPRATED)) {
                movies = Util.getTopRatedMovies();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onPostExecute(String[] paths) {
        super.onPostExecute(paths);
        for (String path :
                paths) {

        }
    }
}
