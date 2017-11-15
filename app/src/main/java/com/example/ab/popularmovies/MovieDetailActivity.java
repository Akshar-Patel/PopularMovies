package com.example.ab.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ab.popularmovies.movie.Movie;
import com.example.ab.popularmovies.movie.MovieDb;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = getIntent().getParcelableExtra("movie_detail");

        TextView titleTextView = findViewById(R.id.tv_title);
        titleTextView.setText(movie.getTitle());

        ImageView posterImageView = findViewById(R.id.iv_poster);
        Picasso.with(this).load(MovieDb.IMAGE_BASE_URL + movie.getPoster()).into(posterImageView);

        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        releaseDateTextView.setText(movie.getReleaseDate().substring(0, 4));

        TextView voteAverageTextView = findViewById(R.id.tv_vote_average);
        voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));

        TextView overViewTextView = findViewById(R.id.tv_overview);
        overViewTextView.setText(movie.getOverview());
    }
}
