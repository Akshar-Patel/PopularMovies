package com.example.ab.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMovies();
    }

    private void loadMovies() {
        new FetchMovieTask().execute(Util.FILTER_PARAM_POPULAR);
    }

}
