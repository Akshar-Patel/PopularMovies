package com.example.ab.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.ab.popularmovies.data.MovieContract.FavoriteMovieEntry;

/**
 * Created by ab on 26/11/17.
 */
public class MovieContentProvider extends ContentProvider {

  public static final int MOVIES = 100;
  public static final int MOVIE_WITH_ID = 101;

  private static final UriMatcher sUriMatcher = buildUriMatcher();
  MovieDbHelper movieDbHelper;

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
    uriMatcher.addURI(
        MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);

    return uriMatcher;
  }

  @Override
  public boolean onCreate() {
    movieDbHelper = new MovieDbHelper(getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(
      @NonNull Uri uri,
      @Nullable String[] projection,
      @Nullable String selection,
      @Nullable String[] selectionArgs,
      @Nullable String sortOrder) {
    final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
    Cursor returnCursor = null;

    int match = sUriMatcher.match(uri);
    switch (match) {
      case MOVIES:
        returnCursor =
            sqLiteDatabase.query(
                FavoriteMovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        break;
      case MOVIE_WITH_ID:
        String movieId = uri.getPathSegments().get(1);
        String select = "movie_id=?";
        String[] selectArgs = new String[]{movieId};
        returnCursor =
            sqLiteDatabase.query(
                FavoriteMovieEntry.TABLE_NAME,
                projection,
                select,
                selectArgs,
                null,
                null,
                sortOrder);

        break;
      default:
        throw new UnsupportedOperationException("Unknown uri " + uri);
    }
    returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return returnCursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
    Uri returnUri;

    int match = sUriMatcher.match(uri);
    switch (match) {
      case MOVIES:
        long id = sqLiteDatabase.insert(FavoriteMovieEntry.TABLE_NAME, null, contentValues);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(FavoriteMovieEntry.CONTENT_URI, id);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
    int taskDeleted = 0;
    int match = sUriMatcher.match(uri);
    switch (match) {
      case MOVIE_WITH_ID:
        String movieId = uri.getPathSegments().get(1);
        taskDeleted = sqLiteDatabase
            .delete(FavoriteMovieEntry.TABLE_NAME, "movie_id=?", new String[]{movieId});
        break;
        
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return taskDeleted;
  }

  @Override
  public int update(
      @NonNull Uri uri,
      @Nullable ContentValues contentValues,
      @Nullable String s,
      @Nullable String[] strings) {

    return 0;
  }
}
