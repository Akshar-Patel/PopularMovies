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

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener {

    String filter;
    int page;
    private RecyclerView recyclerViewMovieGrid;
    private MovieAdapter mMovieAdapter;
    private ProgressBar progressBar;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewMovieGrid = findViewById(R.id.recyclerView_movieGrid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerViewMovieGrid.setLayoutManager(gridLayoutManager);
        recyclerViewMovieGrid.setHasFixedSize(true);
        recyclerViewMovieGrid.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreMovies(page);
            }
        });

        mMovieAdapter = new MovieAdapter(this);
        recyclerViewMovieGrid.setAdapter(mMovieAdapter);

        filter = MovieDb.FILTER_POPULAR;
        loadMovies();
    }

    public void loadMovies() {
        page = 1;
        new FetchMovieTask(this, mMovieAdapter, page).execute(filter);
    }

    public void loadMoreMovies(int page) {
        page++;
        this.page = page;
        new FetchMovieTask(this, mMovieAdapter, page).execute(filter);
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
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_detail", movie);
        startActivity(intent);
    }
}
