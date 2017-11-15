package com.example.ab.popularmovies;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.ab.popularmovies.movie.MovieDb;

public class SortMoviesDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort by")
                .setItems(
                        R.array.sort_filter_array,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity mainActivity = ((MainActivity) getActivity());
                                if (which == 0) {
                                    mainActivity.setSortFilter(MovieDb.FILTER_POPULAR);
                                    mainActivity.loadMovies();
                                }
                                if (which == 1) {
                                    mainActivity.setSortFilter(MovieDb.FILTER_TOPRATED);
                                    mainActivity.loadMovies();
                                }
                            }
                        });
        return builder.create();
    }
}
