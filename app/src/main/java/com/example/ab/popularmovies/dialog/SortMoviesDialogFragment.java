package com.example.ab.popularmovies.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.example.ab.popularmovies.MainActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;

public class SortMoviesDialogFragment extends DialogFragment {

  private MainActivity mMainActivity;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
        .setTitle("Sort by")
        .setSingleChoiceItems(
            R.array.sort_filter_array,
            getActivity()
                .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
                .getInt(MovieDb.SHARED_PREF_SORT_FILTER, MovieDb.CHOICE_POPULAR),
            (dialog, which) -> {
              mMainActivity = ((MainActivity) getActivity());
              if (which == MovieDb.CHOICE_POPULAR) {
                mMainActivity
                    .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
                    .edit()
                    .putInt(MovieDb.SHARED_PREF_SORT_FILTER, MovieDb.CHOICE_POPULAR)
                    .apply();

                mMainActivity.loadMovies();
                dismiss();
              }
              if (which == MovieDb.CHOICE_TOP_RATED) {
                mMainActivity
                    .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
                    .edit()
                    .putInt(MovieDb.SHARED_PREF_SORT_FILTER, MovieDb.CHOICE_TOP_RATED)
                    .apply();

                mMainActivity.loadMovies();
                dismiss();
              }
            });

    return builder.create();
  }
}
