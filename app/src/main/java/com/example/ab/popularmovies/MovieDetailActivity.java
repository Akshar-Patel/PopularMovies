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

    TextView textViewTitle;
    ImageView imageViewPoster;
    TextView textViewReleaseDate;
    TextView textViewVoteAverage;
    TextView textViewOverview;

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

        textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(movie.getTitle());

        imageViewPoster = findViewById(R.id.imageView_poster);
        Picasso.with(this)
                .load(MovieDb.IMAGE_BASE_URL + movie.getPoster())
                .into(imageViewPoster);

        textViewReleaseDate = findViewById(R.id.textView_releaseDate);
        textViewReleaseDate.setText(movie.getReleaseDate().substring(0, 4));

        textViewVoteAverage = findViewById(R.id.textView_voteAverage);
        textViewVoteAverage.setText(String.valueOf(movie.getVotesAverage()));

        textViewOverview = findViewById(R.id.textView_overview);
        textViewOverview.setText(movie.getOverview());
    }


}
