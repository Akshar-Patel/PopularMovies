package com.example.ab.popularmovies;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDb;
import com.example.ab.popularmovies.util.EndlessRecyclerViewScrollListener;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickListener {

    private static ProgressBar mProgressBar;
    private String mSortFilter;
    private MovieAdapter mMovieAdapter;

    public static void showProgressView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressView() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void setSortFilter(String sortFilter) {
        this.mSortFilter = sortFilter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MovieDb.SAVED_SORT_FILTER, mSortFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.pb_loading);

        RecyclerView movieGridRecyclerView = findViewById(R.id.rv_movie_grid);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        movieGridRecyclerView.setLayoutManager(gridLayoutManager);
        movieGridRecyclerView.setHasFixedSize(true);
        movieGridRecyclerView.addOnScrollListener(
                new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        loadMoreMovies(page);
                    }
                });

        mMovieAdapter = new MovieAdapter(this);
        movieGridRecyclerView.setAdapter(mMovieAdapter);

        if (savedInstanceState != null) {
            mSortFilter = savedInstanceState.getString(MovieDb.SAVED_SORT_FILTER);
        } else {
            if (getSharedPreferences("movies_prefs", 0).getInt(MovieDb.SHARED_PREF_SORT_FILTER, 0)
                    == 0) {
                mSortFilter = MovieDb.FILTER_POPULAR;
            } else {
                mSortFilter = MovieDb.FILTER_TOPRATED;
            }
        }

        loadMovies();
    }

    public void loadMovies() {
        loadMoreMovies(0);
    }

    private void loadMoreMovies(int page) {
        page++;
        Bundle bundle = new Bundle();
        bundle.putString("sort_filter", mSortFilter);
        bundle.putInt("page", page);
        getLoaderManager()
                .restartLoader(
                        MovieLoaderManager.MOVIE_LOADER,
                        bundle,
                        new MovieLoaderManager(this, mMovieAdapter));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            DialogFragment sortDialogFragment = new SortMoviesDialogFragment();
            sortDialogFragment.show(getFragmentManager(), "sort");
        }
        return true;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_detail", movie);
        startActivity(intent);
    }
}
