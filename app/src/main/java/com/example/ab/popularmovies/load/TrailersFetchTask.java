package com.example.ab.popularmovies.load;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import com.example.ab.popularmovies.MovieDetailActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.util.Util;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;

public class TrailersFetchTask extends AsyncTask<Integer, Void, ArrayList<String>> {

  private final WeakReference<MovieDetailActivity> mMovieDetailActivityWeakReference;

  public TrailersFetchTask(MovieDetailActivity movieDetailActivityWeakReference) {
    mMovieDetailActivityWeakReference = new WeakReference<>(movieDetailActivityWeakReference);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.bt_trailer1)
        .setVisibility(View.INVISIBLE);
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.bt_trailer2)
        .setVisibility(View.INVISIBLE);
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.pb_loading_trailers)
        .setVisibility(View.VISIBLE);
  }

  @Override
  protected ArrayList<String> doInBackground(Integer... integers) {
    int movieId = integers[0];
    ArrayList<String> trailers = null;
    try {
      trailers = MovieDb.getTrailers(movieId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return trailers;
  }

  @Override
  protected void onPostExecute(ArrayList<String> strings) {
    super.onPostExecute(strings);
    mMovieDetailActivityWeakReference
        .get()
        .findViewById(R.id.pb_loading_trailers)
        .setVisibility(View.INVISIBLE);

    if (strings.size() > 0) {
      Button trailer1Button =
          mMovieDetailActivityWeakReference.get().findViewById(R.id.bt_trailer1);
      trailer1Button.setOnClickListener(
          view -> Util.openYoutubeVideo(mMovieDetailActivityWeakReference.get(), strings.get(0)));
      mMovieDetailActivityWeakReference
          .get()
          .findViewById(R.id.bt_trailer1)
          .setVisibility(View.VISIBLE);
    }
    if (strings.size() > 1) {
      Button trailer2Button =
          mMovieDetailActivityWeakReference.get().findViewById(R.id.bt_trailer2);
      trailer2Button.setOnClickListener(
          view -> Util.openYoutubeVideo(mMovieDetailActivityWeakReference.get(), strings.get(1)));
      mMovieDetailActivityWeakReference
          .get()
          .findViewById(R.id.bt_trailer2)
          .setVisibility(View.VISIBLE);
    }
  }
}
