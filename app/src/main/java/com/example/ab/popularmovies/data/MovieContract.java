package com.example.ab.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

  static final String CONTENT_AUTHORITY = "com.example.ab.popularmovies";
  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  static final String PATH_MOVIES = "movies";

  private MovieContract() {
  }

  public static final class FavoriteMovieEntry implements BaseColumns {

    public static final String TABLE_NAME = "movie";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_VOTES_AVERAGE = "votes_average";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_OVERVIEW = "overview";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
  }
}
