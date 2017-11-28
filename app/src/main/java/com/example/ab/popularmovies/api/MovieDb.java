package com.example.ab.popularmovies.api;

import com.example.ab.popularmovies.model.Movie;
import com.example.ab.popularmovies.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDb {

  public static final String PARCEL_MOVIE_DETAIL = "movie_detail";
  public static final String BUNDLE_PAGE = "page";
  public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
  public static final String SHARED_PREF_SORT_FILTER = "shared_pref_sort_filter";
  public static final String MOVIES_PREFS = "movies_prefs";
  public static final int CHOICE_POPULAR = 0;
  public static final int CHOICE_TOP_RATED = 1;
  private static final String FILTER_POPULAR = "popular";
  private static final String FILTER_TOP_RATED = "top_rated";
  private static final String RESULTS_JSON_KEY = "results";
  private static final String ID_JSON_KEY = "id";
  private static final String TITLE_JSON_KEY = "title";
  private static final String POSTER_JSON_KEY = "poster_path";
  private static final String VOTE_AVERAGE_JSON_KEY = "vote_average";
  private static final String RELEASE_DATE_JSON_KEY = "release_date";
  private static final String OVERVIEW_JSON_KEY = "overview";
  private static final String CONTENT_JSON_KEY = "content";
  private static final String YOUTUBE_JSON_KEY = "youtube";
  private static final String SOURCE_JSON_KEY = "source";
  private static final String API_KEY =
      "?api_key=";
  // append api key after '='.
  // e.g. ?api_key=111111111
  private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
  private static final String PAGE_PARAM = "&page=";

  public static ArrayList<Movie> getPopularMovies(int page) throws IOException, JSONException {
    return convertJsonResponseMovies(
        MOVIEDB_BASE_URL + FILTER_POPULAR + API_KEY + PAGE_PARAM + page);
  }

  public static ArrayList<Movie> getTopRatedMovies(int page) throws IOException, JSONException {
    return convertJsonResponseMovies(
        MOVIEDB_BASE_URL + FILTER_TOP_RATED + API_KEY + PAGE_PARAM + page);
  }

  private static ArrayList<Movie> convertJsonResponseMovies(String url)
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
      movie.setVoteAverage(Float.parseFloat(movieJsonObject.getString(VOTE_AVERAGE_JSON_KEY)));
      movie.setReleaseDate(movieJsonObject.getString(RELEASE_DATE_JSON_KEY));
      movie.setOverview(movieJsonObject.getString(OVERVIEW_JSON_KEY));
      movies.add(movie);
    }
    return movies;
  }

  public static ArrayList<String> getReviews(int movieId) throws IOException, JSONException {
    return convertJsonResponseReviews(
        MOVIEDB_BASE_URL + String.valueOf(movieId) + "/reviews" + API_KEY);
  }

  private static ArrayList<String> convertJsonResponseReviews(String url)
      throws IOException, JSONException {
    ArrayList<String> reviews;
    String responseJson = Util.getResponseFromHttpUrl(url);
    JSONObject reviewsJsonObject = new JSONObject(responseJson);
    JSONArray resultsJsonArray = reviewsJsonObject.getJSONArray(RESULTS_JSON_KEY);
    reviews = new ArrayList<>();
    String review;
    JSONObject reviewJsonObject;
    for (int i = 0; i < resultsJsonArray.length(); i++) {
      reviewJsonObject = resultsJsonArray.getJSONObject(i);
      review = reviewJsonObject.getString(CONTENT_JSON_KEY);
      reviews.add(review);
    }
    return reviews;
  }

  public static ArrayList<String> getTrailers(int movieId) throws IOException, JSONException {
    return convertJsonResponseTrailers(
        MOVIEDB_BASE_URL + String.valueOf(movieId) + "/trailers" + API_KEY);
  }

  private static ArrayList<String> convertJsonResponseTrailers(String url)
      throws IOException, JSONException {
    ArrayList<String> trailers;
    String responseJson = Util.getResponseFromHttpUrl(url);
    JSONObject traiersJsonObject = new JSONObject(responseJson);
    JSONArray resultsJsonArray = traiersJsonObject.getJSONArray(YOUTUBE_JSON_KEY);
    trailers = new ArrayList<>();
    String trailerSource;
    JSONObject trailerJsonObject;
    for (int i = 0; i < resultsJsonArray.length(); i++) {
      trailerJsonObject = resultsJsonArray.getJSONObject(i);
      trailerSource = trailerJsonObject.getString(SOURCE_JSON_KEY);
      trailers.add(trailerSource);
    }
    return trailers;
  }
}
