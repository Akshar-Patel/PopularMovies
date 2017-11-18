package com.example.ab.popularmovies;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.ab.popularmovies.movie.MovieDb;

public class SortMoviesDialogFragment extends DialogFragment {

    int CHOICE_POPULAR = 0;
    int CHOICE_TOPRATED = 1;
    MainActivity mainActivity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort by")
                .setSingleChoiceItems(
                        R.array.sort_filter_array
                        , getActivity()
                                .getSharedPreferences("movies_prefs", 0).getInt(MovieDb.SHARED_PREF_SORT_FILTER, CHOICE_POPULAR),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity = ((MainActivity) getActivity());
                                if (which == 0) {
                                    mainActivity.setSortFilter(MovieDb.FILTER_POPULAR);
                                    mainActivity
                                            .getSharedPreferences("movies_prefs", 0)
                                            .edit()
                                            .putInt(
                                                    MovieDb.SHARED_PREF_SORT_FILTER,
                                                    CHOICE_POPULAR)
                                            .apply();

                                    mainActivity.loadMovies();
                                    dismiss();
                                }
                                if (which == 1) {
                                    mainActivity.setSortFilter(MovieDb.FILTER_TOPRATED);
                                    mainActivity
                                            .getSharedPreferences("movies_prefs", 0)
                                            .edit()
                                            .putInt(
                                                    MovieDb.SHARED_PREF_SORT_FILTER,
                                                    CHOICE_TOPRATED)
                                            .apply();

                                    mainActivity.loadMovies();
                                    dismiss();
                                }
                            }
                        });

        return builder.create();
    }
}
