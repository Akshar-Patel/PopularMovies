package com.example.ab.popularmovies.movie;

import com.example.ab.popularmovies.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MovieDb {
    public static final String FILTER_POPULAR = "popular";
    public static final String FILTER_TOPRATED = "top_rated";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private static final String RESULTS_JSON_KEY = "results";
    private static final String ID_JSON_KEY = "id";
    private static final String TITLE_JSON_KEY = "title";
    private static final String POSTER_JSON_KEY = "poster_path";
    private static final String VOTE_AVERAGE_JSON_KEY = "vote_average";
    private static final String RELEASE_DATE_JSON_KEY = "release_date";
    private static final String OVERVIEW_JSON_KEY = "overview";

    private static final String API_KEY = "?api_key=1e4e3b33c1a13c803417d70888c23363";
    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String PAGE_PARAM = "&page=";

    public static ArrayList<Movie> getPopularMovies(int page) throws IOException, JSONException {
        return convertJsonResponse(MOVIEDB_BASE_URL + FILTER_POPULAR + API_KEY + PAGE_PARAM + page);
    }

    public static ArrayList<Movie> getTopRatedMovies(int page) throws IOException, JSONException {
        return convertJsonResponse(
                MOVIEDB_BASE_URL + FILTER_TOPRATED + API_KEY + PAGE_PARAM + page);
    }

    private static ArrayList<Movie> convertJsonResponse(String url)
            throws IOException, JSONException {
        ArrayList<Movie> movies;
        String responseJson = Util.getResponseFromHttpUrl(url);
        JSONObject moviesJsonObject = new JSONObject(responseJson);
        JSONArray resultsJsonArray = moviesJsonObject.getJSONArray(RESULTS_JSON_KEY);

        movies = new ArrayList<>();

        for (int i = 0; i < resultsJsonArray.length(); i++) {

            JSONObject movieJsonObject = resultsJsonArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(Integer.parseInt(movieJsonObject.getString(ID_JSON_KEY)));
            movie.setTitle(movieJsonObject.getString(TITLE_JSON_KEY));
            movie.setPoster(movieJsonObject.getString(POSTER_JSON_KEY));
            movie.setVoteAverage(
                    Float.parseFloat(movieJsonObject.getString(VOTE_AVERAGE_JSON_KEY)));
            movie.setReleaseDate(movieJsonObject.getString(RELEASE_DATE_JSON_KEY));
            movie.setOverview(movieJsonObject.getString(OVERVIEW_JSON_KEY));
            movies.add(movie);
        }
        return movies;
    }
}
