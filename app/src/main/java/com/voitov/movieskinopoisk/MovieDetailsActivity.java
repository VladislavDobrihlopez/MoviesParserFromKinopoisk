package com.voitov.movieskinopoisk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String TAG = "MovieDetailsActivity";
    public static final String EXTRA_MOVIE = "movie";
    private ImageView imageViewPoster;
    private ImageView imageViewFavouriteMovie;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private Movie movie;
    private MovieDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();

        Movie passedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(passedMovie.getPoster().getUrl())
                .into(imageViewPoster);

        textViewTitle.setText(passedMovie.getName());
        textViewYear.setText(String.valueOf(passedMovie.getYear()));
        textViewDescription.setText(passedMovie.getDescription());

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        viewModel.loadMovieDetailsInfo(passedMovie.getId());

        TrailersAdapter trailersAdapter = new TrailersAdapter();
        ReviewAdapter reviewAdapter = new ReviewAdapter();

        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);

        viewModel.getMovieResponse().observe(this, new Observer<MovieDetailsResponse>() {
            @Override
            public void onChanged(MovieDetailsResponse movieDetailsResponse) {
                Log.d(TAG, movieDetailsResponse.toString());
                trailersAdapter.setTrailers(movieDetailsResponse.getVideos().getTrailers());
            }
        });

        viewModel.loadReviews(passedMovie.getId());
        viewModel.getReviewsLD().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                Log.d(TAG, reviews.toString());
                reviewAdapter.setReviews(reviews);
            }
        });

        Drawable starOnDrawable = ContextCompat.getDrawable(
                MovieDetailsActivity.this,
                android.R.drawable.star_on
        );

        Drawable starOffDrawable = ContextCompat.getDrawable(
                MovieDetailsActivity.this,
                android.R.drawable.star_off
        );

        viewModel.getFavouriteMovie(passedMovie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie == null) {
                    imageViewFavouriteMovie.setImageDrawable(starOffDrawable);
                    imageViewFavouriteMovie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insert(passedMovie);
                        }
                    });
                } else {
                    imageViewFavouriteMovie.setImageDrawable(starOnDrawable);
                    imageViewFavouriteMovie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.remove(movie.getId());
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        imageViewFavouriteMovie = findViewById(R.id.imageViewFavouriteMovie);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}