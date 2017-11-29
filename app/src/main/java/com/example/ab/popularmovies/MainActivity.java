package com.example.ab.popularmovies;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ab.popularmovies.api.MovieDb;
import com.example.ab.popularmovies.dialog.SortMoviesDialogFragment;
import com.example.ab.popularmovies.favorite.FavoriteMovieActivity;
import com.example.ab.popularmovies.load.MovieAdapter;
import com.example.ab.popularmovies.load.MovieLoaderManager;
import com.example.ab.popularmovies.model.Movie;
import com.example.ab.popularmovies.util.EndlessRecyclerViewScrollListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MovieAdapter.MovieAdapterOnClickListener {

  private MovieAdapter mMovieAdapter;
  private RecyclerView mMovieGridRecyclerView;
  private int mPage = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setUpRecyclerView();

    mMovieAdapter = new MovieAdapter(this);
    mMovieGridRecyclerView.setAdapter(mMovieAdapter);

    if (savedInstanceState != null) {
      ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(MovieDb.BUNDLE_MOVIES);
      mMovieAdapter.clearItems();
      mMovieAdapter.setMovies(movies);
      mPage = savedInstanceState.getInt(MovieDb.BUNDLE_NEXT_PAGE);
    } else {
      loadMovies();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    ArrayList<Movie> movies = mMovieAdapter.getMovies();
    outState.putParcelableArrayList(MovieDb.BUNDLE_MOVIES, movies);
    outState.putInt(MovieDb.BUNDLE_NEXT_PAGE, mPage++);
  }

  private void setUpRecyclerView() {
    mMovieGridRecyclerView = findViewById(R.id.rv_movie_grid);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      gridLayoutManager.setSpanCount(3);
    }
    mMovieGridRecyclerView.setLayoutManager(gridLayoutManager);
    mMovieGridRecyclerView.setHasFixedSize(true);
    mMovieGridRecyclerView.addOnScrollListener(
        new EndlessRecyclerViewScrollListener(gridLayoutManager) {
          @Override
          public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            loadMoreMovies(page);
          }
        });
  }

  public void loadMovies() {
    mPage = 0;
    mMovieGridRecyclerView.scrollToPosition(0);
    loadMoreMovies(0);
  }

  private void loadMoreMovies(int page) {
    if (mPage > 0) {
      page = mPage;
    }
    page++;
    mPage = page;
    Bundle bundle = new Bundle();
    bundle.putInt(MovieDb.BUNDLE_PAGE, page);

    getSupportLoaderManager()
        .restartLoader(
            MovieLoaderManager.MOVIE_LOADER, bundle, new MovieLoaderManager(this, mMovieAdapter));
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
    if (item.getItemId() == R.id.action_favorite) {
      Intent intent = new Intent(this, FavoriteMovieActivity.class);
      startActivity(intent);
    }
    return true;
  }

  @Override
  public void onClick(Movie movie) {
    Intent intent = new Intent(this, MovieDetailActivity.class);
    intent.putExtra(MovieDb.PARCEL_MOVIE_DETAIL, movie);
    startActivity(intent);
  }
}
