package com.example.ab.popularmovies.movie;

import com.example.ab.popularmovies.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ab on 14/11/17.
 */

public class MovieDb {
    public static final String FILTER_POPULAR = "popular";
    public static final String FILTER_TOPRATED = "top_rated";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String API_KEY = "?api_key=1e4e3b33c1a13c803417d70888c23363";
    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String PAGE = "&page=";

    public static ArrayList<Movie> getPopularMovies(int page) throws IOException, JSONException {
        return convertJsonResponse(MOVIEDB_BASE_URL + FILTER_POPULAR + API_KEY + PAGE + page);
    }

    public static ArrayList<Movie> getTopRatedMovies(int page) throws IOException, JSONException {
        return convertJsonResponse(MOVIEDB_BASE_URL + FILTER_TOPRATED + API_KEY + PAGE + page);
    }

    private static ArrayList<Movie> convertJsonResponse(String url) throws IOException, JSONException {
        ArrayList<Movie> movies;
        String responseJson = Util.getResponseFromHttpUrl(url);
        JSONObject moviesJson = new JSONObject(responseJson);
        JSONArray resultsJson = moviesJson.getJSONArray("results");

        movies = new ArrayList<>();

        for (int i = 0; i < resultsJson.length(); i++) {

            JSONObject movieJsonObject = resultsJson.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(Integer.parseInt(movieJsonObject.getString("id")));
            movie.setTitle(movieJsonObject.getString("title"));
            movie.setPoster(movieJsonObject.getString("poster_path"));
            movie.setVotesAverage(Float.parseFloat(movieJsonObject.getString("vote_average")));
            movie.setReleaseDate(movieJsonObject.getString("release_date"));
            movie.setOverview(movieJsonObject.getString("overview"));
            movies.add(movie);
        }
        return movies;
    }
}
