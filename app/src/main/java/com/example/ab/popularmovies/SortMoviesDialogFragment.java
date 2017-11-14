package com.example.ab.popularmovies;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.ab.popularmovies.movie.MovieDb;

/**
 * Created by ab on 14/11/17.
 */

public class SortMoviesDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort by")
                .setItems(R.array.sort_filter_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ((MainActivity) getActivity()).setFilter(MovieDb.FILTER_POPULAR);
                            ((MainActivity) getActivity()).loadMovies();
                        }
                        if (which == 1) {
                            ((MainActivity) getActivity()).setFilter(MovieDb.FILTER_TOPRATED);
                            ((MainActivity) getActivity()).loadMovies();
                        }
                    }
                });
        return builder.create();
    }
}
