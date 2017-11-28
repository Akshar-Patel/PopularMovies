package com.example.ab.popularmovies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;

class MovieDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "movie.db";
  private static final int DATABASE_VERSION = 1;

  public MovieDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_MOVIE_TABLE =
        "CREATE TABLE "
            + FavoriteMovieEntry.TABLE_NAME
            + " ("
            + FavoriteMovieEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FavoriteMovieEntry.COLUMN_MOVIE_ID
            + " INTEGER NOT NULL UNIQUE,"
            + FavoriteMovieEntry.COLUMN_TITLE
            + " TEXT NOT NULL,"
            + FavoriteMovieEntry.COLUMN_POSTER
            + " TEXT NOT NULL,"
            + FavoriteMovieEntry.COLUMN_VOTES_AVERAGE
            + " REAL NOT NULL,"
            + FavoriteMovieEntry.COLUMN_RELEASE_DATE
            + " TEXT NOT NULL,"
            + FavoriteMovieEntry.COLUMN_OVERVIEW
            + " TEXT NOT NULL"
            + "); ";
    sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}
