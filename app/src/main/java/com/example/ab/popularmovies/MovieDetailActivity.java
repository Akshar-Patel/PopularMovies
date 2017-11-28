package com.example.ab.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.load.ReviewsFetchTask;
import com.example.ab.popularmovies.load.TrailersFetchTask;
import com.example.ab.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

  private Movie movie;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    toolbar.setNavigationOnClickListener(view -> onBackPressed());

    movie = getIntent().getParcelableExtra(MovieDb.PARCEL_MOVIE_DETAIL);

    TextView titleTextView = findViewById(R.id.tv_title);
    titleTextView.setText(movie.getTitle());

    ImageView posterImageView = findViewById(R.id.iv_poster);
    Picasso.with(this)
        .load(MovieDb.IMAGE_BASE_URL + movie.getPoster())
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(posterImageView);

    TextView releaseDateTextView = findViewById(R.id.tv_release_date);
    releaseDateTextView.setText(movie.getReleaseDate().substring(0, 4));

    TextView voteAverageTextView = findViewById(R.id.tv_vote_average);
    voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));

    TextView overViewTextView = findViewById(R.id.tv_overview);
    overViewTextView.setText(movie.getOverview());

    ToggleButton toggle = findViewById(R.id.tb_favorite);
    toggle.setChecked(isSavedInFavorites());
    toggle.setOnCheckedChangeListener(
        (buttonView, isChecked) -> {
          if (isChecked) {
            saveInFavorites();
          } else {
            removeFromFavorites();
          }
        });

    new TrailersFetchTask(this).execute(movie.getId());
    new ReviewsFetchTask(this).execute(movie.getId());
  }

  private void saveInFavorites() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getId());
    contentValues.put(FavoriteMovieEntry.COLUMN_TITLE, movie.getTitle());
    contentValues.put(FavoriteMovieEntry.COLUMN_POSTER, movie.getPoster());
    contentValues.put(FavoriteMovieEntry.COLUMN_VOTES_AVERAGE, movie.getVoteAverage());
    contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
    contentValues.put(FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview());

    getContentResolver().insert(FavoriteMovieEntry.CONTENT_URI, contentValues);
  }

  private void removeFromFavorites() {
    Uri uriToDelete =
        FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(movie.getId()))
            .build();
    getContentResolver().delete(uriToDelete, null, null);
  }

  private boolean isSavedInFavorites() {
    Uri uri =
        FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(movie.getId()))
            .build();
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.close();
      return true;
    } else {
      return false;
    }
  }
}
