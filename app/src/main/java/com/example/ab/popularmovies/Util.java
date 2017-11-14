package com.example.ab.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ab on 13/11/17.
 */

public class Util {

    public static final String FILTER_PARAM_POPULAR = "popular";
    public static final String FILTER_PARAM_TOPRATED = "top_rated";
    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "?api_key=1e4e3b33c1a13c803417d70888c23363";
    private static OkHttpClient client = new OkHttpClient();

    public static String[] getPopularMovies() throws IOException, JSONException {
        String url = MOVIEDB_BASE_URL + FILTER_PARAM_POPULAR + API_KEY;
        Log.d("url", url);
        return convertJsonResponse(url);
    }

    public static String[] getTopRatedMovies() throws IOException, JSONException {
        return convertJsonResponse(MOVIEDB_BASE_URL + FILTER_PARAM_TOPRATED + API_KEY);
    }

    private static String[] convertJsonResponse(String url) throws IOException, JSONException {
        String[] postersPaths;
        String jsonResponse = getResponseFromHttpUrl(url);
        JSONObject movies = new JSONObject(jsonResponse);

        JSONArray results = movies.getJSONArray("results");
        postersPaths = new String[results.length()];

        for (int i = 0; i < results.length(); i++) {
            String posterPath = results.getJSONObject(i).getString("poster_path");
            postersPaths[i] = posterPath;
        }
        return postersPaths;
    }

    private static String getResponseFromHttpUrl(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

