package com.example.ab.popularmovies.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.example.ab.popularmovies.MainActivity;
import com.example.ab.popularmovies.R;
import com.example.ab.popularmovies.api.MovieDb;

public class SortMoviesDialogFragment extends DialogFragment {

  private MainActivity mainActivity;

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
              mainActivity = ((MainActivity) getActivity());
              if (which == MovieDb.CHOICE_POPULAR) {
                mainActivity
                    .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
                    .edit()
                    .putInt(MovieDb.SHARED_PREF_SORT_FILTER, MovieDb.CHOICE_POPULAR)
                    .apply();

                mainActivity.loadMovies();
                dismiss();
              }
              if (which == MovieDb.CHOICE_TOP_RATED) {
                mainActivity
                    .getSharedPreferences(MovieDb.MOVIES_PREFS, 0)
                    .edit()
                    .putInt(MovieDb.SHARED_PREF_SORT_FILTER, MovieDb.CHOICE_TOP_RATED)
                    .apply();

                mainActivity.loadMovies();
                dismiss();
              }
            });

    return builder.create();
  }
}
