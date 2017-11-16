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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickListener {

    private static final String SAVED_STATE_MOVIES = "saved_state_movies";
    private String mSortFilter;
    private int mPage;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;

    public void setSortFilter(String sortFilter) {
        this.mSortFilter = sortFilter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_STATE_MOVIES, mMovieAdapter.getMovies());
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

        mSortFilter = MovieDb.FILTER_POPULAR;

        if (savedInstanceState != null) {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(SAVED_STATE_MOVIES);
            mMovieAdapter.setMovies(movies);
        } else {
            loadMovies();
        }

    }

    public void loadMovies() {
        mPage = 1;
        new FetchMovieTask(this, mMovieAdapter, mPage).execute(mSortFilter);
    }

    private void loadMoreMovies(int page) {
        page++;
        this.mPage = page;
        new FetchMovieTask(this, mMovieAdapter, page).execute(mSortFilter);
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

    void showProgressView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_detail", movie);
        startActivity(intent);
    }
}
