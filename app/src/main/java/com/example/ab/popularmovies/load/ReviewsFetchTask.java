package com.example.ab.popularmovies.load;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import com.example.ab.popularmovies.MovieDetailActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;


public class ReviewsFetchTask extends AsyncTask<Integer, Void, ArrayList<String>> {

  private final WeakReference<MovieDetailActivity> mMovieDetailActivityWeakReference;

  public ReviewsFetchTask(
      MovieDetailActivity movieDetailActivityWeakReference) {
    mMovieDetailActivityWeakReference = new WeakReference<>(movieDetailActivityWeakReference);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();

    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.tv_reviews)
        .setVisibility(View.INVISIBLE);
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.pb_loading_reviews)
        .setVisibility(View.VISIBLE);
  }

  @Override
  protected ArrayList<String> doInBackground(Integer... integers) {
    int movieId = integers[0];
    ArrayList<String> reviews = null;
    try {
      reviews = MovieDb.getReviews(movieId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return reviews;
  }

  @Override
  protected void onPostExecute(ArrayList<String> strings) {
    super.onPostExecute(strings);
    TextView reviewTextView = mMovieDetailActivityWeakReference.get().findViewById(R.id.tv_reviews);
    for (String string : strings) {
      reviewTextView.append(string + "\n\n");
    }
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.pb_loading_reviews)
        .setVisibility(View.INVISIBLE);

    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.tv_reviews)
        .setVisibility(View.VISIBLE);
  }
}
