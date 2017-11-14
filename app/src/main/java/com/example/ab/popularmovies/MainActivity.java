package com.example.ab.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mMovieGridRecycleView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGridRecycleView = findViewById(R.id.rv_movie_grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mMovieGridRecycleView.setLayoutManager(gridLayoutManager);
        //mMovieGridRecycleView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();
        mMovieGridRecycleView.setAdapter(mMovieAdapter);

        loadMovies();
    }

    private void loadMovies() {
        new FetchMovieTask(mMovieAdapter).execute(Util.FILTER_PARAM_POPULAR);
    }

}
