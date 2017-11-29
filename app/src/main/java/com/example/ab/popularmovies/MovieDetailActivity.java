package com.example.ab.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.db.MovieContract.FavoriteMovieEntry;
import com.example.ab.popularmovies.load.ReviewsFetchTask;
import com.example.ab.popularmovies.load.TrailersFetchTask;
import com.example.ab.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

  private Movie mMovie;
  private ScrollView mScrollView;
  private ArrayList<String> mTrailers;
  private ArrayList<String> mReviews;

  public ArrayList<String> getTrailers() {
    return mTrailers;
  }

  public void setTrailers(ArrayList<String> trailers) {
    mTrailers = trailers;
  }

  public ArrayList<String> getReviews() {
    return mReviews;
  }

  public void setReviews(ArrayList<String> reviews) {
    mReviews = reviews;
  }

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

    mMovie = getIntent().getParcelableExtra(MovieDb.PARCEL_MOVIE_DETAIL);

    mScrollView = findViewById(R.id.sv_movie_detail);

    TextView titleTextView = findViewById(R.id.tv_title);
    titleTextView.setText(mMovie.getTitle());

    ImageView posterImageView = findViewById(R.id.iv_poster);
    Picasso.with(this)
        .load(MovieDb.IMAGE_BASE_URL + mMovie.getPoster())
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(posterImageView);

    TextView releaseDateTextView = findViewById(R.id.tv_release_date);
    releaseDateTextView.setText(mMovie.getReleaseDate().substring(0, 4));

    TextView voteAverageTextView = findViewById(R.id.tv_vote_average);
    voteAverageTextView.setText(String.valueOf(mMovie.getVoteAverage()));

    TextView overViewTextView = findViewById(R.id.tv_overview);
    overViewTextView.setText(mMovie.getOverview());

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

    if (savedInstanceState != null) {
      mReviews = savedInstanceState.getStringArrayList("reviews");
      TextView reviewTextView = findViewById(R.id.tv_reviews);
      for (String review : mReviews) {
        reviewTextView.append(review + "\n\n");
      }
      final int[] position = savedInstanceState.getIntArray("scroll_position");
      if (position != null) {
        mScrollView.post(
            new Runnable() {
              public void run() {
                mScrollView.scrollTo(position[0], position[1]);
              }
            });
      }
    } else {
      new ReviewsFetchTask(this).execute(mMovie.getId());
    }
    new TrailersFetchTask(this).execute(mMovie.getId());
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putIntArray(
        "scroll_position", new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    outState.putStringArrayList("reviews", mReviews);
  }

  private void saveInFavorites() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(FavoriteMovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
    contentValues.put(FavoriteMovieEntry.COLUMN_TITLE, mMovie.getTitle());
    contentValues.put(FavoriteMovieEntry.COLUMN_POSTER, mMovie.getPoster());
    contentValues.put(FavoriteMovieEntry.COLUMN_VOTES_AVERAGE, mMovie.getVoteAverage());
    contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
    contentValues.put(FavoriteMovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());

    getContentResolver().insert(FavoriteMovieEntry.CONTENT_URI, contentValues);
  }

  private void removeFromFavorites() {
    Uri uriToDelete =
        FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(mMovie.getId()))
            .build();
    getContentResolver().delete(uriToDelete, null, null);
  }

  private boolean isSavedInFavorites() {
    Uri uri =
        FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(mMovie.getId()))
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
